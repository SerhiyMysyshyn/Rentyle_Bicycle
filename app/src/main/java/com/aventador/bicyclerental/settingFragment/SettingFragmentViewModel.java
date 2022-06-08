package com.aventador.bicyclerental.settingFragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aventador.bicyclerental.settingFragment.list.ListObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SettingFragmentViewModel extends AndroidViewModel {
    private final SettingFragmentRepository settingFragmentRepository = new SettingFragmentRepository();
    @SuppressLint("StaticFieldLeak")
    public Context context;

    public SettingFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    MutableLiveData<List<ListObject>> listData = new MutableLiveData<>();

    public void setBicycleList(){
        settingFragmentRepository.setBicycleList(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<ListObject>>() {
                    @Override
                    public void onSuccess(List<ListObject> listObjects) {
                        listData.setValue(listObjects);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    public void selectBicycle(int position){
        settingFragmentRepository.selectBicycle(context, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<ListObject>>() {
                    @Override
                    public void onSuccess(List<ListObject> listObjects) {
                        listData.setValue(listObjects);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
