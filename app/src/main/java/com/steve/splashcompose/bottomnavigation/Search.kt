package com.steve.splashcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.steve.splashcompose.ui.theme.DeepBlue

@Composable
fun Search(navController: NavHostController) {

    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxHeight()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Search")
        }
    }

}
