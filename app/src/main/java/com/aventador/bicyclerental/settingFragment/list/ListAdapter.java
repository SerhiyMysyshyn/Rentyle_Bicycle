package com.aventador.bicyclerental.settingFragment.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    public List<ListObject> bicycleList = new ArrayList<>();

    public Context context;

    public ListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        return new ListAdapter.ViewHolder(v);
    }

    public void updateList(List<ListObject> _bicycleList) {
        bicycleList = _bicycleList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.backgroundRes.setBackgroundDrawable(bicycleList.get(position).getBackgroundImage());
        holder.bicycle_img.setImageDrawable(bicycleList.get(position).getImage());
        holder.bicycle_type.setImageDrawable(getTypeDrawable(bicycleList.get(position).getType()));
        holder.bicycle_name.setText(bicycleList.get(position).getName());
    }

    private Drawable getTypeDrawable(String type){
        Drawable newDraw = null;
        switch (type){
            case "Міські":
                newDraw = context.getDrawable(R.drawable.ic_city);
                break;
            case "Електро":
                newDraw = context.getDrawable(R.drawable.ic_electro_bike);
                break;
            case "Дитячі":
                newDraw = context.getDrawable(R.drawable.ic_kids);
                break;
        }
        return newDraw;
    }

    @Override
    public int getItemCount() {
        return bicycleList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView bicycle_img;
        ImageView bicycle_type;
        RelativeLayout backgroundRes;
        TextView bicycle_name;

        ViewHolder(View itemView) {
            super(itemView);
            bicycle_type = itemView.findViewById(R.id.bicycle_type);
            bicycle_name = itemView.findViewById(R.id.bicycle_name);
            backgroundRes = itemView.findViewById(R.id.item_selectable_background);
            bicycle_img = itemView.findViewById(R.id.bicycle_img);
        }

    }
}
