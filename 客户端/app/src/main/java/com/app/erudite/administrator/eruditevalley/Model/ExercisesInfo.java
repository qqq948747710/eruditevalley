package com.app.erudite.administrator.eruditevalley.Model;

import com.app.erudite.administrator.eruditevalley.Services.ExescisesService;
import com.app.erudite.administrator.eruditevalley.View.Fragment.ExescisesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Exescisesinfo
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/4/29 21:49
 */
public class ExercisesInfo {
    private static ExercisesInfo sMExercisesInfo;
    private List<Exercises> mExescls;
    private ExescisesService service;
    private ExescisesFragment fragment;


    private ExercisesInfo(ExescisesFragment fragment){
        this.fragment=fragment;
        service=new ExescisesService(fragment.getContext());
        mExescls =new ArrayList<>();
    }
    //请求题目资源
    public  void loadExecises(){
        service.getEsescises(mExescls);
    }
    //刷新题库视图
    public void updateView(){
        fragment.updateView(mExescls);
    }

    public List<Exercises> getExescls() {
        return mExescls;
    }

    public static ExercisesInfo get(ExescisesFragment fragment){
        if(sMExercisesInfo !=null){
            return sMExercisesInfo;
        }
        return sMExercisesInfo = new ExercisesInfo(fragment);
    }
}
