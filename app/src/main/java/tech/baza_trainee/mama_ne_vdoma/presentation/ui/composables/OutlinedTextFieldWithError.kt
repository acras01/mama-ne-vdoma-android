package tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily

@Composable
fun PasswordTextFieldWithError(
    modifier: Modifier = Modifier,
    label: String = "Введіть свій пароль",
    password: String = "",
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorText: String = "Пароль не відповідає вимогам"
) {
    Column {
        val focusRequester = remember { FocusRequester() }

        var isPasswordFocused by remember { mutableStateOf(false) }

        ShowHidePasswordTextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isPasswordFocused = it.isFocused
                }
                .fillMaxWidth(),
            label = label,
            placeHolder = "Пароль",
            password = password,
            onValueChange = { onValueChange(it) },
            isError = isError && isPasswordFocused
        )
        if (isError && isPasswordFocused) {
            Text(
                text = errorText,
                color = Color.Red,
                modifier = modifier.padding(top = 4.dp),
                style = TextStyle(
                    fontFamily = redHatDisplayFontFamily
                ),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun OutlinedTextFieldWithError(
    modifier: Modifier = Modifier,
    label: String = "",
    text: String = "",
    trailingIcon: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    Column {
        val focusRequester = remember { FocusRequester() }

        var isEmailFocused by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isEmailFocused = it.isFocused
                }
                .fillMaxWidth(),
            value = text,
            label = { Text(label) },
            onValueChange = { onValueChange(it) },
            isError = isError && isEmailFocused,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon ?: {
                if (isError)
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "error",
                        tint = Color.Red
                    )
            },
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(
                fontFamily = redHatDisplayFontFamily
            )
        )
        if (isError && isEmailFocused) {
            Text(
                text = errorText,
                color = Color.Red,
                modifier = modifier.padding(top = 4.dp),
                style = TextStyle(
                    fontFamily = redHatDisplayFontFamily
                ),
                fontSize = 14.sp
            )
        }
    }
}