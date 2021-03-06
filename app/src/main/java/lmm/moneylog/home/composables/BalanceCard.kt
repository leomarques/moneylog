package lmm.moneylog.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BalanceCard(total: String, credit: String, debt: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                if (isSystemInDarkTheme()) {
                    Color.DarkGray
                } else {
                    MaterialTheme.colors.background
                }
            )
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Balance("Saldo em contas", total)

        Spacer(Modifier.height(32.dp))

        Row {
            Amount("Receitas", credit, Color.Green)

            Spacer(Modifier.width(64.dp))

            Amount("Despesas", debt, Color.Red)
        }
    }
}

@Composable
fun Balance(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun Amount(title: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = value,
            style = MaterialTheme.typography.h5,
            color = color
        )
    }
}