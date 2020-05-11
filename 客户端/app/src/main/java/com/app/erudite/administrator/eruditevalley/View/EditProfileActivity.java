package com.app.erudite.administrator.eruditevalley.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.erudite.administrator.eruditevalley.Config.AppConfig;
import com.app.erudite.administrator.eruditevalley.Model.UserInfo;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.Services.Entity.HeadEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.UserEntity;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,Observer<HeadEntity>{
    private ImageView eduitHeadImg;
    private TextView nav_title;
    private Button nav_back;
    private RelativeLayout editNameItem;
    private TextView editName;
    private RelativeLayout editSexItem;
    private TextView editSex;
    private RelativeLayout editEmailItem;
    private TextView editEmail;
    private UserInfo mInfo;
    private Button editOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        eduitHeadImg = (ImageView) findViewById(R.id.eduit_head_img);
        nav_title=(TextView)findViewById(R.id.nav_title);
        nav_back=(Button)findViewById(R.id.nav_back_btn);
        editNameItem = (RelativeLayout) findViewById(R.id.edit_name_item);
        editName = (TextView) findViewById(R.id.edit_name);
        editSexItem = (RelativeLayout) findViewById(R.id.edit_sex_item);
        editSex = (TextView) findViewById(R.id.edit_sex);
        editEmailItem = (RelativeLayout) findViewById(R.id.edit_email_item);
        editEmail = (TextView) findViewById(R.id.edit_email);
        editOk = (Button) findViewById(R.id.edit_ok);
        nav_title.setText("资料");
        eduitHeadImg.setOnClickListener(this);
        nav_back.setOnClickListener(this);
        editNameItem.setOnClickListener(this);
        editSexItem.setOnClickListener(this);
        editEmailItem.setOnClickListener(this);
        editOk.setOnClickListener(this);
        mInfo=UserInfo.get(null);
        Glide.with(this).load(AppConfig.Services.baserurl+mInfo.getHeadpath()).into(eduitHeadImg);
        editName.setText(mInfo.getName().trim());
        editEmail.setText(mInfo.getEmail().trim());
        editSex.setText(mInfo.getSex().equals("0")?"男":"女");
    }
    private void selectPic(){
        //intent可以应用于广播和发起意图，其中属性有：ComponentName,action,data等
        Intent intent=new Intent();
        intent.setType("image/*");
        //action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
        //类型的内容给你选择
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back_btn:
                finish();
                break;
            case R.id.eduit_head_img:
                selectPic();
                break;
            case R.id. edit_name_item:
                Intent intent=new Intent(this,EditActivity.class);
                intent.putExtra("content",mInfo.getName());
                startActivityForResult(intent, 2);
                break;
            case R.id.edit_email_item:
                intent=new Intent(this,EditActivity.class);
                intent.putExtra("content",mInfo.getEmail());
                startActivityForResult(intent, 3);
                break;
            case R.id.edit_sex_item:
                break;
            case R.id.edit_ok:
                UserEntity userEntity=new UserEntity();
                userEntity.setCode(0);
                userEntity.setUsername("");
                userEntity.setHeadpath("");
                userEntity.setName(editName.getText().toString().trim());
                userEntity.setSex(editSex.getText().toString().trim().equals("男")?"0":"1");
                userEntity.setEmail(editEmail.getText().toString().trim());
                mInfo.alterinfoandupdateView(userEntity);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver cr=this.getContentResolver();
            mInfo.updatehead(uri, this);
            return;
        }
        if(requestCode==2){
            String content=data.getStringExtra(EditActivity.Content);
            editName.setText(content);
        }
        if(requestCode==3){
            String content=data.getStringExtra(EditActivity.Content);
            editEmail.setText(content);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(HeadEntity headEntity) {
        if(headEntity.getError()==1){
            Toast.makeText(this,"修改失败! 错误信息:"+headEntity.getResult().getMsg(),Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"修改成功!",Toast.LENGTH_SHORT).show();
        Glide.with(this).load(AppConfig.Services.baserurl+headEntity.getResult().getPath()).into(eduitHeadImg);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
