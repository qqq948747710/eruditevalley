package com.app.erudite.administrator.eruditevalley.Services;

import android.content.Context;

import com.app.erudite.administrator.eruditevalley.Model.Exercises;
import com.app.erudite.administrator.eruditevalley.Model.ExercisesInfo;
import com.app.erudite.administrator.eruditevalley.Services.Entity.ExercisesEntity;
import com.app.erudite.administrator.eruditevalley.Services.Interface.ExescisesServiceInterface;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: ExescisesService
 * @Description: java类作用描述
 * @Author: 小污
 * @Date: 2020/4/30 11:18
 */
public class ExescisesService extends Service {
    ExescisesServiceInterface service;
    public ExescisesService(Context context) {
        super(context);
        service=retrofit.create(ExescisesServiceInterface.class);
        setqueue(1);
    }

    public void getEsescises(final List<Exercises> exescls){
        service.getEsescises()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExercisesEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ExercisesEntity exercisesEntity) {
                        if(exercisesEntity.getCode()==0){
                            List<ExercisesEntity.ResultBean> result = exercisesEntity.getResult();
                            for (ExercisesEntity.ResultBean item:result){
                                Exercises exercises =new Exercises();
                                exercises.setId(item.getId());
                                exercises.setSum(item.getSum());
                                exercises.setTitle(item.getTitle());
                                exercises.setContent(item.getContent());
                                exercises.setOption(item.getOption());
                                exercises.setRight(item.getRight());
                                exescls.add(exercises);
                            }
                            addqueue(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    protected void onFinishqueue(int queue) {
        if(queue==0){
            ExercisesInfo.get(null).updateView();
        }
    }
}
