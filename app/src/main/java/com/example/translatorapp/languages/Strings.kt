package com.example.translatorapp.languages

import androidx.compose.runtime.Composable

@Composable
fun getMapStrings(): List<Map<String, String>> {
    val englishList = mapOf(
        "title" to "Hello World",
        "subtitle" to "The world is yours",
        "modelDownloaded" to "Model downlaodes succeful!"
    )

    val spanishList = mapOf(
        "title" to "Hola Mundo",
        "subtitle" to "El mundo es tuyo",
        "modelDownloaded" to "Modelo descargado correctamente!"
    )

    return listOf(
        englishList,
        spanishList
    )
}