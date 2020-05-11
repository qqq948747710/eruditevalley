package com.app.erudite.administrator.eruditevalley.View.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.erudite.administrator.eruditevalley.Model.Cycleinfo;
import com.app.erudite.administrator.eruditevalley.Model.IndexInfo;
import com.app.erudite.administrator.eruditevalley.Model.IndexListinfo;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.View.CycleView.CycleViewPager;
import com.app.erudite.administrator.eruditevalley.View.Fragment.Adapter.IndexListAdapter;
import com.app.erudite.administrator.eruditevalley.View.VideoActivity;


import java.util.List;

public class IndexFragment extends Fragment implements CycleViewPager.ImageCycleViewListener, IndexListAdapter.IndexClick {
    private CycleViewPager mCycleViewPager;
    private RecyclerView mList;
    private IndexListAdapter mlistAdapter;
    private IndexInfo mIndexInfo;
    private static IndexFragment mIndexFragment;
    public IndexFragment() {
        super();
    }
    public static IndexFragment get(){
        if(mIndexFragment!=null){
            return mIndexFragment;
        }else {
            mIndexFragment=new IndexFragment();
        }
        return mIndexFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_index,container,false);
       mCycleViewPager= (CycleViewPager) view.findViewById(R.id.index_cycleview);
       mList=(RecyclerView)view.findViewById(R.id.index_listview);
       initModul();
       initListData();
       return view;
    }
    private void initModul(){
        mIndexInfo=IndexInfo.get(this);
    }

    private void initListData(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        mList.setLayoutManager(gridLayoutManager);
    }

    /**
     * 更新轮播View,由网络回调model"Indexinfor"后，调用该函数刷新。，
     */
    public void updateView(List<Cycleinfo> cycleinfos, List<IndexListinfo> indexListinfos) {
        //设置选中和未选中时的图片
        mCycleViewPager.setIndications(R.mipmap.ad_select, R.mipmap.ad_unselect);
        //设置轮播间隔时间
        mCycleViewPager.setData(cycleinfos,this);
        mlistAdapter=new IndexListAdapter(indexListinfos,this);
        mList.setAdapter(mlistAdapter);
    }
    //更新indexlis列表
    public void updateListView(List<IndexListinfo> indexListinfos){
        mlistAdapter.updateData(indexListinfos);
        mlistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageClick(Cycleinfo info, int position, View imageView) {
        Log.i("tap",position+"被点击了！");
    }

    @Override
    public void OnClickIndex(int position) {
        VideoActivity.loadActivity(getContext(),mIndexInfo.getIndexlists().get(position));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView(mIndexInfo.getCycleinfos(),mIndexInfo.getIndexlists());
    }
}
