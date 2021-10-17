package com.example.projectmcc;

import com.google.android.gms.maps.model.LatLng;

public class BuildURL {
    private String url="https://api.openweathermap.org/data/2.5/onecall?";
    private String appid= "45ddd3983dea639831f30bda8cbcb8e9";

    public String build(LatLng latLng)
    {
        String tempurl=url+
                "lat="+latLng.latitude+"&lon="+latLng.longitude+
                "&exclude=minutely,hourly,alerts"+
                "&appid="+appid;
        return tempurl;
    }

}
