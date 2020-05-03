package com.app.erudite.administrator.eruditevalley.Services;

import android.content.Context;
import android.util.Log;

import com.app.erudite.administrator.eruditevalley.Config.AppConfig;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;



import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class Service {
    protected Retrofit retrofit;
    //队列号从0开始，按照设置队列顺序排列
    private ArrayList<Integer> index=new ArrayList<>();//任务队列计次索引。
    private ArrayList<Integer> maxindex=new ArrayList<>();//任务队列最大索引。
    private boolean isqueue=false;
    private PersistentCookieJar cookieJar;
    public Service(Context context){
        cookieJar=new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl(AppConfig.Services.baserurl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    //开启队列,并设置队列分支和队列最大值
    public void setqueue(int maxindex){
        this.maxindex.add(maxindex);
        this.index.add(0);
        this.isqueue=true;
    }
    /**任务队列增加，当超过说明资源都请求完成！
     * @param queue 队列分支列表
     * */
    protected void addqueue(int queue){
        if(queue>index.size()){
            Log.e("array","请求队列下标超出");
            return;
        }
        if(isqueue){
            int i=index.get(queue);
            index.set(queue,++i);
        }
        if(index.get(queue)==maxindex.get(queue)){
            onFinishqueue(queue);
        }
    }
    /**
     * 请求队列完成时候调用的生命周期
     * @param queue 完成的队列数
     */
    abstract protected void onFinishqueue(int queue);

}
