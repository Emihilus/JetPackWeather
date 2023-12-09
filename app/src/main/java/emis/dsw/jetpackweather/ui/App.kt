package emis.dsw.jetpackweather.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import emis.dsw.jetpackweather.ui.navigation.ReplyBottomNavigationBar
import emis.dsw.jetpackweather.ui.navigation.ReplyNavigationActions
import emis.dsw.jetpackweather.ui.navigation.ReplyRoute
import emis.dsw.jetpackweather.ui.navigation.ReplyTopLevelDestination
import emis.dsw.jetpackweather.ui.utils.ReplyContentType
import emis.dsw.jetpackweather.ui.utils.ReplyNavigationContentPosition
import emis.dsw.jetpackweather.ui.utils.ReplyNavigationType


@Composable
fun App(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: DiceUiState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, ReplyContentType) -> Unit = { _, _ -> },
    toggleSelectedEmail: (Long) -> Unit = { },
    saveLocation: (String, Any, Any) -> Unit,
    setLocation: (Int) -> Unit
) {


    val navController = rememberNavController()


    val navigationType: ReplyNavigationType
    val contentType: ReplyContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

//    when (windowSize.widthSizeClass) {
//        WindowWidthSizeClass.Compact -> {
//            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
//            contentType = ReplyContentType.SINGLE_PANE
//        }
//
//        WindowWidthSizeClass.Medium -> {
//            navigationType = ReplyNavigationType.NAVIGATION_RAIL
//            contentType =
//                ReplyContentType.SINGLE_PANE
//        }
//
//        WindowWidthSizeClass.Expanded -> {
//            navigationType =
//                ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
//            contentType = ReplyContentType.DUAL_PANE
//        }
//
//        else -> {
//            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
//            contentType = ReplyContentType.SINGLE_PANE
//        }
//    }
    navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
    contentType = ReplyContentType.SINGLE_PANE

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            ReplyNavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            ReplyNavigationContentPosition.CENTER
        }

        else -> {
            ReplyNavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
        replyHomeUIState = replyHomeUIState,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail,
        toggleSelectedEmail = toggleSelectedEmail,
        saveLocation = saveLocation,
        setLocation = setLocation

    )
}

@Composable
fun NavigationWrapper(
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ReplyNavigationContentPosition,
    replyHomeUIState: DiceUiState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    toggleSelectedEmail: (Long) -> Unit,
    saveLocation: (String, Any, Any) -> Unit,
    setLocation: (Int) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ReplyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: ReplyRoute.CUURENT_MAIN

    // TODO check on custom width of PermanentNavigationDrawer: b/232495216
//    PermanentNavigationDrawer(drawerContent = {
//        PermanentNavigationDrawerContent(
//            selectedDestination = selectedDestination,
//            navigationContentPosition = navigationContentPosition,
//            navigateToTopLevelDestination = navigationActions::navigateTo,
//        )
//    }) {
    AppContent(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
        replyHomeUIState = replyHomeUIState,
        navController = navController,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail,
        toggleSelectedEmail = toggleSelectedEmail,
        saveLocation = saveLocation,
        setLocation = setLocation
    )
//    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: ReplyNavigationContentPosition,
    replyHomeUIState: DiceUiState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    toggleSelectedEmail: (Long) -> Unit,
    onDrawerClicked: () -> Unit = {},
    saveLocation: (String, Any, Any) -> Unit,
    setLocation: (Int) -> Unit
) {
    Row {
//        AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
//            ReplyNavigationRail(
//                selectedDestination = selectedDestination,
//                navigationContentPosition = navigationContentPosition,
//                navigateToTopLevelDestination = navigateToTopLevelDestination,
//                onDrawerClicked = onDrawerClicked,
//            )
//        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            AppNavHost(
                navController = navController,
                replyHomeUIState = replyHomeUIState,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationType = navigationType,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
                toggleSelectedEmail = toggleSelectedEmail,
                saveLocation = saveLocation,
                modifier = Modifier.weight(1f),
                setLocation = setLocation
            )
            AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
                ReplyBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    replyHomeUIState: DiceUiState,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: ReplyNavigationType,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    toggleSelectedEmail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    saveLocation: (String, Any, Any) -> Unit,
    setLocation: (Int) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ReplyRoute.CUURENT_MAIN,
    ) {
        composable(ReplyRoute.CUURENT_MAIN) {
            WeatherCurrentScreen(
                temp = replyHomeUIState.currentTemp,
                loc = replyHomeUIState.currentWLocation
            )
        }
        composable(ReplyRoute.CURRENT_DETAILS) {
            WeatherDetailsScreen(saveLocation = saveLocation)
        }
        composable(ReplyRoute.LIST) {
            WeatherLocationsListScreen(
                locations = replyHomeUIState.locations,
                setLocation = setLocation,
                currentIndex = replyHomeUIState.currentIndex
            )
        }
        composable(ReplyRoute.DUMMY) {
            WeatherLocationsGridScreen(
                locations = replyHomeUIState.locations,
                setLocation = setLocation,
                currentIndex = replyHomeUIState.currentIndex
            )
        }
    }
}