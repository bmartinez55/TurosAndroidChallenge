package c.bmartinez.yelpclone.presentation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object BusinessDetailsScreen: Screen("business_details_screen")
}
