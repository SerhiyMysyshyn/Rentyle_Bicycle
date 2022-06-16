package com.aventador.bicyclerental.selectFragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aventador.bicyclerental.selectFragment.list.ListObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SelectBicycleFragmentViewModel extends AndroidViewModel {
    private final SelectBicycleFragmentRepository selectBicycleFragmentRepository = new SelectBicycleFragmentRepository();
    @SuppressLint("StaticFieldLeak")
    public Context context;

    public SelectBicycleFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    MutableLiveData<List<ListObject>> listData = new MutableLiveData<>();
    MutableLiveData<String> bicycleName = new MutableLiveData<>();

    public void setBicycleList(String selectedName){
        selectBicycleFragmentRepository.setBicycleList(context, selectedName)
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
        selectBicycleFragmentRepository.selectBicycle(context, position)
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

        selectBicycleFragmentRepository.getNameByPosition(context, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        bicycleName.setValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        bicycleName.setValue(null);
                    }
                });
    }
}
