package com.tripbegins.Network;

import com.tripbegins.Data.VisaServiceResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("get_country_visa.php")
    Call<VisaServiceResponse> getVisaServices();
}