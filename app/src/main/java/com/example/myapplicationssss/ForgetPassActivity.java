package com.example.myapplicationssss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.BmobSMS.verifySmsCode;

public class ForgetPassActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText forget_user_phone;
    private EditText forget_textpass;
    private Button get_textpass;
    private EditText forget_user_pass;
    //private EditText forget_user_pass2;
    private TextView reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        forget_user_phone = (EditText)findViewById(R.id.forget_user_phone);
        forget_textpass = (EditText)findViewById(R.id.forget_user_testpass);
        get_textpass = (Button)findViewById(R.id.send_forget_testpass);
        forget_user_pass = (EditText)findViewById(R.id.forget_user_pass);
        //forget_user_pass2 = (EditText)findViewById(R.id.forget_user_pass2);
        reset=(TextView)findViewById(R.id.activity_forget_reset);

        get_textpass.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send_forget_testpass:
                if (forget_user_phone.getText().toString()!=null && !forget_user_phone.getText().toString().equals("")){
                    if(forget_user_phone.getText().toString().length()==11){

                        requestSendSMSCode(forget_user_phone.getText().toString());
                    }else{
                        Toast.makeText(ForgetPassActivity.this, "请输入正确的十一位号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPassActivity.this, "请输入手机号，手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_forget_reset:
                checktestpass(forget_user_phone.getText().toString(), forget_textpass.getText().toString(),forget_user_pass.getText().toString());


                break;
        }

    }

    public void requestSendSMSCode(final String phone) {
        cn.bmob.sms.BmobSMS.requestSMSCode(this, phone,
                "您的验证码是%smscode%，有效期为%ttl%分钟。您正在使用%appname%验证码", new RequestSMSCodeListener(){
                    @Override
                    public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                        if(e==null){
                            Toast.makeText(ForgetPassActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgetPassActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public void checktestpass(final String phone,final String testpass,final String new_pass){
        verifySmsCode(phone, testpass, new UpdateListener() {
            @Override
            public void done(cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    reset(testpass,new_pass);
                    //Toast.makeText(ForgetPassActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgetPassActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void reset(final String testpass, final String new_pass){

        BmobUser.resetPasswordBySMSCode(testpass, new_pass, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ForgetPassActivity.this, "666", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("显示"," "+testpass+" "+new_pass);
                    Toast.makeText(ForgetPassActivity.this, "7777", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
