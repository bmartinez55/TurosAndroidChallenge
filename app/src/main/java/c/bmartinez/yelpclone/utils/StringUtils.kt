package c.bmartinez.yelpclone.utils

import c.bmartinez.yelpclone.data.model.BusinessCategories
import c.bmartinez.yelpclone.data.model.ResultsCategories
import c.bmartinez.yelpclone.data.model.YelpBusinessDetails
import java.lang.StringBuilder

//Concats all string titles in BusinessCatergories object
fun concatBusinessListOfStrings(input: List<BusinessCategories>): String {
    val output = arrayListOf<String>()

    for(str in input){ output.add(str.title) }

    return output.joinToString(", ")
}

//Concats all string titles in BusinessCatergories object
fun concatSearchListOfStrings(input: List<ResultsCategories>): String {
    val output = arrayListOf<String>()

    for(str in input){ output.add(str.title) }

    return output.joinToString(" ")
}