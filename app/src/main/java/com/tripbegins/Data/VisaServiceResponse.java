package com.tripbegins.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VisaServiceResponse {
    @SerializedName("country")
    public List<Country> country = new ArrayList<>();
    @SerializedName("visa")
    public List<VisaServices> visa = new ArrayList<>();
    @SerializedName("success")
    public String success="";
}
