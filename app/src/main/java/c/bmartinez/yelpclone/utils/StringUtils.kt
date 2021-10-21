package c.bmartinez.yelpclone.utils

import c.bmartinez.yelpclone.data.remote.dto.common_dtos.CategoriesDto

//Concats all string titles in Business Details screen
fun concatBusinessListOfStrings(input: List<CategoriesDto>): String {
    val output = arrayListOf<String>()

    for(str in input){ output.add(str.title) }

    return output.joinToString(", ")
}

//Concats all string titles in Search or Main Screen
fun concatSearchListOfStrings(input: List<CategoriesDto>): String {
    val output = arrayListOf<String>()

    for(str in input){ output.add(str.title) }

    return output.joinToString(" ")
}