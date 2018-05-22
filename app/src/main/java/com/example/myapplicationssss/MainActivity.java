package com.example.myapplicationssss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.logging.Logger;

import cn.bmob.push.BmobPush;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b1 = (Button)findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });

        Button b2 =(Button)findViewById(R.id.publish);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,PublishActivity.class);
                startActivity(intent2);
            }
        });

        Button b3 =(Button)findViewById(R.id.get);
        b3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent3 =new Intent(MainActivity.this,GetResourceActivity.class);
                startActivity(intent3);
            }
        });

        Bmob.initialize(this,"a221092014d3a2f1103818b909d606bb");

        BmobSMS.initialize(this,"a221092014d3a2f1103818b909d606bb ");

        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    Log.e(" "," "+bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Log.e("",e.getMessage());
                }
            }

           /* public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Logger.e(e.getMessage());
                }
            }*/
        });
// 启动推送服务
        BmobPush.startWork(this);


    }
}
