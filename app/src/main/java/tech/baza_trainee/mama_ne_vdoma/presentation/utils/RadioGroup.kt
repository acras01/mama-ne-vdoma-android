package tech.baza_trainee.mama_ne_vdoma.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.Mama_ne_vdomaTheme

@Composable
fun <T> RadioGroup(
    modifier: Modifier = Modifier,
    radioGroupOptions: List<T>,
    getText: (T) -> String
) {
    Mama_ne_vdomaTheme {
        var selected by remember { mutableStateOf<T?>(null) }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val onSelectedChange: (T) -> Unit = { value ->
                selected = value
            }
            radioGroupOptions.forEach { value ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (value == selected),
                            onClick = { onSelectedChange(value) }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = (value == selected),
                        onClick = { onSelectedChange(value) }
                    )
                    Text(text = getText(value))
                }
            }
        }
    }
}