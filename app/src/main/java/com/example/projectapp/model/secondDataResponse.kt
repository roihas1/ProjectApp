package com.example.projectapp.model

import com.google.gson.annotations.SerializedName


data class StockWeightsResponse(
    val Safest: Map<String, Double>,
    val Sharpe: Map<String, Double>,
    @SerializedName("Max Returns") val MaxReturns: Map<String, Double>
)