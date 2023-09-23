package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.Gray
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.Mama_ne_vdomaTheme

@Composable
fun EnterPhoneFunc(
    onCreateUser: () -> Unit
) {
    EnterPhone(
        onCreateUser = onCreateUser
    )
}

@Composable
fun EnterPhone(
    modifier: Modifier = Modifier,
    onCreateUser: () -> Unit
) {
    Mama_ne_vdomaTheme {
        Surface(
            modifier = modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxWidth()
            ) {
                val (title, input, next) = createRefs()

                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(title) {
                            top.linkTo(parent.top, 16.dp)
                        },
                    text = "Заповнення профілю",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val phoneText = remember { mutableStateOf(TextFieldValue("")) }

                OutlinedTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(input) {
                            top.linkTo(title.bottom, 24.dp)
                        },
                    value = phoneText.value,
                    label = { Text("Введіть свій номер телефону") },
                    onValueChange = { phoneText.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Gray,
                        unfocusedContainerColor = Gray,
                        disabledContainerColor = Gray,
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    )
                )

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 24.dp)
                        .constrainAs(next) {
                            bottom.linkTo(parent.bottom, 16.dp)
                        },
                    onClick = onCreateUser
                ) {
                    Text(text = "Далі")
                }
            }
        }
    }
}

@Composable
@Preview
fun EnterPhonePreview() {
    EnterPhone(
        onCreateUser = {}
    )
}