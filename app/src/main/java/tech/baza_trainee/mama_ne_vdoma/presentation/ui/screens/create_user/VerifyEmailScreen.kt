package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user.model.VerifyEmailViewState
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user.vm.UserCreateViewModel
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.composables.OtpTextField
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.composables.SurfaceWithSystemBars
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.ButtonText

@Composable
fun VerifyEmailFunc(
    viewModel: UserCreateViewModel,
    onNext: () -> Unit
) {
    VerifyEmail(
        screenState = viewModel.verifyEmailViewState.collectAsStateWithLifecycle(),
        onVerify = { otp, isLastDigit ->
            viewModel.verifyEmail(otp, isLastDigit) {
                onNext()
            }
        }
    )
}

@Composable
fun VerifyEmail(
    modifier: Modifier = Modifier,
    screenState: State<VerifyEmailViewState> = mutableStateOf(VerifyEmailViewState()),
    onVerify: (String, Boolean) -> Unit = { _,_ -> }
) {
    SurfaceWithSystemBars {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .imePadding()
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = modifier.height(16.dp))

                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = "Створити профіль",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = redHatDisplayFontFamily
                )

                Spacer(modifier = modifier.height(48.dp))

                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = "Ми відправили на вашу пошту електронний лист з верифікаційним посиланням — код з 4 символів." +
                            " Перевірте пошту, і якщо не знайдете листа — теку «спам»",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = redHatDisplayFontFamily
                )

                Spacer(modifier = modifier.height(32.dp))

                OtpTextField(
                    otpText = screenState.value.otp,
                    onOtpTextChange = { value, otpInputFilled ->
                        onVerify(value, otpInputFilled)
                    }
                )
            }

            Button(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {}
            ) {
                ButtonText(
                    text = "Надіслати код ще раз"
                )
            }
        }
    }
}

@Composable
@Preview
fun VerifyEmailPreview() {
    VerifyEmail()
}