package com.example.mama_ne_vdoma.ui.screens.create_user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mama_ne_vdoma.ui.theme.Gray
import com.example.mama_ne_vdoma.ui.theme.Mama_ne_vdomaTheme
import com.example.mama_ne_vdoma.utils.CustomButton

@Composable
fun RestorePasswordFunc(
    onBack: () -> Unit,
    onRestore: () -> Unit
) {
    RestorePassword(onBack = onBack, onRestore = onRestore)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestorePassword(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onRestore: () -> Unit = {}
) {
    Mama_ne_vdomaTheme {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = modifier.height(16.dp))

                    ConstraintLayout(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        val (back, title) = createRefs()
                        Text(
                            modifier = modifier
                                .clickable {
                                    onBack()
                                }
                                .constrainAs(back) {
                                    start.linkTo(parent.start, 16.dp)
                                },
                            text = "<",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = modifier
                                .constrainAs(title) {
                                    start.linkTo(back.end, 16.dp)
                                    end.linkTo(parent.end, 16.dp)
                                    width = Dimension.fillToConstraints
                                },
                            text = "Забули пароль?",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        text = "Не турбуйтеся! Будь ласка, введіть " +
                                "свій email за яким ви реєструвались, " +
                                "щоб отримати лист з інструкціями",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = modifier.height(24.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        label = { Text("Введіть свій email") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Gray,
                            focusedBorderColor = MaterialTheme.colorScheme.background,
                            unfocusedBorderColor = MaterialTheme.colorScheme.background,
                        )
                    )
                }

                CustomButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    text = "Відправити",
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    action = onRestore
                )
            }
        }
    }
}

@Composable
@Preview
fun RestorePasswordPreview() {
    RestorePassword()
}