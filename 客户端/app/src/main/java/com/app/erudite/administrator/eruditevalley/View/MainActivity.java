package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.View.Fragment.ExescisesFragment;
import com.app.erudite.administrator.eruditevalley.View.Fragment.IndexFragment;
import com.app.erudite.administrator.eruditevalley.View.Fragment.MyFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int state;
    private final int COURSE=0;
    private final int EXERCISES=1;
    private final int MY=2;
    private Button navBackBtn;
    private TextView navTitle;
    private FrameLayout fragmentContainer;
    private ImageView mainCourseImg;
    private TextView mainCourseTv;
    private ImageView mainExercisesImg;
    private TextView mainExercisesTv;
    private ImageView mainMyImg;
    private TextView mainMyTv;
    private LinearLayout mainTabbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initcontent();
        inittab();
    }
    private void  init(){
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        navTitle = (TextView) findViewById(R.id.nav_title);
    }
    private void initcontent(){
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        initfragment();
    }
    private void inittab() {
        mainCourseImg = (ImageView) findViewById(R.id.main_course_img);
        mainCourseTv = (TextView) findViewById(R.id.main_course_tv);
        mainExercisesImg = (ImageView) findViewById(R.id.main_exercises_img);
        mainExercisesTv = (TextView) findViewById(R.id.main_exercises_tv);
        mainMyImg = (ImageView) findViewById(R.id.main_my_img);
        mainMyTv = (TextView) findViewById(R.id.main_my_tv);
        mainTabbar = (LinearLayout) findViewById(R.id.main_tabbar);
        tabbarlisten(mainTabbar);
        setstart(COURSE);
    }
    private  void initfragment(){
        FragmentTransaction fragmentTransaction = getfragmentTransaction();
        MyFragment myFragment=MyFragment.get();
        IndexFragment indexFragment=IndexFragment.get();
        ExescisesFragment exercisesFragment=ExescisesFragment.get();
        fragmentTransaction.add(R.id.fragment_container,myFragment,String.valueOf(MY));
        fragmentTransaction.add(R.id.fragment_container,indexFragment,String.valueOf(COURSE));
        fragmentTransaction.add(R.id.fragment_container,exercisesFragment,String.valueOf(EXERCISES));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentTransaction.hide(myFragment);
        fragmentTransaction.hide(exercisesFragment);
    }
    private void setstart(int state){
        if(state==COURSE){
            navTitle.setText("课程");
            navBackBtn.setVisibility(View.GONE);
            mainCourseImg.setImageResource(R.drawable.main_course_icon_selected);
            mainCourseTv.setTextColor(Color.parseColor("#1c86ee"));
            this.state=COURSE;
            showfragment(IndexFragment.get());
        }else if(state==EXERCISES){
            navTitle.setText("习题");
            mainExercisesImg.setImageResource(R.drawable.main_exercises_icon_selected);
            mainExercisesTv.setTextColor(Color.parseColor("#1c86ee"));
            this.state=EXERCISES;
            showfragment(ExescisesFragment.get());
        }else if(state==MY){
            navTitle.setText("我");
            navBackBtn.setVisibility(View.GONE);
            mainMyImg.setImageResource(R.drawable.main_my_icon_selected);
            mainMyTv.setTextColor(Color.parseColor("#1c86ee"));
            this.state=MY;
            showfragment(MyFragment.get());
        }
    }
    private void clearstate(){
        hidefragment(MyFragment.get());
        hidefragment(IndexFragment.get());
        hidefragment(ExescisesFragment.get());
        mainCourseImg.setImageResource(R.drawable.main_course_icon);
        mainExercisesImg.setImageResource(R.drawable.main_exercises_icon);
        mainMyImg.setImageResource(R.drawable.main_my_icon);
        mainCourseTv.setTextColor(Color.parseColor("#000000"));
        mainExercisesTv.setTextColor(Color.parseColor("#000000"));
        mainMyTv.setTextColor(Color.parseColor("#000000"));
    }
    private void tabbarlisten(ViewGroup view){
        for(int i=0;i<view.getChildCount();i++){
            view.getChildAt(i).setOnClickListener(this);
        }
    }
    private FragmentTransaction  getfragmentTransaction(){
        FragmentManager FragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = FragmentManager.beginTransaction();
        return fragmentTransaction;
    }
    private void showfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getfragmentTransaction();
        fragmentTransaction.show(fragment).commit();
    }
    private void hidefragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getfragmentTransaction();
        fragmentTransaction.hide(fragment).commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_course:
                clearstate();
                setstart(COURSE);
                break;
            case R.id.main_exercises:
                clearstate();
                setstart(EXERCISES);
                break;
            case R.id.main_my:
                clearstate();
                setstart(MY);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
