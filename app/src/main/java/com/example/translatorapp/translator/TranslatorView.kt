package com.example.translatorapp.translator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TranslatorView(translatorViewModel: TranslatorViewModel) {
    val translatorState = translatorViewModel.translatorState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val languageOptions = translatorViewModel.languageOptions
    val itemSelection = translatorViewModel.itemSelection

    var indexSource by remember { mutableStateOf(0) }
    var indexTarget by remember { mutableStateOf(1) }
    var expandSource by remember { mutableStateOf(false) }
    var expandTarget by remember { mutableStateOf(false) }

    var selectedSourceLanguage by remember { mutableStateOf(languageOptions[0]) }
    var selectedTargetLanguage by remember { mutableStateOf(languageOptions[1]) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownLanguageComponent(
                itemSelection = itemSelection,
                selectedIndex = indexSource,
                expand = expandSource,
                onClickExpanded = { expandSource = true },
                onClickDismiss = { expandSource = false },
                onClickItem = { index ->
                    indexSource = index
                    selectedSourceLanguage = languageOptions[index]
                    expandSource = false
                }
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "",
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)

            )


            DropdownLanguageComponent(
                itemSelection = itemSelection,
                selectedIndex = indexTarget,
                expand = expandTarget,
                onClickExpanded = { expandTarget = true },
                onClickDismiss = { expandTarget = false },
                onClickItem = { index ->
                    indexTarget = index
                    selectedTargetLanguage = languageOptions[index]
                    expandTarget = false
                }
            )
        }
        
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = translatorState.textToTranslate,
            onValueChange = { textSource -> translatorViewModel.setValueText(textSource) },
            label = { 
                Text(text = "Escribe texto...")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Es el enter del teclado

            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    translatorViewModel.onTranslate(
                        translatorState.textToTranslate,
                        context,
                        selectedSourceLanguage,
                        selectedTargetLanguage)
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if(translatorState.isDownloading){
            CircularProgressIndicator()
            Text(text = "Descargando modelo")
        } else {
            OutlinedTextField(
                value = translatorState.translatedText,
                onValueChange = {},
                label = {
                    Text(text = "Traducción...")
                },
                readOnly = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            )
        }
    }
}