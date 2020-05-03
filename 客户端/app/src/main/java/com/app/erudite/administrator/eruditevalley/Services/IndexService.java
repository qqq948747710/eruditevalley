package com.app.erudite.administrator.eruditevalley.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.app.erudite.administrator.eruditevalley.Model.Cycleinfo;
import com.app.erudite.administrator.eruditevalley.Model.IndexInfo;
import com.app.erudite.administrator.eruditevalley.Model.IndexListinfo;
import com.app.erudite.administrator.eruditevalley.Services.Entity.CycleEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.IndexListEntity;
import com.app.erudite.administrator.eruditevalley.Services.Interface.IndexServiceInterface;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class IndexService  extends Service{
    IndexServiceInterface server;
    public IndexService(Context context) {
        super(context);
        server=retrofit.create(IndexServiceInterface.class);
    }


 //获取主页json信息
    public void getindexlist(final List<IndexListinfo> infos, final int queue){
        server.getIndexlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexListEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IndexListEntity indexListEntity) {
                        for(int i=0;i<indexListEntity.getResult().size();i++){
                            String title=indexListEntity.getResult().get(i).getTitle();
                            String imagename=indexListEntity.getResult().get(i).getImagename();
                            String videoname=indexListEntity.getResult().get(i).getVideoname();
                            IndexListinfo item=new IndexListinfo(title,imagename,videoname);
                            infos.add(item);
                        }
                        addqueue(queue);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获取轮播json信息
    public void getCycle(final List<Cycleinfo> infos, final int queue){
        server.getCycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CycleEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CycleEntity cycleEntity) {
                        for(int i=0;i<cycleEntity.getResult().size();i++){
                            String title=cycleEntity.getResult().get(i).getTitle();
                            String imagename=cycleEntity.getResult().get(i).getImagename();
                            Cycleinfo item=new Cycleinfo(title,imagename);
                            infos.add(item);
                        }
                        addqueue(queue);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获取列表图片
    public void getIndexlistimage(String imagename, final int queue, final List<IndexListinfo> infos, final int index){
        server.getListImage(imagename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Bitmap bp=null;
                        byte[] bytes;
                        try {
                            bytes=responseBody.bytes();
                            bp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        infos.get(index).setBp(bp);
                        addqueue(queue);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("indeximageerr","indeximageerr");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获取轮播图片信息
    public void getCycleImage(String imagename, final int queue, final List<Cycleinfo> infos, final int index){
        server.getCycleimage(imagename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Bitmap bp=null;
                        byte[] bytes;
                        try {
                            bytes=responseBody.bytes();
                            bp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        infos.get(index).setBp(bp);
                        addqueue(queue);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("cycimageerr","err");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //任务队列完成回调，队列1完成请求图片，队列2完成请求刷新视图
    @Override
    protected void onFinishqueue(int queue) {
        if(queue==0){
            IndexInfo.get(null).loadNetworkResource();
            return;
        }else if (queue==1){
            IndexInfo.get(null).updateView();
            return;
        }
        //其他队列进行刷新列表
        IndexInfo.get(null).updateLIst();
    }
}
