package com.example.weathearquality;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.google.gson.JsonElement;

public interface WeatherAPI {

    @GET("{ville}/?token=be7e58e0cac0ed2d5b17369bf1fee76491a7db24")
    Call<JsonElement> dataVille(@Path(("ville")) String ville);
}
