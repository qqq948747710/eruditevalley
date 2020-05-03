package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.Services.UserService;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,Observer<UserEntity>{
    private EditText regName;
    private EditText regUsername;
    private EditText regPwd;
    private EditText regRepwd;
    private Button btnReg;
    private Button navBackBtn;
    private TextView navTitle;
    private UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    private void init(){
        regName = (EditText) findViewById(R.id.reg_name);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regPwd = (EditText) findViewById(R.id.reg_pwd);
        regRepwd = (EditText) findViewById(R.id.reg_repwd);
        btnReg = (Button) findViewById(R.id.btn_reg);

        btnReg.setOnClickListener(this);
        initnavbar();
        initService();
    }
    private void initnavbar(){
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        navBackBtn.setOnClickListener(this);
        navTitle = (TextView) findViewById(R.id.nav_title);
        navTitle.setText("注册");
    }
    private void initService(){
         service=new UserService(getBaseContext());
    }
    private boolean iserrvalue(String username,String password,String repwd){
        if(username.isEmpty()){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }else if (password.isEmpty()||repwd.isEmpty()){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    private void register(String name,String username,String password,String repwd){
        if(!repwd.equals(repwd)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        service.register(name,username,password,repwd,this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back_btn:
                finish();
                break;
            case R.id.btn_reg:
                String name=regName.getText().toString().trim();
                String username=regUsername.getText().toString().trim();
                String password= regPwd.getText().toString().trim();
                String repwd=regRepwd.getText().toString().trim();
                if(iserrvalue(username,password,repwd)){
                    return;
                }
                register(name,username,password,repwd);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(UserEntity userEntity) {
        if(userEntity.getCode()!=0){
            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("username",userEntity.getUsername());
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
