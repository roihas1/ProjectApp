package com.example.projectapp.model

data class addInvestmentRequest(
    var amount: Int,
    var stocks_weights: List<Double>,
)
