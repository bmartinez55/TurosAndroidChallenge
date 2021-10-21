package c.bmartinez.yelpclone.utils

object YelpConstants {
    const val API_KEY = "Zw9OVRIdU_icHgdMY9j2QlR5-asI_bHSlf39eFcniPuE1Bz4_1q-U7fJm9JpUvJwRTHcHXFi4lAXbRnLUrRaHvgXlC2LlzPurFds6bEpQq1_OG8tCQJ9kTmCGKkJYXYx"
    const val BASE_URL = "https://api.yelp.com/v3/"
    const val SEARCH_POPULAR_RADIUS = 8000  //This is approx. 5 miles for popular locations
    const val SEARCH_NEW_RADIUS = 40000 //This is approx. 25 miles
    const val METERS_IN_MILE = 1609.344 //This is what one mile is in meters
    const val MAX_POPULAR_RESULT_SIZE = 20 //Max amount of results to retrieve from endpoint for popular locations
    const val MAX_SEARCH_LIST_SIZE = 50 //Max amount of results to retrieve from endpoint for list use
    const val REQUEST_LOCATION_INTERVAL: Long = 1000 * 60 * 5 // 5 mins in milliseconds

    //Saved State Handle Parameter Keys
    const val PARAM_KEY_BUSINESS_ID = "businessId"
    const val PARAM_KEY_SEARCH_TERM = "searchTerm"
}