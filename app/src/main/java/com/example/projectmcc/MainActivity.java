package com.example.projectmcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import com.google.android.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient client;
    LatLng latLng;
    DecimalFormat df=new DecimalFormat("#.##");
    String URL="";

    static RecyclerView mlistView;
    static TextView tv_condition,tv_max,tv_min;
    static ImageView iv_condition;
    ImageView iv_condition2;
    public  mData[] dataList=new mData[7];
    WeatherListView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        client = LocationServices.getFusedLocationProviderClient(this);

        tv_condition=(TextView) findViewById(R.id.tv_condition);
        tv_max=(TextView)findViewById(R.id.tv_temphigh);
        tv_min=(TextView)findViewById(R.id.tv_templow);
        iv_condition=(ImageView)findViewById(R.id.iv_condition);
        iv_condition2=(ImageView)findViewById(R.id.iv_condition);
        mlistView= findViewById(R.id.listView);


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        getMyLocation();
                        //createView();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();

        ExecutorService executor= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());
        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                WeatherListView adapter = new WeatherListView(MainActivity.this,R.layout.adapter_view_layout, dataList);
                mlistView.setAdapter(adapter);
            }
        });*/

    }

    public void createView(){

        recylerAdapter adapter = new recylerAdapter(dataList);

        mlistView.setAdapter(adapter);
    }

    public void getMyLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(final Location location)
            {
                latLng=new LatLng(Double.parseDouble(df.format(location.getLatitude())),Double.parseDouble(df.format(location.getLongitude())));
                //Log.e("TAG", "onSuccess: "+latLng.latitude+" "+latLng.longitude);

                BuildURL buildURL = new BuildURL();
                URL=buildURL.build(latLng);

                ExecutorService executor= Executors.newSingleThreadExecutor();
                Handler handler=new Handler(Looper.getMainLooper());

                final mData[][] data = {new mData[8]};

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        FetchData fetchData=new FetchData();
                        FetchData.getData(getApplicationContext(),URL);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                data[0] =FetchData.getter();


                            }
                        });
                    }
                });
            }
            });
    }

    public void setter(mData data,int index)
    {
        dataList[index]=data;

    }

    public void elementsSetter()
    {
        for(int i=0;i<1;i++)
        {
            mData data=dataList[i];
            tv_condition.setText(data.condition);
            tv_max.setText(data.currentTemp);
            tv_min.setText(data.minTemp);

            String str_cond=data.condition;
            if(str_cond=="Rain")
                iv_condition2.setImageResource(R.drawable.ic_rain);
            else if(str_cond=="Clear")
                iv_condition2.setImageResource(R.drawable.ic_clear);
            else if(str_cond=="Clouds")
                iv_condition2.setImageResource(R.drawable.ic_cloudy);
            else if(str_cond=="Storm")
                iv_condition2.setImageResource(R.drawable.ic_storm);
            else if(str_cond=="Snow")
                iv_condition2.setImageResource(R.drawable.ic_snow);
            else if(str_cond=="Thunderstorm")
                iv_condition2.setImageResource(R.drawable.ic_storm);
            else if(str_cond=="Haze")
                iv_condition2.setImageResource(R.drawable.ic_fog);
        }
        return;

    }
}