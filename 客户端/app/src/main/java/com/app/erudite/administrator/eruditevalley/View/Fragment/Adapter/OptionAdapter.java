package com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.erudite.administrator.eruditevalley.R;

import java.util.List;

/**
 * @ClassName: OptionAdapter
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/5/1 12:27
 */
public class OptionAdapter extends ArrayAdapter<String> {
    private int resource;
    private int result;
    private boolean isoption=false;
    private List<String> list;
    private int[] icon_res={R.drawable.exercises_a,R.drawable.exercises_b,R.drawable.exercises_c,R.drawable.exercises_d};
    public OptionAdapter(@NonNull Context context,int textViewResourceId, @NonNull List<String> objects,int result) {
        super(context,textViewResourceId, objects);
        this.resource=textViewResourceId;
        this.list=objects;
        this.result=result;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        final Button Icon=view.findViewById(R.id.exercises_item_option_icon);
        final TextView text=view.findViewById(R.id.exercises_item_option_text);
        Icon.setBackgroundResource(icon_res[position]);
        text.setText(list.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isoption){
                    return;
                }
                if(position==result){
                    Icon.setBackgroundResource(R.drawable.exercises_right_icon);
                    isoption=true;
                }else{
                    Icon.setBackgroundResource(R.drawable.exercises_error_icon);
                    parent.getChildAt(result).findViewById(R.id.exercises_item_option_icon).setBackgroundResource(R.drawable.exercises_right_icon);
                    isoption=true;
                }
            }
        });
        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
