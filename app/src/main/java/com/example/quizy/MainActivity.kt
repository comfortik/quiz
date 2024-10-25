package com.example.quizy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quizy.presentation.clicker.ClickerScreen
import com.example.quizy.presentation.common.navigation.BottomBarItem
import com.example.quizy.presentation.common.navigation.Routes
import com.example.quizy.presentation.common.navigation.bottomBarItems
import com.example.quizy.presentation.dialogs.ErrorDialog
import com.example.quizy.presentation.drawing.DrawingScreen
import com.example.quizy.presentation.games.GamesScreen
import com.example.quizy.presentation.leaderboard.LeaderboardScreen
import com.example.quizy.presentation.pairs.PairsScreen
import com.example.quizy.presentation.profile.ProfileScreen
import com.example.quizy.presentation.quiz.QuizScreen
import com.example.quizy.presentation.search.SearchScreen
import com.example.quizy.ui.theme.QuizyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizyTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { CreateBottomBar(navController)}
                ){innerPadding->
                    println(innerPadding)
                    CreateNavigation(navController)
                }


            }
        }
    }
}

@Composable
fun CreateNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.LeaderboardScreen){
        composable<Routes.LeaderboardScreen> {
            LeaderboardScreen(
                onBackPressed = {

                },
                onError = {message->
                    navController.navigate(Routes.ErrorDialog(message))

                })
        }
        composable<Routes.Search> { SearchScreen()  }
        composable<Routes.Games> { GamesScreen() }
        composable<Routes.Profile> { ProfileScreen() }
        composable<Routes.Clicker> { ClickerScreen {navController.navigateUp()}}
        composable<Routes.Pairs> { PairsScreen {navController.navigateUp() }  }
        composable<Routes.Drawing> { DrawingScreen {navController.navigateUp() } }
        composable<Routes.Quiz> { QuizScreen {navController.navigateUp()} }

        dialog<Routes.ErrorDialog> { navBackStackEntry ->
            val errorMessage = navBackStackEntry.toRoute<Routes.ErrorDialog>().errorMessage
            ErrorDialog(text = errorMessage) {
                navController.navigateUp()
            }
        }
    }

}


@Composable
fun CreateBottomBar(navController: NavController){
    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                bottomBarItems.forEach { item ->
                    if(item is BottomBarItem.RandomGame){
                        val game = listOf(
                            Routes.Quiz,
                            Routes.Clicker,
                            Routes.Pairs,
                            Routes.Drawing
                        ).random()
                        IconButton(onClick = { navController.navigate(game) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.icon),
                                contentDescription = null
                            )
                        }
                    }else{
                        IconButton(onClick = { navController.navigate(item.destination) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.icon),
                                contentDescription = null
                            )
                        }
                    }


                }
            }
        }
    )
}






@Composable
private fun CreateBottomBar(bottomNavController: NavController, navController: NavController) {
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(modifier = Modifier.height(58.dp)){
        bottomBarItems.forEach { item->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.destination::class)
            } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(item.destination) },
                icon = {
                    Image(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(0xFFCCCCCC))
                    )
                }
            )
        }
    }



}

