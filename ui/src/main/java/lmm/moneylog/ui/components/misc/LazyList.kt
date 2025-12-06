package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.theme.Size

@Composable
fun LazyList(
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Card(
        modifier =
            modifier
                .padding(horizontal = Size.DefaultSpaceSize),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyColumn(
            Modifier.background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
            )
        ) {
            content()
        }
    }
}
