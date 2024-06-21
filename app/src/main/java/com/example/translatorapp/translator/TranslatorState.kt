package com.example.translatorapp.translator

data class TranslatorState(
    val textToTranslate: String = "",
    val translatedText: String = "",
    val isDownloading: Boolean = false
)
