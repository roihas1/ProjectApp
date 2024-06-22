package com.example.projectapp.model

data class FirstDataResponse (
    val lowRisk: Risk,
    val mediumRisk: Risk,
    val highRisk: Risk
    )
data class Risk(

    val meanYield:Double,
    val standardDeviation: Double,
    val min:Double,
    val firstQ: Double,
    val median: Double,
    val thirdQ: Double,
    val max: Double,

    )