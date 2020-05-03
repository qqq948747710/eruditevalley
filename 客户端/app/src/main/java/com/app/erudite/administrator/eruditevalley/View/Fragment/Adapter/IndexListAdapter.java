package com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.erudite.administrator.eruditevalley.Model.IndexListinfo;
import com.app.erudite.administrator.eruditevalley.R;

import java.util.List;

public class IndexListAdapter extends RecyclerView.Adapter<IndexListAdapter.ViewHolder> {
    private List<IndexListinfo> mIndexListinfos;
    private IndexClick mListener;
    public IndexListAdapter(List<IndexListinfo> indexListinfos,IndexClick listener){
        mIndexListinfos=indexListinfos;
        mListener=listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.index_item_title);
            image=itemView.findViewById(R.id.index_item_img);
        }
    }

    public void updateData(List<IndexListinfo> infos){
        this.mIndexListinfos=infos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(mIndexListinfos.get(position).getTitle());
        holder.image.setImageBitmap(mIndexListinfos.get(position).getBp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClickIndex(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIndexListinfos.size();
    }


    public interface  IndexClick{
        void OnClickIndex(int position);
    }
}
