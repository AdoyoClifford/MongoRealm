package com.adoyo.mongodbrealm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adoyo.mongodbrealm.screen.auth.AuthenticationScreen
import com.adoyo.mongodbrealm.screen.auth.AuthenticationViewModel
import com.adoyo.mongodbrealm.screen.home.HomeScreen
import com.adoyo.mongodbrealm.screen.home.HomeViewModel
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetUpNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authRoute (navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screens.Home.route)
        })
        homeRoute()
    }
}

fun NavGraphBuilder.authRoute(navigateToHome: () -> Unit) {
    composable(route = Screens.Home.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onSuccessfulSignIn = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)

            },
            navigateToHome = navigateToHome

        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(Screens.Home.route) {
        val viewModel: HomeViewModel = viewModel()
        val data by viewModel.data

        HomeScreen(
            data = data,
            filtered = viewModel.filtered.value,
            name = viewModel.name.value,
            objectId = viewModel.objectId.value,
            onNameChanged = viewModel::updateName,
            onObjectIdChanged = viewModel::updateObjectId,
            onInsertClicked = viewModel::insertPerson,
            onUpdateClicked = viewModel::updatePerson,
            onDeleteClicked = viewModel::deletePerson,
            onFilterClicked = viewModel::filterData
        )
    }
}