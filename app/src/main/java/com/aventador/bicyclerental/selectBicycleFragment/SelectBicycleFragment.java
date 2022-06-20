package com.aventador.bicyclerental.selectBicycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.mainActivity.MainActivity;
import com.aventador.bicyclerental.selectBicycleFragment.list.ListAdapter;
import com.aventador.bicyclerental.selectBicycleFragment.list.ListClickListener;
import com.aventador.bicyclerental.selectBicycleFragment.list.ListObject;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class SelectBicycleFragment extends BottomSheetDialogFragment {
    private SelectBicycleFragmentViewModel selectBicycleFragmentViewModel;
    private MainActivity mainActivity;
    private SelectBicycleFragment selectBicycleFragment;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    private String selectedItem;
    private String SELECTED_ITEM_NAME;

    private Button goBtn;

    public SelectBicycleFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_bike_dialog_view, container, false);


        selectBicycleFragmentViewModel = new ViewModelProvider(this).get(SelectBicycleFragmentViewModel.class);

        initViewComponents(view);

        listAdapter = new ListAdapter(view.getContext());
        recyclerView.setAdapter(listAdapter);



        selectBicycleFragmentViewModel.setBicycleList(getSelectedItem());
        selectBicycleFragmentViewModel.listData.observe(getViewLifecycleOwner(), new Observer<List<ListObject>>() {
            @Override
            public void onChanged(List<ListObject> listObjects) {
                listAdapter.updateList(listObjects);
            }
        });



        selectBicycleFragmentViewModel.bicycleName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SELECTED_ITEM_NAME = s;
            }
        });



        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.notifyActivityToChangeData(SELECTED_ITEM_NAME);
                selectBicycleFragment.dismiss();
            }
        });



        recyclerView.addOnItemTouchListener(new ListClickListener(view.getContext(), new ListClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectBicycleFragmentViewModel.selectBicycle(position);
            }
        }));


        return view;
    }



    public void initViewComponents(View view){
        goBtn = view.findViewById(R.id.go_button);
        recyclerView = view.findViewById(R.id.bicycleRecyclerView);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
    }

    public void setViewForDisablePossibility(SelectBicycleFragment selectBicycleFragment){
        this.selectBicycleFragment = selectBicycleFragment;
    }
}
