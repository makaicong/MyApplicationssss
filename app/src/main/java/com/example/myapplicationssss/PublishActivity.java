package com.example.myapplicationssss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationssss.tools.Bimp;
import com.example.myapplicationssss.tools.GlobalDefineValues;
import com.example.myapplicationssss.tools.ImageItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class PublishActivity extends AppCompatActivity{
    private EditText goodtitle;
    private EditText gooddescribe;
    private EditText goodprice;
    private EditText goodschool;
    private EditText goodprovince;
    private EditText goodfirst;
    private EditText goodsecond;
    private TextView publish;
    private GridView gridView;
    private View root;
    private PopupWindow pop = null;
    private static final int TAKE_PHOTO=1;
    private static final int CHOOSE_PHOTO=2;
    private File img;
    private PublishGoodsGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=getLayoutInflater().inflate(R.layout.activity_publish,null);
        setContentView(root);


        goodtitle = (EditText) findViewById(R.id.title);
        gooddescribe = (EditText)findViewById(R.id.describe);
        goodprice = (EditText)findViewById(R.id.price);
        goodschool = (EditText)findViewById(R.id.school);
        goodprovince = (EditText)findViewById(R.id.province);
        goodfirst = (EditText)findViewById(R.id.first);
        goodsecond = (EditText)findViewById(R.id.second);
        publish = (TextView)findViewById(R.id.publish);
        gridView=(GridView)findViewById(R.id.noScrollgridview);
        adapter=new PublishGoodsGridAdapter(getApplicationContext());

        pop = new PopupWindow(PublishActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        //ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        //拍照
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
                //intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                pop.dismiss();
                //ll_popup.clearAnimation();
            }
        });
        //相册
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(PublishActivity.this, AlbumActivity.class);
                //startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();

                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                //ll_popup.clearAnimation();
            }
        });
        //取消
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                //ll_popup.clearAnimation();
            }
        });



        adapter.setListener(new PublishGoodsGridAdapter.OnPublishGridDeleteItemListener() {
            @Override
            public void deleteOnClick(View v, int position) {
                Bimp.tempSelectBitmap.remove(position);
                adapter.notifyDataSetChanged();
            }
        });


        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//查看某个照片

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    //ll_popup.startAnimation(AnimationUtils.loadAnimation(PublishGoodsActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(root, Gravity.CENTER, 0, 0);
                } else {
                    //Toast.makeText(PublishGoodsActivity.this, Bimp.tempSelectBitmap.get(arg2).imagePath, Toast.LENGTH_LONG).show();
                }
            }
        });



        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishnow();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bm = extras.getParcelable("data");
                        Uri uri = saveBitmap(bm);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        takePhoto.setImagePath(uri.getPath());
                        Bimp.tempSelectBitmap.add(takePhoto);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
           /* case GlobalDefineValues.CHOOSE_KIND:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        kind = extras.getString(GlobalDefineValues.GOODS_KIND);
                        secondkind = extras.getString(GlobalDefineValues.GOODS_SECOND_KIND);
                        Log.e("kind", kind + "  " + secondkind);
                        mGoodsKindText.setText(kind + "  " + secondkind);
                    }
                }
                break;*/
        }

    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir;
        if (hasSD()) {
            tmpDir = new File(Environment.getExternalStorageDirectory() + "/tradein/");
        } else {
            tmpDir = new File(Environment.getDataDirectory() + "/tradein/");
        }

        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        img = new File(tmpDir.getAbsolutePath() + System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasSD() {
        //如果有SD卡 则下载到SD卡中
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;

        } else {
            //如果没有SD卡
            return false;
        }
    }

    public void publishnow(){

    }


}
