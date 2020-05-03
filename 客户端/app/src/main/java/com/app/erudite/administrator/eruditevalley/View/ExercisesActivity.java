package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.erudite.administrator.eruditevalley.Model.Exercises;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter.OptionAdapter;

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener {
    private static Exercises sExercises;
    private TextView activityExercisesContent;
    private ListView activityExercisesOption;
    private Button navBackBtn;
    private TextView navTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        activityExercisesContent = (TextView) findViewById(R.id.activity_exercises_content);
        activityExercisesOption = (ListView) findViewById(R.id.activity_exercises_option);
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        navTitle = (TextView) findViewById(R.id.nav_title);
        navBackBtn.setOnClickListener(this);
        update();
    }

   private void update(){
        if(sExercises!=null){
            navTitle.setText(sExercises.getTitle());
            activityExercisesContent.setText(sExercises.getContent());
            OptionAdapter optionAdapter = new OptionAdapter(this,R.layout.activity_exercises_item,sExercises.getOption(),sExercises.getRight());
            activityExercisesOption.setAdapter(optionAdapter);
        }
   }

    //跳转选题页面，并传入题目参数
    public static void loadActivity(Context context, Exercises exercises){
        Intent intent=new Intent(context, ExercisesActivity.class);
        sExercises=exercises;
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
