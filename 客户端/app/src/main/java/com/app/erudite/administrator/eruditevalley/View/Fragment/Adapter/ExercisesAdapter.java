package com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.erudite.administrator.eruditevalley.Model.Exercises;
import com.app.erudite.administrator.eruditevalley.R;

import java.util.List;


/**
 * @ClassName: ExescisesAdapter
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/4/29 21:47
 */
public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {
    private List<Exercises> mExercises;
    ExercisesClicke listener;
    private int[] btnResource={R.drawable.exercises_bg_1,R.drawable.exercises_bg_2,R.drawable.exercises_bg_3,R.drawable.exercises_bg_4};
    public ExercisesAdapter(List<Exercises> exercises,ExercisesClicke listenter){
        this.mExercises = exercises;
        this.listener=listenter;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private Button itemExercisesId;
        private TextView itemExercisesTitle;
        private TextView itemExercisesSum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemExercisesId = itemView.findViewById(R.id.item_exercises_id);
            itemExercisesTitle =itemView.findViewById(R.id.item_exercises_title);
            itemExercisesSum = itemView.findViewById(R.id.item_exercises_sum);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exercises_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemExercisesId.setBackgroundResource(btnResource[position%4]);
        holder.itemExercisesId.setText(String.valueOf(mExercises.get(position).getId()));
        holder.itemExercisesTitle.setText(mExercises.get(position).getTitle());
        holder.itemExercisesSum.setText(mExercises.get(position).getSum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnExercisesClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public void updateData(List<Exercises> list){
        this.mExercises =list;
    }


    //item点击监听器
    public static interface ExercisesClicke{
        void OnExercisesClick(int position);
    }
}
