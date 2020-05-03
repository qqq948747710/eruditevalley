package com.app.erudite.administrator.eruditevalley.Model;



import com.app.erudite.administrator.eruditevalley.Services.IndexService;
import com.app.erudite.administrator.eruditevalley.View.Fragment.IndexFragment;


import java.util.ArrayList;
import java.util.List;


public class IndexInfo{
    private static IndexInfo mIndexinfo;
    private List<Cycleinfo> mCycleinfos;
    private List<IndexListinfo> mIndexlists;
    private IndexService service;
    private IndexFragment fragment;

    private IndexInfo(IndexFragment fragment){
        this.fragment=fragment;
        mCycleinfos =new ArrayList<>();
        mIndexlists=new ArrayList<>();
        service=new IndexService(fragment.getContext());
        //开启服务任务队列
        service.setqueue(2);
        loadNetInfo();
    }
    public static IndexInfo get(IndexFragment fragment){
        if (mIndexinfo!=null){
            return mIndexinfo;
        }
        return mIndexinfo=new IndexInfo(fragment);
    }
    //请求json资源
    public void loadNetInfo(){
        service.getCycle(mCycleinfos,0);
        service.getindexlist(mIndexlists,0);
    }
    //请求图片资源,等等json信息请求完后再增加队列
    public void loadNetworkResource(){
        service.setqueue(mCycleinfos.size()+mIndexlists.size());
        for (int i=0;i<mCycleinfos.size();i++){
            service.getCycleImage(this.mCycleinfos.get(i).getImagename(),1,mCycleinfos,i);
        }
        for(int i=0;i<mIndexlists.size();i++){
            service.getIndexlistimage(this.mIndexlists.get(i).getImagename(),1,mIndexlists,i);
        }
    }
    //对视图层做刷新
    public void updateView(){
        fragment.updateView(mCycleinfos,mIndexlists);
    }
    //只对列表进行刷新，因为列表如果多需要使用队列请求再做刷新
    public void updateLIst(){
        fragment.updateListView(mIndexlists);
    }
    public List<Cycleinfo> getCycleinfos(){
        return mCycleinfos;
    }

    public List<IndexListinfo> getIndexlists() {
        return mIndexlists;
    }
}
