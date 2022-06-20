package com.aventador.bicyclerental.selectBicycleFragment;

import android.content.Context;

import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.selectBicycleFragment.list.ListObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class SelectBicycleFragmentRepository {

    public List<ListObject> createDataList(Context context){
        ListObject bicycle1 = new ListObject("Orbea Capre","City", context.getResources().getDrawable(R.drawable.c_orbea_capre), context.getResources().getDrawable(R.drawable.custom_list_background));
        ListObject bicycle2 = new ListObject("Retro Black","City", context.getResources().getDrawable(R.drawable.c_retro_black), context.getResources().getDrawable(R.drawable.custom_list_background));
        ListObject bicycle3 = new ListObject("Haibike Sduro","Electro", context.getResources().getDrawable(R.drawable.e_haibike_sduro), context.getResources().getDrawable(R.drawable.custom_list_background));
        ListObject bicycle4 = new ListObject("Orbea Keram","Electro", context.getResources().getDrawable(R.drawable.e_orbea_keram), context.getResources().getDrawable(R.drawable.custom_list_background));
        ListObject bicycle5 = new ListObject("Ghost Powerkid","Kids", context.getResources().getDrawable(R.drawable.k_ghost_powerkid), context.getResources().getDrawable(R.drawable.custom_list_background));
        ListObject bicycle6 = new ListObject("Ghost Kato","Kids", context.getResources().getDrawable(R.drawable.k_ghost_kato), context.getResources().getDrawable(R.drawable.custom_list_background));

        List<ListObject> list = new ArrayList<>();
            list.add(bicycle1);
            list.add(bicycle2);
            list.add(bicycle3);
            list.add(bicycle4);
            list.add(bicycle5);
            list.add(bicycle6);
        return list;
    }


    Single<List<ListObject>> setBicycleList(Context context, String selectedName){
      Single<List<ListObject>> dataList = Single.fromCallable(new Callable<List<ListObject>>() {
       @Override
       public List<ListObject> call() throws Exception {
           if (selectedName.equals("") || selectedName==null){
               return createDataList(context);
           }else {
               List<ListObject> newList = createDataList(context);
               for (int i = 0; i < newList.size(); i++){
                   if (newList.get(i).getName().equals(selectedName)){
                       newList.get(i).setBackgroundImage(context.getResources().getDrawable(R.drawable.custom_selected_item));
                   }
               }
               return newList;
           }
       }
      }).subscribeOn(Schedulers.io());
      return dataList;
    }

    Single<List<ListObject>> selectBicycle(Context context, int position){
      Single<List<ListObject>> dataList = Single.fromCallable(new Callable<List<ListObject>>() {
          @Override
          public List<ListObject> call() throws Exception {
              List<ListObject> newList = createDataList(context);
              newList.get(position).setBackgroundImage(context.getResources().getDrawable(R.drawable.custom_selected_item));
              return newList;
          }
      }).subscribeOn(Schedulers.io());
      return dataList;
    }

    Single<String> getNameByPosition(Context context, int position){
        Single<String> pos = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return createDataList(context).get(position).getName();
            }
        }).subscribeOn(Schedulers.io());
        return pos;
    }
}
