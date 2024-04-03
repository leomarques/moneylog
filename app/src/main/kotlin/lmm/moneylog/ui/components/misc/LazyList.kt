package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lmm.moneylog.ui.theme.Size

@Composable
fun LazyList(content: LazyListScope.() -> Unit) {
    LazyColumn(
        Modifier.background(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            shape = RoundedCornerShape(Size.ListRoundedCornerSize)
        )
    ) {
        content()
    }
}
