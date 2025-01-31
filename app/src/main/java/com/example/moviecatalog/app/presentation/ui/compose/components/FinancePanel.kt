package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R

@Composable
fun FinancePanel(
    budget: Int,
    income: Int,
    modifier: Modifier = Modifier
) {
    MoviePanel(
        iconID = R.drawable.money,
        textID = R.string.information_finance,
        modifier = modifier
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InformationPanelElement(
                name = stringResource(R.string.information_budget),
                content = budget.toString(),
                modifier = Modifier.weight(1f)
            )
            InformationPanelElement(
                name = stringResource(R.string.information_income),
                content = income.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}