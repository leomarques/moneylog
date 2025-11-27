package lmm.moneylog.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
    ) {
        Text(
            text = "Hello, World!",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun MyCardPreview() {
    MyCard()
}
