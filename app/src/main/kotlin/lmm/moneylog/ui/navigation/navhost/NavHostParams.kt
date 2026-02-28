package lmm.moneylog.ui.navigation.navhost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import lmm.moneylog.ui.navigation.createNavigationActions

@Composable
fun NavHostParams(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    onNavigate: (destination: String) -> Unit,
    onBackNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val actions = createNavigationActions(onNavigate, onBackNavigation)

    MyNavHost(
        navController = navController,
        startDestination = startDestination,
        actions = actions,
        modifier = modifier.padding(paddingValues)
    )
}
