package com.example.projectmcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeatherListView extends ArrayAdapter<mData> {

    private Context mcontext;
    private int mResource;
    public WeatherListView(Context context, int resource, mData[] objects)
    {
        super(context,resource,objects);
        mcontext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(mcontext);

        convertView=inflater.inflate(mResource,parent,false);

        ImageView iv_condition=convertView.findViewById(R.id.other_icon);
        TextView date=convertView.findViewById(R.id.date);
        TextView condition=convertView.findViewById(R.id.condition);
        TextView maxTemp=convertView.findViewById(R.id.maxTemp);
        TextView minTemp=convertView.findViewById(R.id.mintemp);

        if(getItem(position)==null)
            return null;

        String str_cond=getItem(position).getCondition();
        String str_maxTemp=getItem(position).getMaxTemp();
        String str_minTemp=getItem(position).getMinTemp();

        if(str_cond=="Rain")
            iv_condition.setImageResource(R.drawable.ic_rain);
        else if(str_cond=="Clear")
            iv_condition.setImageResource(R.drawable.ic_clear);
        else if(str_cond=="Cloudy")
            iv_condition.setImageResource(R.drawable.ic_cloudy);
        else if(str_cond=="Storm")
            iv_condition.setImageResource(R.drawable.ic_storm);
        else if(str_cond=="Snow")
            iv_condition.setImageResource(R.drawable.ic_snow);
        else if(str_cond=="Thunderstorm")
            iv_condition.setImageResource(R.drawable.ic_storm);

        condition.setText(str_cond);
        maxTemp.setText(str_maxTemp);
        minTemp.setText(str_minTemp);
        date.setText("01/07/2000");

        return convertView;
    }
}
