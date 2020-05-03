package com.app.erudite.administrator.eruditevalley.Services.Interface;

import com.app.erudite.administrator.eruditevalley.Services.Entity.ExercisesEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @ClassName: ExescisesServiceInterface
 * @Description: 负责服务请求接口
 * @Author: 小污
 * @Date: 2020/4/30 11:26
 */
public interface ExescisesServiceInterface {
    @GET("static/data/exescises/exescises.json")
    Observable<ExercisesEntity> getEsescises();
}
