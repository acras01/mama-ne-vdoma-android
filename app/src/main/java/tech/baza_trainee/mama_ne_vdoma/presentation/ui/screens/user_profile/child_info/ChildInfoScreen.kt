package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.child_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tech.baza_trainee.mama_ne_vdoma.domain.model.Gender
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.OutlinedTextFieldWithError
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.RadioGroup
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.TopBarWithArrow
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.ButtonText

@Composable
fun ChildInfoScreen(
    modifier: Modifier = Modifier,
    screenState: State<ChildInfoViewState> = mutableStateOf(ChildInfoViewState()),
    onHandleChildEvent: (ChildInfoEvent) -> Unit = { _ -> },
    onNext: () -> Unit = { },
    onBack: () -> Unit = { }
) {
    Surface(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            modifier = modifier
                .imePadding()
                .fillMaxWidth()
        ) {
            val (topBar, content, btnNext) = createRefs()

            val topGuideline = createGuidelineFromTop(0.2f)

            TopBarWithArrow(
                modifier = modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        bottom.linkTo(topGuideline)
                        height = Dimension.fillToConstraints
                    },
                title = "Розкажіть про свою дитину",
                onBack = onBack
            )

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .constrainAs(content) {
                        top.linkTo(topGuideline)
                        bottom.linkTo(btnNext.top, 16.dp)
                        height = Dimension.fillToConstraints
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = modifier.height(16.dp))

                OutlinedTextFieldWithError(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    text = screenState.value.name,
                    label = "Вкажіть ім'я дитини",
                    onValueChange = { onHandleChildEvent(ChildInfoEvent.ValidateChildName(it)) },
                    isError = screenState.value.nameValid == ValidField.INVALID,
                    errorText = "Ви ввели некоректнe ім'я"
                )

                Spacer(modifier = modifier.height(16.dp))

                OutlinedTextFieldWithError(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    text = screenState.value.age,
                    label = "Вкажіть вік дитини",
                    onValueChange = { onHandleChildEvent(ChildInfoEvent.ValidateAge(it)) },
                    isError = screenState.value.ageValid == ValidField.INVALID,
                    errorText = "Ви ввели некоректний вік",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = modifier.height(16.dp))

                val genderOptions = listOf(Gender.BOY, Gender.GIRL)

                RadioGroup(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    radioGroupOptions = genderOptions,
                    getText = { it.gender },
                    selected = screenState.value.gender,
                    onSelectedChange = { onHandleChildEvent(ChildInfoEvent.SetGender(it)) }
                )
            }

            Button(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(btnNext) {
                        bottom.linkTo(parent.bottom)
                    }
                    .height(48.dp),
                onClick = {
                    onHandleChildEvent(ChildInfoEvent.SaveCurrentChild)
                    onNext()
                },
                enabled = screenState.value.nameValid == ValidField.VALID &&
                        screenState.value.ageValid == ValidField.VALID &&
                        screenState.value.gender != Gender.NONE
            ) {
                ButtonText(
                    text = "Зареєструвати дитину"
                )
            }
        }
    }
}

@Composable
@Preview
fun ChildInfoPreview() {
    ChildInfoScreen()
}