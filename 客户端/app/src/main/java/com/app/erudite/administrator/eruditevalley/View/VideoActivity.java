package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.erudite.administrator.eruditevalley.Config.AppConfig;
import com.app.erudite.administrator.eruditevalley.Model.IndexListinfo;
import com.app.erudite.administrator.eruditevalley.R;
import com.bumptech.glide.Glide;



import cn.jzvd.JzvdStd;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
     private static IndexListinfo sinfo;
    private Button navBackBtn;
    private TextView navTitle;
    private TextView video_text;
     JzvdStd mplayer;
     public VideoActivity(){
         super();
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mplayer=findViewById(R.id.activity_video_player);
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        navTitle = (TextView) findViewById(R.id.nav_title);
        video_text= (TextView) findViewById(R.id.activity_video_text);
        navTitle.setOnClickListener(this);
        if(sinfo!=null){
            video_text.setText(sinfo.getTitle());
            navTitle.setText(sinfo.getTitle());
            mplayer.setUp(AppConfig.Services.baserurl+"/static/video/"+sinfo.getVideoname(),sinfo.getTitle(),JzvdStd.SCREEN_NORMAL);
            Glide.with(this).load(AppConfig.Services.baserurl+"/static/images/indexlist"+sinfo.getImagename()).into(mplayer.posterImageView);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mplayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mplayer.backPress();
    }

    //跳转选题页面，并传入题目参数
    public static void loadActivity(Context context, IndexListinfo infos){
        Intent intent=new Intent(context, VideoActivity.class);
        sinfo=infos;
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back_btn:
                finish();
                break;
        }
    }
}
