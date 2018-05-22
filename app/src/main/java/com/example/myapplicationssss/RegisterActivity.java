package com.example.myapplicationssss;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText input_user_name;
    private EditText input_user_phone;
    private EditText input_textpass;
    private Button get_textpass;
    private EditText input_user_pass;
    private TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_user_name = (EditText)findViewById(R.id.login_user_name);
        input_user_phone =(EditText)findViewById(R.id.login_user_phone);
        input_textpass = (EditText)findViewById(R.id.login_user_testpass);
        get_textpass =(Button)findViewById(R.id.send_testpass);
        input_user_pass =(EditText)findViewById(R.id.login_user_pass);
        register = (TextView)findViewById(R.id.activity_register_sure_textview);


        get_textpass.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.send_testpass:
                if (input_user_phone.getText().toString()!=null && !input_user_phone.getText().toString().equals("")){
                    if(input_user_phone.getText().toString().length()==11){

                    requestSendSMSCode(input_user_phone.getText().toString());
                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入正确的十一位号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "请输入手机号，手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_register_sure_textview:
                checktestpass(input_user_phone.getText().toString(),
                        input_textpass.getText().toString(),input_user_pass.getText().toString(), input_user_name.getText().toString());

                break;
        }

    }

    public void requestSendSMSCode(final String phone) {
        cn.bmob.sms.BmobSMS.requestSMSCode(this, phone,
                "您的验证码是%smscode%，有效期为%ttl%分钟。您正在使用%appname%验证码", new RequestSMSCodeListener(){
            @Override
            public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void checktestpass(final String phone, final String textpass,final String pass,final String username){
        BmobSMS.verifySmsCode(phone, textpass, new UpdateListener() {
//
                    @Override
                    public void done(BmobException e) {
                        if(e==null){

                                register(phone, pass, username);

                        }else{
                            Toast.makeText(RegisterActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(String phone,String pass,String username){
        User bu = new User();
        bu.setUsername(username);
        bu.setPassword(pass);
        bu.setMobilePhoneNumber(phone);
        bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
