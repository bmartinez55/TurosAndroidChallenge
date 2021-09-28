package c.bmartinez.yelpclone.utils

const val api_key = "Zw9OVRIdU_icHgdMY9j2QlR5-asI_bHSlf39eFcniPuE1Bz4_1q-U7fJm9JpUvJwRTHcHXFi4lAXbRnLUrRaHvgXlC2LlzPurFds6bEpQq1_OG8tCQJ9kTmCGKkJYXYx"
const val baseURL = "https://api.yelp.com/v3/"
const val searchPopularRadius = 8000  //This is approx. 5 miles for popular locations
const val searchNewRadius = 40000 //This is approx. 25 miles
const val metersInMile = 1609.344 //This is what one mile is in meters
const val maxPopularResults = 20 //Max amount of results to retrieve from endpoint for popular locations
const val maxListResults = 50 //Max amount of results to retrieve from endpoint for list use
const val REQUEST_LOCATION_INTERVAL: Long = 1000 * 60 * 30