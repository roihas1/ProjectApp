package com.example.projectapp.model

import com.google.gson.annotations.SerializedName

data class FirstDataResponse (
    @SerializedName("Low Risk") val lowRisk: Risk,
    @SerializedName("Medium Risk") val mediumRisk: Risk,
    @SerializedName("High Risk") val highRisk: Risk

    )


data class Risk(
//    val level:String,
    @SerializedName("Mean Yield") val meanYield: Double,
    @SerializedName("Standard Deviation") val standardDeviation: Double,
    @SerializedName("Min") val min: Double,
    @SerializedName("25%(Q1)") val q1: Double,
    @SerializedName("50%(Median)") val median: Double,
    @SerializedName("75%(Q3)") val q3: Double,
    @SerializedName("Max") val max: Double
//    val sharpeRatio:Double
    )