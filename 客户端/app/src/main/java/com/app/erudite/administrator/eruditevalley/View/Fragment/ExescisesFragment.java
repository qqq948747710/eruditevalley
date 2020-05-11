package com.app.erudite.administrator.eruditevalley.View.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.erudite.administrator.eruditevalley.Model.Exercises;
import com.app.erudite.administrator.eruditevalley.Model.ExercisesInfo;
import com.app.erudite.administrator.eruditevalley.Model.UserInfo;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.View.ExercisesActivity;
import com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter.ExercisesAdapter;

import java.util.List;

/**
 * @ClassName: ExescisesFragment
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/4/28 15:57
 */
public class ExescisesFragment extends Fragment implements ExercisesAdapter.ExercisesClicke {
    private static ExescisesFragment sfragment;
    private ExercisesAdapter mMexescisesAdapter;
    private RecyclerView mexescisesList;
    private ExercisesInfo mMexescisesInfo;
    public ExescisesFragment(){
        super();
    }
    public static ExescisesFragment get(){
        if(sfragment!=null){
            return sfragment;
        }
        return  sfragment=new ExescisesFragment();
    }

    //数据准备完毕刷新视图
    public void updateView(List<Exercises> list){
        mMexescisesAdapter =new ExercisesAdapter(list,this);
        mexescisesList.setAdapter(mMexescisesAdapter);
    }
    //刷新list
    public void updateList(List<Exercises> list){
        mMexescisesAdapter.updateData(list);
        mMexescisesAdapter.notifyDataSetChanged();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_exercises,container,false);
        mexescisesList=view.findViewById(R.id.exercises_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        mexescisesList.setLayoutManager(linearLayoutManager);
        mMexescisesInfo = ExercisesInfo.get(this);
        mMexescisesInfo.loadExecises();
        return view;
    }



    @Override
    public void OnExercisesClick(int position) {
        if(UserInfo.unlogin()){
            Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
            return;
        }
        ExercisesActivity.loadActivity(getContext(),mMexescisesInfo.getExescls().get(position));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView(mMexescisesInfo.getExescls());
    }
}
