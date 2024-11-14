package com.example.quizy


import androidx.compose.foundation.Canvas
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.presentation.choosePlayer.ChoosePlayerScreen
import com.example.quizy.presentation.clicker.ClickerScreen
import com.example.quizy.presentation.common.BarShape
import com.example.quizy.presentation.common.ErrorHandler
import com.example.quizy.presentation.common.ErrorHandlerProvider
import com.example.quizy.presentation.common.ErrorType
import com.example.quizy.presentation.common.navigation.BottomBarItem
import com.example.quizy.presentation.common.navigation.DialogRoutes
import com.example.quizy.presentation.common.navigation.Routes
import com.example.quizy.presentation.common.navigation.bottomBarItems
import com.example.quizy.presentation.dialogs.EndGameDialog
import com.example.quizy.presentation.dialogs.ErrorDialog
import com.example.quizy.presentation.drawing.DrawingScreen
import com.example.quizy.presentation.games.GamesScreen
import com.example.quizy.presentation.leaderboard.LeaderboardScreen
import com.example.quizy.presentation.pairs.PairsScreen
import com.example.quizy.presentation.profile.ProfileScreen
import com.example.quizy.presentation.quiz.QuizScreen
import com.example.quizy.presentation.quiz.dilog.QuizStartDialog
import com.example.quizy.presentation.quiz.models.QuizIntent
import com.example.quizy.presentation.search.SearchScreen
import com.example.quizy.ui.theme.QuizyTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity(), ErrorHandler {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ErrorHandlerProvider.setErrorHandler(this)
        initSharedPrefs()
        val id = getId()
//        val startRoute =Routes.Games TODO не забыть поменять на норм нав
        val startRoute  =  if(id==-1)Routes.ChoosePlayer else Routes.LeaderboardScreen
        setContent {
            QuizyTheme {
                navController = rememberNavController()
                navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val screen = backStackEntry?.destination?.route
                Log.d("mainactivity", screen.toString())

                var currentRoute by remember { mutableStateOf<Routes>(Routes.LeaderboardScreen)}



                Scaffold(
                    topBar = {CreateTopBar(currentRoute)},
                    bottomBar = { CreateBottomBar(navController)}
                ){innerPadding->
                    println(innerPadding)
                    CreateNavigation(navController, startRoute, Modifier.padding(innerPadding)){route->
                        currentRoute = route
                    }
                }


            }
        }
    }

    override fun showError(error: ErrorType) {
        Log.d("error", error.message)
        navController.navigate(DialogRoutes.ErrorDialog(error.message))
    }

    private fun initSharedPrefs(){
        SharedPreferensesProvider.init(this)
    }
    private fun getId()=
        SharedPreferensesProvider.getIdFromSharedPrefs()

}
private fun getScreenTitle(route: Routes) =
    when(route){
        is Routes.Search -> "Search"
        is Routes.Quiz -> "Quiz"
        is Routes.Pairs->"Pairs"
        is Routes.Drawing->"Drawing"
        is Routes.LeaderboardScreen-> "Leaderboard"
        is Routes.ChoosePlayer->"Choose player"
        is Routes.Games -> "Games"
        is Routes.Clicker->"Clicker"
        is Routes.Profile->"Profile"
    }







@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTopBar(route: Routes) {
    val title = getScreenTitle(route)

    TopAppBar(title = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title)
        }
    },
        navigationIcon = {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.clickable {  })
        }
    )
}


@Composable
private fun CreateNavigation(navController: NavHostController, startRoute: Routes,modifier: Modifier = Modifier, onNavigate: (Routes)->Unit){

    NavHost(navController = navController, startDestination = startRoute, modifier = modifier){
        composable<Routes.LeaderboardScreen> {
            onNavigate(Routes.LeaderboardScreen)
            LeaderboardScreen(
                onBackPressed = {

                },
                onError = {message->
                    navController.navigate(DialogRoutes.ErrorDialog(message))
                })
        }
        composable<Routes.Search> {
            onNavigate(Routes.Search)
            SearchScreen()
        }
        composable<Routes.Games> {
            onNavigate(Routes.Games)
            GamesScreen{ gameName->
            with(navController){
                when(gameName){
                    "Clicker"->navigate(Routes.Clicker)
                    "Quiz"-> navigate(Routes.Quiz)
                    "Drawing"-> navigate(Routes.Drawing)
                    "Pairs"-> navigate(Routes.Pairs)
                }
            }

        } }
        composable<Routes.Profile> {
            onNavigate(Routes.Profile)
            ProfileScreen()
        }
        composable<Routes.Clicker> {
            onNavigate(Routes.Clicker)
            ClickerScreen(
                onBackPressed = {navController.navigateUp()}
            )
        }
        composable<Routes.Pairs> {
            onNavigate(Routes.Pairs)
            PairsScreen(
            onBackPressed = {navController.navigateUp() },
            onEndGame = { score->
                navController.navigate(DialogRoutes.EndGameDialog(score = score))
            }
        )  }
        composable<Routes.Drawing> {
            onNavigate(Routes.Drawing)
            DrawingScreen {navController.navigateUp() }
        }

        composable<Routes.ChoosePlayer>{
            onNavigate(Routes.ChoosePlayer)
            ChoosePlayerScreen{navController.navigate(Routes.LeaderboardScreen)}
        }
        composable<Routes.Quiz> {
            onNavigate(Routes.Quiz)
            QuizScreen {name, score->
                navController.navigate(DialogRoutes.EndGameDialog(name = name, score = score))
            }
        }



        dialog<DialogRoutes.ErrorDialog> { navBackStackEntry ->
            val errorMessage = navBackStackEntry.toRoute<DialogRoutes.ErrorDialog>().errorMessage
            ErrorDialog(text = errorMessage) {
                navController.navigateUp()
            }
        }

        dialog<DialogRoutes.EndGameDialog> { navBackStackEntry ->
            val name = navBackStackEntry.toRoute<DialogRoutes.EndGameDialog>().name
            val score = navBackStackEntry.toRoute<DialogRoutes.EndGameDialog>().score
            EndGameDialog(name = name, score = score) {
                navController.navigateUp()
                navController.navigateUp()
            }

        }
    }

}

@Preview
@Composable
fun CreatePreview() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CreateBottomBar(navController = navController)
        }
    ) { innerPadding ->

    }
}


@Composable
fun CreateBottomBar(navController: NavController) {
    val randomGame = listOf(
        Routes.Quiz,
        Routes.Clicker,
        Routes.Pairs,
        Routes.Drawing
    ).random()
    val withPx = LocalContext.current.resources.displayMetrics.widthPixels
    val barShape = BarShape(
        offset = withPx / 2f,
        circleRadius = 30.dp,
        cornerRadius = 12.dp,
        circleGap = 5.dp
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedCard(
            shape = barShape,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = barShape)
                .shadow(30.dp, shape = barShape),
            border = BorderStroke(1.dp, Color.White)
        ) {

            NavigationBar(
                modifier = Modifier
                    .background(Color.White, shape = barShape)
                    .fillMaxWidth()
                    .height(80.dp),
                containerColor = Color.White,
                contentColor = Color.Black
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomBarItems.take(2).forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.hasRoute(item.destination::class)
                        } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = item.icon),
                                    contentDescription = null
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.destination)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = colorResource(id = R.color.black),
                                unselectedIconColor = colorResource(id = R.color.light_gray),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomBarItems.drop(2).take(2).forEach { item ->
                        IconButton(onClick = { navController.navigate(item.destination) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.icon),
                                contentDescription = null,
                                tint = colorResource(id = R.color.light_gray)
                            )
                        }
                    }
                }
            }

        }
        FloatingActionButton(
            onClick = { navController.navigate(randomGame) },
            shape = CircleShape,
            containerColor = colorResource(id = R.color.primary_purple),
            modifier = Modifier
                .size(55.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-45).dp)

        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.icon_play),
                tint = Color.White,
                contentDescription = "Random Game"
            )
        }
    }


}
