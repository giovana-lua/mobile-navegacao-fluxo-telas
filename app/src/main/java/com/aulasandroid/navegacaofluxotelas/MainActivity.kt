package com.aulasandroid.navegacaofluxotelas

import android.R.attr.name
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aulasandroid.navegacaofluxotelas.screens.LoginScreen
import com.aulasandroid.navegacaofluxotelas.screens.MenuScreen
import com.aulasandroid.navegacaofluxotelas.screens.PedidosScreen
import com.aulasandroid.navegacaofluxotelas.screens.PerfilScreen
import com.aulasandroid.navegacaofluxotelas.ui.theme.NavegacaoFluxoTelasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoFluxoTelasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        //inicia na tela login
                        startDestination = "login",
                        exitTransition = {
                            slideOutOfContainer(
                            towards = AnimatedContentTransitionScope
                                .SlideDirection.Left,
                                animationSpec = tween(durationMillis = 1500)
                        )},
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope
                                    .SlideDirection.Left,
                                animationSpec = tween(durationMillis = 1500)
                            )
                        }

                    ) {

                        composable(route = "login",

                                exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope
                                    .SlideDirection.Up,
                                animationSpec = tween(durationMillis = 1500))+ fadeOut(animationSpec = tween(durationMillis = 1500))


                                }




                        ) {LoginScreen(navController = navController)}

                        composable(route = "menu") {MenuScreen(navController = navController)}

                        composable(route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument("nome") {
                                    type = NavType.StringType
                                },
                                navArgument("idade")
                                {
                                    type = NavType.IntType
                                })
                            ) {
                            val nome = it.arguments?.getString("nome")

                            val idade = it.arguments?.getInt("idade")

                            PerfilScreen(
                                navController = navController,
                                // !! deixa claro que o valor pode ser null
                                nome = nome!!,
                                idade = idade!!
                            )}

                        composable(
                            route = "pedidos?numeroPedido={numeroPedido}",
                            arguments = listOf(
                                navArgument(name = "numeroPedido") {
                                    //se não tiver nenum valor ->
                                    defaultValue = "Sem pedidos"
                                }
                            )
                        ) {
                            val numeroPedido = it.arguments?.getString("numeroPedido")
                            PedidosScreen(
                                navController = navController,
                                numeroPedido = numeroPedido!!
                            )}
                    }

                    //LoginScreen()
                    //MenuScreen()
                    //PerfilScreen()
                    //PedidosScreen()
                }
            }
        }
    }
}

