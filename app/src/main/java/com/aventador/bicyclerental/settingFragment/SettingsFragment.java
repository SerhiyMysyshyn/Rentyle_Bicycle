package com.aventador.bicyclerental.settingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.mainActivity.MainActivity;
import com.aventador.bicyclerental.settingFragment.list.ListAdapter;
import com.aventador.bicyclerental.settingFragment.list.ListClickListener;
import com.aventador.bicyclerental.settingFragment.list.ListObject;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {
    private SettingFragmentViewModel settingFragmentViewModel;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        settingFragmentViewModel = new ViewModelProvider(this).get(SettingFragmentViewModel.class);


        initViewComponents(view);

        listAdapter = new ListAdapter(view.getContext());
        recyclerView.setAdapter(listAdapter);


        settingFragmentViewModel.setBicycleList();
        settingFragmentViewModel.listData.observe(getViewLifecycleOwner(), new Observer<List<ListObject>>() {
            @Override
            public void onChanged(List<ListObject> listObjects) {
                listAdapter.updateList(listObjects);
            }
        });



        recyclerView.addOnItemTouchListener(new ListClickListener(view.getContext(), new ListClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                settingFragmentViewModel.selectBicycle(position);
            }
        }));

        return view;
    }



    public void initViewComponents(View view){
        recyclerView = view.findViewById(R.id.bicycleRecyclerView);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
    }
}