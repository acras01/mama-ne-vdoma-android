package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.groups.create_group

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.GroupAvatarWithCameraAndGallery
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.LoadingIndicator
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.OutlinedTextFieldWithError
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.ScheduleGroup
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.ButtonText

@Composable
fun CreateGroupScreen(
    modifier: Modifier = Modifier,
    screenState: State<CreateGroupViewState> = mutableStateOf(CreateGroupViewState()),
    uiState: State<CreateGroupUiState> = mutableStateOf(CreateGroupUiState.Idle),
    handleEvent: (CreateGroupEvent) -> Unit = {}
) {
    BackHandler { handleEvent(CreateGroupEvent.OnBack) }

    val context = LocalContext.current

    when (val state = uiState.value) {
        CreateGroupUiState.Idle -> Unit
        is CreateGroupUiState.OnError -> {
            if (state.error.isNotBlank()) Toast.makeText(context, state.error, Toast.LENGTH_LONG)
                .show()
            handleEvent(CreateGroupEvent.ResetUiState)
        }
        CreateGroupUiState.OnAvatarError -> {
            Toast.makeText(
                context,
                "Аватарка має розмір більше 1МБ. Будь ласка, оберіть інше фото і повторіть",
                Toast.LENGTH_LONG
            ).show()
            handleEvent(CreateGroupEvent.ResetUiState)
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Створення нової групи",
            fontFamily = redHatDisplayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Адреса групи",
            fontFamily = redHatDisplayFontFamily,
            fontSize = 14.sp
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = screenState.value.address,
            fontFamily = redHatDisplayFontFamily,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextFieldWithError(
            text = screenState.value.name,
            onValueChange = { handleEvent(CreateGroupEvent.UpdateName(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = "Назва групи",
            isError = screenState.value.nameValid == ValidField.INVALID,
            errorText = "Ви ввели некоректну назву"
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = "Назва групи повинна складатись від 6 до 18 символів, може містити латинські чи кириличні букви та цифри, не є унікальною",
            fontFamily = redHatDisplayFontFamily,
            fontSize = 12.sp
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Вік дитини",
            fontFamily = redHatDisplayFontFamily,
            fontSize = 14.sp
        )

        var isMinAgeFocused by remember { mutableStateOf(false) }
        var isMaxAgeFocused by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val focusRequester = remember { FocusRequester() }
            OutlinedTextField(
                value = screenState.value.minAge,
                onValueChange = { handleEvent(CreateGroupEvent.UpdateMinAge(it)) },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isMinAgeFocused = it.isFocused
                    },
                label = { Text("Від") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = screenState.value.minAgeValid == ValidField.INVALID && isMinAgeFocused,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                ),
                trailingIcon = {
                    if (screenState.value.minAgeValid == ValidField.INVALID && isMinAgeFocused)
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "error",
                            tint = Color.Red
                        )
                },
                textStyle = TextStyle(
                    fontFamily = redHatDisplayFontFamily
                ),
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(32.dp))
            OutlinedTextField(
                value = screenState.value.maxAge,
                onValueChange = { handleEvent(CreateGroupEvent.UpdateMaxAge(it)) },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isMaxAgeFocused = it.isFocused
                    },
                label = { Text("До") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = screenState.value.maxAgeValid == ValidField.INVALID && isMaxAgeFocused,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                ),
                trailingIcon = {
                    if (screenState.value.maxAgeValid == ValidField.INVALID && isMaxAgeFocused)
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "error",
                            tint = Color.Red
                        )
                },
                textStyle = TextStyle(
                    fontFamily = redHatDisplayFontFamily
                ),
                maxLines = 1
            )
        }

        val minAgeErrorText = "Не може бути менше 1 та більше за макс. вік"
        val maxAgeErrorText = "Не може бути більше 18 та менше за макс. вік"
        val errorText = when {
            screenState.value.maxAgeValid == ValidField.INVALID && isMaxAgeFocused -> maxAgeErrorText
            screenState.value.minAgeValid == ValidField.INVALID && isMinAgeFocused -> minAgeErrorText
            else -> ""
        }
        if (errorText.isNotEmpty()) {
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

        Spacer(modifier = modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Визначіть графік групи",
            fontFamily = redHatDisplayFontFamily,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        ScheduleGroup(
            scheduleModel = screenState.value.schedule,
            onValueChange = { day, period ->
                handleEvent(
                    CreateGroupEvent.UpdateGroupSchedule(
                        day,
                        period
                    )
                )
            }
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Фото групи",
            fontFamily = redHatDisplayFontFamily,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        GroupAvatarWithCameraAndGallery(
            modifier = modifier.fillMaxWidth(),
            avatar = screenState.value.avatar,
//            setUriForCrop = {
//                handleEvent(CreateGroupEvent.SetImageToCrop(it))
//            },
//            onEditPhoto = { handleEvent(CreateGroupEvent.OnEditPhoto) },
//            onDeletePhoto = { handleEvent(CreateGroupEvent.OnDeletePhoto) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = screenState.value.description,
            label = { Text("Опис групи") },
            onValueChange = { handleEvent(CreateGroupEvent.UpdateDescription(it)) },
            minLines = 2,
            maxLines = 2,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            ),
            textStyle = TextStyle(
                fontFamily = redHatDisplayFontFamily
            )
        )

        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(48.dp),
            onClick = {
                handleEvent(CreateGroupEvent.OnCreate)
            },
            enabled = screenState.value.nameValid == ValidField.VALID &&
                    screenState.value.minAgeValid == ValidField.VALID &&
                    screenState.value.maxAgeValid == ValidField.VALID &&
                    screenState.value.schedule.schedule.values.any { it.isFilled() }
        ) {
            ButtonText(
                text = "Створити нову групу"
            )
        }

        if (screenState.value.isLoading) LoadingIndicator()
    }
}

@Composable
@Preview
fun CreateGroupScreenPreview() {
    CreateGroupScreen()
}