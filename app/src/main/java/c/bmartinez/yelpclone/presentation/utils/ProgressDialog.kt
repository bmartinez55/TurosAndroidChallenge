package c.bmartinez.yelpclone.presentation.utils


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressDialog() {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(progress = 0.5f ,strokeWidth = 5.dp)
    }

}