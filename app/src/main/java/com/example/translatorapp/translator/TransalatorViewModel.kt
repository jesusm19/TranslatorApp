package com.example.translatorapp.translator

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.translatorapp.languages.LanguageStorage
import com.example.translatorapp.languages.getMapStrings
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslatorViewModel: ViewModel() {

    var translatorState by mutableStateOf(TranslatorState())
        private set

    fun setValueText(text: String){
        translatorState = translatorState.copy(textToTranslate = text)
    }

    val languageOptions = listOf(
        TranslateLanguage.SPANISH,
        TranslateLanguage.ENGLISH,
        TranslateLanguage.GERMAN,
        TranslateLanguage.ITALIAN,
    )

    val itemSelection = listOf(
        "SPANISH",
        "ENGLISH",
        "GERMAN",
        "ITALIAN",
    )



    fun onTranslate(text: String, context: Context, sourceLanguage: String, targetLanguage: String){
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        val languageTranslator = Translation.getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener { translateText ->
                translatorState = translatorState.copy(translatedText = translateText)
            }.addOnFailureListener {
                Toast.makeText(context, "Model is downloading!", Toast.LENGTH_SHORT).show()
                downloadLanguageModel(languageTranslator, context)
            }
    }

    private fun downloadLanguageModel(languageTranslator: Translator, context: Context){
        translatorState = translatorState.copy(isDownloading = true)

        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        languageTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Toast.makeText(context, "Model downloaded successful!", Toast.LENGTH_SHORT).show()
                translatorState = translatorState.copy(isDownloading = false)
            }.addOnFailureListener {
                Toast.makeText(context, "Model downloaded failure!", Toast.LENGTH_SHORT).show()
            }

    }
}
