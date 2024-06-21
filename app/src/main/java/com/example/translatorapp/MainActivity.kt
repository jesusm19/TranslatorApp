package com.example.translatorapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.translatorapp.translator.TranslatorView
import com.example.translatorapp.translator.TranslatorViewModel
import com.example.translatorapp.ui.theme.TranslatorAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val translatorViewModel: TranslatorViewModel by viewModels()
        setContent {
            TranslatorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TranslatorView(translatorViewModel)
                }
            }
        }
    }
}