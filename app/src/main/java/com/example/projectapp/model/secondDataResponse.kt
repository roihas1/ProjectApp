package com.example.projectapp.model

data class StockWeightsResponse(
    val Safest: Map<String, Double>,
    val Sharpe: Map<String, Double>,
    val MaxReturns: Map<String, Double>
)
