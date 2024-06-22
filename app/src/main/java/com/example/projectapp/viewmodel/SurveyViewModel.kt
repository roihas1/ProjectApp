package com.example.projectapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projectapp.model.FirstDataResponse
import com.example.projectapp.model.Form1Request

import com.example.projectapp.network.RetrofitInstance

class SurveyViewModel : ViewModel() {
    private val totalQuestions = 6
    private val answers = mutableStateOf(mutableMapOf<Int, String>())

    fun saveAnswer(questionNumber: Int, answer: String) {
        val currentAnswers = answers.value.toMutableMap()
        currentAnswers[questionNumber] = answer
        answers.value = currentAnswers
    }

    fun getAnswer(questionNumber: Int): String {
        return answers.value[questionNumber] ?: ""
    }
    fun getAnswers(): MutableState<MutableMap<Int, String>> {
        return answers
    }
    fun isSurveyComplete(): Boolean {
        return answers.value.size == totalQuestions
    }
    fun clearAnswers() {
        answers.value.clear()
    }


    suspend fun firstForm(answers: MutableMap<Int, String>): List<Any> {
        val currentAnswers = answers.toMutableMap()
        val mlAnswerString = currentAnswers[2]
        val mlAnswer = if(mlAnswerString == "No") 0 else 1
        val modelAnswer = if(currentAnswers[3] == "Markowitz") 0 else 1
        val response = RetrofitInstance.api.form1(Form1Request(mlAnswer,modelAnswer))
        if (response.isSuccessful) {
            val responseToShow = RetrofitInstance.api.getPlotData1()
            val body = responseToShow.body()
            return riskToList(body)

        }
        else{
            return listOf("Error")
        }
    }

    private fun riskToList(body: FirstDataResponse?): List<Any> {
        if (body != null) {
            return listOf(
                listOf(
                "Low Risk Stats - \n",
                "  Mean Yield: ${body.lowRisk.meanYield}\",\n" ,
                        "        \"Standard Deviation: ${body.lowRisk.standardDeviation},\n" ,
                        "        \"Min: ${body.lowRisk.min}\",\n" ,
                        "        \"25%(Q1): ${body.lowRisk.firstQ}\",\n" ,
                        "        \"50%(Median): ${body.lowRisk.median}\",\n" ,
                        "        \"75%(Q3): ${body.lowRisk.thirdQ}\",\n" ,
                        "        \"Max: ${body.lowRisk.max}\""
                ),
                listOf(
                "Medium Risk Stats -\n",
                        "  Mean Yield: ${body.mediumRisk.meanYield}\",\n" ,
                        "        \"Standard Deviation: ${body.mediumRisk.standardDeviation},\n" ,
                        "        \"Min: ${body.mediumRisk.min}\",\n" ,
                        "        \"25%(Q1): ${body.mediumRisk.firstQ}\",\n" ,
                        "        \"50%(Median): ${body.mediumRisk.median}\",\n",
                        "        \"75%(Q3): ${body.mediumRisk.thirdQ}\",\n" ,
                        "        \"Max: ${body.mediumRisk.max}\"",
                ),
                listOf(
                "High Risk Stats - \n",
                        "  Mean Yield: ${body.highRisk.meanYield}\",\n" ,
                        "        \"Standard Deviation: ${body.highRisk.standardDeviation},\n" ,
                        "        \"Min: ${body.highRisk.min}\",\n" ,
                        "        \"25%(Q1): ${body.highRisk.firstQ}\",\n" ,
                        "        \"50%(Median): ${body.highRisk.median}\",\n" ,
                        "        \"75%(Q3): ${body.highRisk.thirdQ}\",\n" ,
                        "        \"Max: ${body.highRisk.max}\"",
                )

            )
        }
        return listOf()

    }
    sealed class SurveyState {
        object Idle : SurveyState()
        object Loading : SurveyState()
        data class Success(val data: FirstDataResponse?) : SurveyState()
        data class Error(val message: String) : SurveyState()
    }
}
