package lmm.moneylog.ui.components.radiogroup

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RadioOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
        )

        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioOptionPreview() {
    RadioOption(
        text = "Option 1",
        selected = true,
        onClick = {}
    )
}
