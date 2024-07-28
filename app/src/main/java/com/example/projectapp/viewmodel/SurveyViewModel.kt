package com.example.projectapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.RiskData
import com.example.projectapp.SessionManager
import com.example.projectapp.model.FirstDataResponse
import com.example.projectapp.model.Form1Request
import com.example.projectapp.model.Risk
import com.example.projectapp.model.StockWeightsResponse
import com.example.projectapp.model.form2Request

import com.example.projectapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Weight(
    val name:String,
    val weight:Double
    )
class SurveyViewModel : ViewModel() {
    private val totalQuestions = 7
    private val _answers = MutableStateFlow(mapOf<Int, String>())
    val answers: StateFlow<Map<Int, String>> = _answers.asStateFlow()

    private val _surveyState = MutableStateFlow<SurveyState>(SurveyState.Idle)
    val surveyState: StateFlow<SurveyState> = _surveyState.asStateFlow()

    private val _surveyState2 = MutableStateFlow<SurveyState>(SurveyState.Idle)
    val surveyState2: StateFlow<SurveyState> = _surveyState2.asStateFlow()

    private var _lastAnswers = MutableStateFlow(mapOf<Int, String>())
    val lastAnswers: StateFlow<Map<Int, String>> = _lastAnswers.asStateFlow()

    private val _riskData = MutableStateFlow<List<RiskData>>(emptyList())
    val riskData: StateFlow<List<RiskData>> = _riskData.asStateFlow()
    private val _stocksData = MutableStateFlow<List<Weight>>(emptyList())
    val stockData: StateFlow<List<Weight>> = _stocksData.asStateFlow()

    fun saveAnswer(questionNumber: Int, answer: String) {
        _answers.value = _answers.value.toMutableMap().apply {
            put(questionNumber, answer)
        }
    }

    fun getAnswer(questionNumber: Int): String = _answers.value[questionNumber] ?: ""

    fun isSurveyComplete(): Boolean = _answers.value.size == totalQuestions

    fun clearAnswers() {
        _lastAnswers.value = _answers.value
        _answers.value = emptyMap()
    }

    fun getLastAnswer(questionNumber: Int): String = _lastAnswers.value[questionNumber] ?: ""
    fun finalSurvey(){
        viewModelScope.launch {
            _surveyState2.value = SurveyState.Loading
            var answer_1 = 0
            var answer_2 = 0
            var answer_3 = 0
            var stocks_symbols = 0
            try{
                val currentAnswers = _answers.value
                when(currentAnswers[4]){
                    "Indexes(recommended)" -> stocks_symbols = 1
                    "Top indexes" -> stocks_symbols = 2
                    "Indexes and stocks" -> stocks_symbols = 3
                    "Top stocks" -> stocks_symbols = 4
                }
                when(currentAnswers[5]){
                    "0-2" -> answer_1 = 1
                    "2-4"-> answer_1 = 2
                    "4-40" -> answer_1 = 3
                }
                when(currentAnswers[6]){
                    "Low Risk"-> {
                        answer_2=1
                        answer_3 =1
                    }
                    "Medium Risk"-> {
                        answer_2 =2
                        answer_3 =2
                    }
                    "High Risk" -> {
                        answer_2 = 3
                        answer_3 = 3
                    }
                }
                val sumRisk = answer_1 + answer_2 + stocks_symbols
                val finalPortfolioType = when {
                    sumRisk in 0..3 -> "Safest"
                    sumRisk in 4..6 -> "Sharpe"
                    else -> "Max Returns"
                }


                val form2Request = form2Request(answer_1,answer_2,answer_3,stocks_symbols)
                RetrofitInstance.api.form2(form2Request)
                val plotDataResponse = RetrofitInstance.api.getPlotData2()


                _stocksData.value = plotDataResponse.body()?.let { convertToWeightList(it,finalPortfolioType) }!!
                _surveyState2.value = SurveyState.Success("ok")

            } catch (e: Exception) {0
                _surveyState2.value = SurveyState.Error("Exception occurred: ${e.message}")
            }
        }
    }
    fun convertToWeightList(stockWeightsResponse: StockWeightsResponse, typeOfRisk: String): List<Weight> {
        val stocks = when (typeOfRisk) {
            "Safest" -> stockWeightsResponse.Safest
            "Sharpe" -> stockWeightsResponse.Sharpe
            "Max Returns" -> stockWeightsResponse.MaxReturns
            else -> throw IllegalArgumentException("Invalid risk type: $typeOfRisk")
        }

        return stocks.map { (name, weight) ->
            Weight(name = name, weight = weight)
        }
    }
    fun submitSurvey(sessionManager: SessionManager) {
        viewModelScope.launch {
            _surveyState.value = SurveyState.Loading
            try {
                val currentAnswers = _answers.value
                val mlAnswer = if (currentAnswers[2] == "No") 0 else 1
                val modelAnswer = if (currentAnswers[3] == "Markowitz") 0 else 1


                    val form1Request = Form1Request(mlAnswer, modelAnswer)
                    RetrofitInstance.api.form1(form1Request)
                    val plotDataResponse = RetrofitInstance.api.getPlotData1()
                    _riskData.value = riskToList(plotDataResponse.body())
                    _surveyState.value = SurveyState.Success("ok")
                }
             catch (e: Exception) {
                _surveyState.value = SurveyState.Error("Exception occurred: ${e.message}")
            }
        }
    }

    private fun riskToList(body: FirstDataResponse?): List<RiskData> {
        return body?.let { response ->
            listOf(
                createRiskData("Low Risk", response.lowRisk, 0.864),
                createRiskData("Medium Risk", response.mediumRisk, 0.864),
                createRiskData("High Risk", response.highRisk, 0.864)
            )
        } ?: emptyList()
    }

    private fun createRiskData(title: String, risk: Risk, additionalMetric: Double): RiskData {
        return RiskData(
            level = title,
            meanYield = risk.meanYield,
            stdDev = risk.standardDeviation,
            min = risk.min,
            q1 = risk.q1,
            median = risk.median,
            q3 = risk.q3,
            max = risk.max,
            sharpeRatio = additionalMetric
        )
    }
}


sealed class SurveyState {
    object Idle : SurveyState()
    object Loading : SurveyState()
    data class Success(val data: String?) : SurveyState()
    data class Error(val message: String) : SurveyState()
}