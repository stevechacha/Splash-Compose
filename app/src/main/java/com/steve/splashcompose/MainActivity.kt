package com.steve.splashcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.steve.splashcompose.ui.theme.DeepBlue
import com.steve.splashcompose.ui.theme.SplashComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.navigationBarColor=MaterialTheme.colors.background.toArgb()
            SplashComposeTheme {
                Surface(color = DeepBlue,elevation = 2.dp){
                    Navigation()
                }
                // A surface container using the 'background' color from the theme


            }

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Screen.Splash.route") {
        composable("Screen.Splash.route") { SplashScreen(navController = navController) }

        composable("main_screen") {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    MainScreen()
                }

            }
        }

    }

}

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate("main_screen")

    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(DeepBlue)) {
        Image(
            painter = painterResource(id = R.drawable.compose), contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )

    }
}

@ExperimentalFoundationApi
@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(){
    val items = listOf(Screen.Home, Screen.Search, Screen.Settings, Screen.Profile)

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }

            }

        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { Home(navController) }
            composable(Screen.Search.route) { Search(navController) }
            composable(Screen.Settings.route) {Settings(navController) }
            composable(Screen.Profile.route) { Profile(navController) }
        }
    }
}

