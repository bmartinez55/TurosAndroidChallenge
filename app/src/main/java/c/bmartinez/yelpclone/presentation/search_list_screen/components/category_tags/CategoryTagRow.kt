package c.bmartinez.yelpclone.presentation.search_list_screen.components.category_tags

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun CategoryTagRow(categories: ArrayList<String>) {
    LazyRow {
        items(categories) { category ->
            CategoryTag(category)
        }
    }
}