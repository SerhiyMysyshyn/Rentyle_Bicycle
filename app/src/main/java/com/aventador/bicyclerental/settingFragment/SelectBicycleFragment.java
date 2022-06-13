package com.aventador.bicyclerental.settingFragment;

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
import com.aventador.bicyclerental.settingFragment.list.ListAdapter;
import com.aventador.bicyclerental.settingFragment.list.ListClickListener;
import com.aventador.bicyclerental.settingFragment.list.ListObject;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class SelectBicycleFragment extends BottomSheetDialogFragment {
    private SelectBicycleFragmentViewModel selectBicycleFragmentViewModel;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    private Button goBtn , time30minBtn, time1hourBtn, time3hourBtn, time6hourBtn, time12hourBtn, time24hourBtn;

    private int SELECT_TIME;


    public SelectBicycleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_bike_dialog_view, container, false);

        selectBicycleFragmentViewModel = new ViewModelProvider(this).get(SelectBicycleFragmentViewModel.class);


        initViewComponents(view);

        listAdapter = new ListAdapter(view.getContext());
        recyclerView.setAdapter(listAdapter);


        selectBicycleFragmentViewModel.setBicycleList();
        selectBicycleFragmentViewModel.listData.observe(getViewLifecycleOwner(), new Observer<List<ListObject>>() {
            @Override
            public void onChanged(List<ListObject> listObjects) {
                listAdapter.updateList(listObjects);
            }
        });
//--------------------------------------------------------------------------------------------------

        time30minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time30minBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 0;
            }
        });

        time1hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time1hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 1;
            }
        });

        time3hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time3hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 2;
            }
        });

        time6hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time6hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 3;
            }
        });

        time12hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time12hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 4;
            }
        });

        time24hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time24hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 5;
            }
        });

//--------------------------------------------------------------------------------------------------


        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
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

    public void disableTimeButtons(){
        time30minBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time1hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time3hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time6hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time12hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time24hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
    }



    public void initViewComponents(View view){
        goBtn = view.findViewById(R.id.go_button);

        time30minBtn = view.findViewById(R.id.button);
        time1hourBtn = view.findViewById(R.id.button2);
        time3hourBtn = view.findViewById(R.id.button3);
        time6hourBtn = view.findViewById(R.id.button4);
        time12hourBtn = view.findViewById(R.id.button5);
        time24hourBtn = view.findViewById(R.id.button6);

        recyclerView = view.findViewById(R.id.bicycleRecyclerView);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
    }
}
