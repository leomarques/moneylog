package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun ListItemName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = name.ifEmpty { stringResource(R.string.state_no_description) },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge,
        color =
            if (name.isEmpty()) {
                neutralColor
            } else {
                MaterialTheme.colorScheme.onSurface
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ListItemNamePreview() {
    ListItemName(name = "Account name")
}
