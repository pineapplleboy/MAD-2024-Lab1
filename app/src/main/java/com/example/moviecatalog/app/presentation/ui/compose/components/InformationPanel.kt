package com.example.moviecatalog.app.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R

@Composable
fun InformationPanel(
    countries: String,
    age: Int,
    durability: Int,
    year: Int,
    modifier: Modifier = Modifier
) {
    MoviePanel(
        iconID = R.drawable.info,
        textID = R.string.information_panel,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InformationPanelElement(
                    name = stringResource(R.string.information_countries),
                    content = countries,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                InformationPanelElement(
                    name = stringResource(R.string.information_age),
                    content = "${age}+",
                    modifier = Modifier.wrapContentWidth()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InformationPanelElement(
                    name = stringResource(R.string.information_durability),
                    content = durability.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                InformationPanelElement(
                    name = stringResource(R.string.information_release_year),
                    content = year.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}