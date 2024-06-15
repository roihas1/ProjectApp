package com.example.projectapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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
}
