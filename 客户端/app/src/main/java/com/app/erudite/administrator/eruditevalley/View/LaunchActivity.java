package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.app.erudite.administrator.eruditevalley.R;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {
    private TextView launchVerTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        load();
    }
    private String getversioninfo(Context context){
        String version="";
        try{
            PackageManager pm=context.getPackageManager();
            PackageInfo pi=pm.getPackageInfo(context.getPackageName(),0);
            version=pi.versionName;
            if(version==null||version.length()<=0){
                return "";
            }
        }catch (Exception e){
            Log.e("verison get err",e.getMessage());
        }
        return version;
    }
    private  void load(){
        launchVerTv = (TextView) findViewById(R.id.launch_ver_tv);
        launchVerTv.setText("V"+getversioninfo(getBaseContext()));
        Timer time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(LaunchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

}
