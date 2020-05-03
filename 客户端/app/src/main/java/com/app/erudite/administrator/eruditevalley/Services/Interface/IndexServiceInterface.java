package com.app.erudite.administrator.eruditevalley.Services.Interface;


import com.app.erudite.administrator.eruditevalley.Services.Entity.CycleEntity;
import com.app.erudite.administrator.eruditevalley.Services.Entity.IndexListEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IndexServiceInterface {
    @GET("static/data/index/index.json")
    Observable<CycleEntity> getCycle();
    @GET("static/data/index/indexlist.json")
    Observable<IndexListEntity> getIndexlist();
    @GET("static/images/index/{imagename}")
    Observable<ResponseBody> getCycleimage(@Path("imagename") String imagename);
    @GET("static/images/indexlist/{imagename}")
    Observable<ResponseBody>getListImage(@Path("imagename") String imagename);
}
