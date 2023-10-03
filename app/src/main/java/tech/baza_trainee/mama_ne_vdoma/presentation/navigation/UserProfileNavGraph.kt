package tech.baza_trainee.mama_ne_vdoma.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.routes.UserProfileRoutes
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.ChildInfoScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.ChildScheduleScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.ChildrenInfoScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.ImageCropScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.ParentScheduleScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.UserInfoScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.UserLocationScreen
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.vm.UserSettingsViewModel
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.sharedViewModel

fun NavGraphBuilder.userProfileGraph(
    navController: NavHostController
) {
    navigation(
        route = "user_profile_graph",
        startDestination = UserProfileRoutes.UserInfo.route
    ) {

        composable(UserProfileRoutes.UserInfo.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            UserInfoScreen(
                screenState = userSettingsViewModel.userInfoScreenState.collectAsStateWithLifecycle(),
                userName = userSettingsViewModel.userName,
                userPhone = userSettingsViewModel.userPhone,
                onHandleUserInfoEvent = { userSettingsViewModel.handleUserInfoEvent(it)},
                onCreateUser = { navController.navigate(UserProfileRoutes.UserLocation.route) },
                onEditPhoto = { navController.navigate(UserProfileRoutes.ImageCrop.route) }
            )
        }
        composable(UserProfileRoutes.ImageCrop.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            val context = LocalContext.current
            val imageForCrop = userSettingsViewModel.getBitmapForCrop(context.contentResolver)
            ImageCropScreen(
                imageForCrop = imageForCrop,
                onSaveUserAvatar = { userSettingsViewModel.saveUserAvatar(it) }
            ) { navController.navigate(UserProfileRoutes.UserInfo.route) }
        }
        composable(UserProfileRoutes.UserLocation.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            UserLocationScreen(
                screenState = userSettingsViewModel.locationScreenState.collectAsStateWithLifecycle(),
                userAddress = userSettingsViewModel.userAddress,
                onHandleLocationEvent = { userSettingsViewModel.handleUserLocationEvent(it) }
            ) {
                navController.navigate(UserProfileRoutes.ChildInfo.route)
            }
        }
        composable(UserProfileRoutes.ChildInfo.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            ChildInfoScreen(
                screenState = userSettingsViewModel.childInfoScreenState.collectAsStateWithLifecycle(),
                childName = userSettingsViewModel.childName,
                childAge = userSettingsViewModel.childAge,
                onHandleChildEvent = { userSettingsViewModel.handleChildInfoEvent(it) },
                onNext = { navController.navigate(UserProfileRoutes.ChildSchedule.route,) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(UserProfileRoutes.ChildSchedule.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            ChildScheduleScreen(
                screenState = userSettingsViewModel.childScheduleViewState.collectAsStateWithLifecycle(),
                comment = userSettingsViewModel.childComment,
                onHandleScheduleEvent = { userSettingsViewModel.handleScheduleEvent(it) },
                { navController.navigate(UserProfileRoutes.ChildrenInfo.route) },
                { navController.popBackStack() }
            )
        }
        composable(UserProfileRoutes.ChildrenInfo.route) { entry ->
            val userSettingsViewModel: UserSettingsViewModel = entry.sharedViewModel(navController)
            ChildrenInfoScreen(
                screenState = userSettingsViewModel.childrenInfoViewState.collectAsStateWithLifecycle(),
                onHandleChildrenInfoEvent = { userSettingsViewModel.handleChildrenInfoEvent(it) },
                onNext = { navController.navigate(UserProfileRoutes.ParentSchedule.route) },
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(UserProfileRoutes.ChildInfo.route) }
            )
        }
        composable(UserProfileRoutes.ParentSchedule.route) {
            val userSettingsViewModel: UserSettingsViewModel = it.sharedViewModel(navController)
            ParentScheduleScreen(
                userSettingsViewModel,
                { },
                { navController.popBackStack() }
            )
        }
    }
}