package com.example.projectmcc;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class recylerAdapter extends RecyclerView.Adapter<recylerAdapter.Holder> {

    private final mData[] list;

    recylerAdapter(mData[] list) {
        this.list = list;
    }

    @NonNull
    @Override
    public recylerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull recylerAdapter.Holder holder, int position) {
        Log.d("aaa", "onBindViewHolder: " + list[position].getCondition());
        holder.condition.setText(list[position].getCondition());
        holder.maxTemp.setText(list[position].getMaxTemp());
        holder.minxTemp.setText(list[position].getMinTemp());
        holder.condition.setText(list[position].getCondition());
        holder.date.setText(parseDate(list[position].getDate()));

        String str_cond = list[position].getCondition();
        int resId = -1;

        switch (str_cond.toLowerCase()) {
            case "rain":
                resId = R.drawable.ic_rain;
                break;
            case "clear":
                resId = R.drawable.ic_clear;
                break;
            case "clouds":
                resId = R.drawable.ic_cloudy;
                break;
            case "storm":
            case "thunderstorm":
                resId = R.drawable.ic_storm;
                break;
            case "snow":
                resId = R.drawable.ic_snow;
                break;
            default:
            case "haze":
                resId = R.drawable.ic_fog;
                break;
        }
        holder.iv_condition.setImageDrawable(ContextCompat.getDrawable(holder.condition.getContext(), resId));

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView date;
        TextView condition;
        TextView maxTemp;
        TextView minxTemp;
        ImageView iv_condition;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date =  itemView.findViewById(R.id.date);
            condition = itemView.findViewById(R.id.condition);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minxTemp = itemView.findViewById(R.id.mintemp);
            iv_condition = itemView.findViewById(R.id.other_icon);
        }
    }

    String parseDate(String ip)
    {
        ip+="000";
        Date date= new Date(Long.parseLong(ip));
        return  date.toString();
    }
}
