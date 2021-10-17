package com.example.projectmcc;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class FetchData  extends Application {
    public static mData data[]=new mData[7];



    public static mData[] getData(Context mcontext, String tempurl)
    {
        DecimalFormat df=new DecimalFormat("#");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     String condition;
                     double currentTemp;
                     double minTemp;
                     double maxTemp;
                     double feelsLike;
                     double pressure;
                     double humidity;
                     String date;

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonDaily=jsonResponse.getJSONArray("daily");
                    //today
                    JSONObject jsonOcurrent=jsonResponse.getJSONObject("current");

                    condition=jsonOcurrent.getJSONArray("weather").getJSONObject(0).getString("main");
                    currentTemp=jsonOcurrent.getDouble("temp");
                    maxTemp=jsonDaily.getJSONObject(0).getJSONObject("temp").getDouble("max");
                    minTemp=jsonDaily.getJSONObject(0).getJSONObject("temp").getDouble("min");
                    feelsLike=jsonOcurrent.getDouble("feels_like");
                    pressure=jsonOcurrent.getDouble("pressure");
                    humidity=jsonOcurrent.getDouble("humidity");
                    date=jsonDaily.getJSONObject(0).getString("dt");

//                    data[0]=new mData(condition,df.format(currentTemp-275),df.format(minTemp-275),df.format(maxTemp-275),df.format(feelsLike-275),df.format(pressure),df.format(humidity),date);
//                    data[0]= new mData(condition,df.format(currentTemp-275),df.format(minTemp-275),df.format(maxTemp-275),df.format(feelsLike-275),df.format(pressure),df.format(humidity),date);
                    data[0] = new mData(condition,df.format(currentTemp-275),df.format(minTemp-275),df.format(maxTemp-275),df.format(feelsLike-275),df.format(pressure),df.format(humidity),date);
                    //rest
                    for(int i=1;i<7;i++)
                    {
                        condition=jsonDaily.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                        currentTemp=-275;
                        maxTemp=jsonDaily.getJSONObject(i).getJSONObject("temp").getDouble("max");
                        minTemp=jsonDaily.getJSONObject(i).getJSONObject("temp").getDouble("min");
                        feelsLike=-275;
                        pressure=jsonDaily.getJSONObject(i).getDouble("pressure");
                        humidity=jsonDaily.getJSONObject(i).getDouble("humidity");
                        date=jsonDaily.getJSONObject(i).getString("dt");
                        data[i]=new mData(condition,df.format(currentTemp-275),df.format(minTemp-275),df.format(maxTemp-275),df.format(feelsLike-275),df.format(pressure),df.format(humidity),date);
                    }
                    MainActivity mainActivity=new MainActivity();
                    for(int i=0;i<7;i++)
                    {
                        mainActivity.setter(data[i],i);
                    }


                    mainActivity.elementsSetter();

                    mainActivity.createView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext,error.toString().trim(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


        return data.clone();
    }

    public static com.example.projectmcc.mData[] getter()
    {
        mData[] tmp=data;
        return data.clone();
    }

}
