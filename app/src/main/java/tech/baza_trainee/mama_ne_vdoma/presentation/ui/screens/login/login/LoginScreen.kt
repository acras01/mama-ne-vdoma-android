package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.login.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.LoadingIndicator
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.SocialLoginBlock
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.SurfaceWithSystemBars
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.functions.getTextWithUnderline
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.text_fields.OutlinedTextFieldWithError
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.text_fields.PasswordTextFieldWithError
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.RequestState
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField

@Composable
fun LoginUserScreen(
    modifier: Modifier = Modifier,
    screenState: State<LoginViewState> = mutableStateOf(LoginViewState()),
    uiState: State<RequestState> = mutableStateOf(RequestState.Idle),
    handleEvent: (LoginEvent) -> Unit = { _ -> }
) {
    SurfaceWithSystemBars {
        BackHandler { handleEvent(LoginEvent.OnBack) }

        val context = LocalContext.current

        when(val state = uiState.value) {
            RequestState.Idle -> Unit
            is RequestState.OnError -> {
                if (state.error.isNotBlank()) Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                handleEvent(LoginEvent.ResetUiState)
            }
        }
        
        Column(
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Увійти у свій профіль",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = redHatDisplayFontFamily
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextFieldWithError(
                    modifier = Modifier.fillMaxWidth(),
                    text = screenState.value.email,
                    label = "Введіть свій email",
                    onValueChange = { handleEvent(LoginEvent.ValidateEmail(it)) },
                    isError = screenState.value.emailValid == ValidField.INVALID,
                    errorText = "Ви ввели некоректний email",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextFieldWithError(
                    modifier = Modifier.fillMaxWidth(),
                    password = screenState.value.password,
                    onValueChange = { handleEvent(LoginEvent.ValidatePassword(it)) },
                    isError = screenState.value.passwordValid == ValidField.INVALID
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(alignment = Alignment.End)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { handleEvent(LoginEvent.OnRestore) },
                    text = getTextWithUnderline("", "Забули пароль?", false),
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontFamily = redHatDisplayFontFamily
                )

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { handleEvent(LoginEvent.LoginUser) },
                    enabled = screenState.value.passwordValid == ValidField.VALID &&
                            screenState.value.emailValid == ValidField.VALID
                ) {
                    Text(
                        text = "Увійти",
                        fontFamily = redHatDisplayFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

//                ConstraintLayout(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    val (box1, text, box2) = createRefs()
//                    Box(
//                        modifier = Modifier
//                            .height(height = 2.dp)
//                            .background(color = Gray)
//                            .constrainAs(box1) {
//                                start.linkTo(parent.start, 24.dp)
//                                end.linkTo(text.start, 16.dp)
//                                top.linkTo(parent.top)
//                                bottom.linkTo(parent.bottom)
//                                width = Dimension.fillToConstraints
//                            }
//                    )
//                    Text(
//                        modifier = Modifier
//                            .constrainAs(text) {
//                                start.linkTo(box1.end, 16.dp)
//                                end.linkTo(box2.start, 16.dp)
//                                top.linkTo(parent.top)
//                                bottom.linkTo(parent.bottom)
//                                width = Dimension.wrapContent
//                            },
//                        text = "чи",
//                        fontSize = 14.sp,
//                        fontFamily = redHatDisplayFontFamily
//                    )
//                    Box(
//                        modifier = Modifier
//                            .height(height = 2.dp)
//                            .background(color = Gray)
//                            .constrainAs(box2) {
//                                start.linkTo(text.end, 16.dp)
//                                end.linkTo(parent.end, 24.dp)
//                                top.linkTo(parent.top)
//                                bottom.linkTo(parent.bottom)
//                                width = Dimension.fillToConstraints
//                            }
//                    )
//                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            SocialLoginBlock(
                textForBottomButton = getTextWithUnderline("Ще немає профілю? ", "Зареєструватись")
            ) { handleEvent(LoginEvent.OnCreate) }
        }

        if (screenState.value.isLoading) LoadingIndicator()
    }
}

@Composable
@Preview
fun LoginUserPreview() {
    LoginUserScreen()
}