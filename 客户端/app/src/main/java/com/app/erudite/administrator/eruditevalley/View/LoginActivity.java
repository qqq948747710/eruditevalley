package com.app.erudite.administrator.eruditevalley.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.app.erudite.administrator.eruditevalley.Model.UserInfo;
import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.Services.UserService;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener , Observer<UserEntity> {
    private EditText logUsername;
    private EditText logPwd;
    private Button btnLog;
    private Button logReg;
    private Button logFind;
    private Button navBackBtn;
    private TextView navTitle;
    private UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        logUsername = (EditText) findViewById(R.id.log_username);
        logPwd = (EditText) findViewById(R.id.log_pwd);
        btnLog = (Button) findViewById(R.id.btn_log);
        logReg = (Button) findViewById(R.id.log_reg);
        logFind = (Button) findViewById(R.id.log_find);
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        navTitle = (TextView) findViewById(R.id.nav_title);
        btnLog.setOnClickListener(this);
        logReg.setOnClickListener(this);
        logFind.setOnClickListener(this);
        navBackBtn.setOnClickListener(this);
        navTitle.setText("登录");
        initSerive();
    }

    private void initSerive(){
        this.service=new UserService(getBaseContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_reg:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.log_find:
                Toast.makeText(this,"功能还未实现",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_log:
                String username=logUsername.getText().toString().trim();
                String password=logPwd.getText().toString().trim();
                if(iserrvalue(username,password)){
                    return;
                }
                login(username,password);
                break;
            case R.id.nav_back_btn:
                finish();
                break;
        }
    }
    private boolean iserrvalue(String username,String password){
        if(username.isEmpty()){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }else if(password.isEmpty()){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    private void login(String username,String password){
        service.login(username,password,this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(UserEntity userEntity) {
        if(userEntity.getCode()!=0){
            Toast.makeText(this,"账号不存在或者密码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            if (extras!=null){
                String username = extras.getString("username");
                logUsername.setText(username);
                logPwd.setSelection(0);
            };
        }
    }
}
