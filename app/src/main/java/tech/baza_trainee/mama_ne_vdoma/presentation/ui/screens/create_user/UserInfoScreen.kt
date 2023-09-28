package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canopas.campose.countrypicker.CountryPickerBottomSheet
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user.model.UserInfoViewState
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user.vm.UserSettingsViewModel
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.SlateGray
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.OutlinedTextFieldWithError
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField

@Composable
fun UserInfoFunc(
    viewModel: UserSettingsViewModel,
    onCreateUser: () -> Unit,
    onEditPhoto: () -> Unit
) {
    UserInfo(
        screenState = viewModel.userInfoScreenState.collectAsStateWithLifecycle(),
        validateName = { viewModel.validateUserName(it) },
        setCode = { viewModel.setCode(it) },
        validatePhone = { viewModel.validatePhone(it) },
        setUriForCrop = { viewModel.setUriForCrop(it) },
        onCreateUser = onCreateUser,
        onEditPhoto = onEditPhoto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    screenState: State<UserInfoViewState> = mutableStateOf(UserInfoViewState()),
    validateName: (String) -> Unit = {},
    setCode: (String) -> Unit = {},
    validatePhone: (String) -> Unit = {},
    setUriForCrop: (Uri) -> Unit = {},
    onCreateUser: () -> Unit,
    onEditPhoto: () -> Unit
) {
    Surface(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var openBottomSheet by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = modifier
                .imePadding()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp),
                    text = "Заповнення профілю",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val launcher =
                    rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
                        it?.let {
                            setUriForCrop(it)
                            onEditPhoto()
                        }
                    }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(screenState.value.userAvatar)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.no_photo),
                    fallback = painterResource(id = R.drawable.no_photo),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp)
                        .width(172.dp)
                        .height(172.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch(arrayOf("image/*"))
                        }
                )

                OutlinedTextFieldWithError(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp),
                    text = screenState.value.userName,
                    label = "Вкажіть своє ім'я",
                    onValueChange = { validateName(it) },
                    isError = screenState.value.nameValid == ValidField.INVALID,
                    errorText = "Ви ввели некоректнe ім'я"
                )

                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 4.dp),
                    text = "Ваше ім’я повинне складатись із 6-18 символів і може містити букви та цифри",
                    textAlign = TextAlign.Start
                )

                var isPhoneFocused by remember { mutableStateOf(false) }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = modifier
                            .weight(.25f)
                            .padding(start = 24.dp)
                            .clickable {
                                openBottomSheet = true
                            },
                        value = screenState.value.code,
                        label = { Text("Код") },
                        onValueChange = {},
                        enabled = false,
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SlateGray,
                            unfocusedContainerColor = SlateGray,
                            disabledContainerColor = SlateGray,
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            disabledBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                    )

                    val focusRequester = remember { FocusRequester() }

                    OutlinedTextField(
                        modifier = modifier
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                isPhoneFocused = it.isFocused
                            }
                            .weight(.75f)
                            .padding(end = 24.dp),
                        value = screenState.value.userPhone,
                        label = { Text("Введіть свій номер телефону") },
                        onValueChange = { validatePhone(it) },
                        isError = screenState.value.phoneValid == ValidField.INVALID && isPhoneFocused,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            disabledBorderColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
                        enabled = screenState.value.code.isNotEmpty()
                    )
                }
                if (screenState.value.phoneValid == ValidField.INVALID && isPhoneFocused) {
                    Text(
                        text = "Ви ввели некоректний номер",
                        color = Color.Red,
                        modifier = modifier
                            .padding(horizontal = 24.dp)
                    )
                }
            }

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                onClick = onCreateUser,
                enabled = screenState.value.nameValid == ValidField.VALID &&
                        screenState.value.phoneValid == ValidField.VALID &&
                        screenState.value.code.isNotEmpty()
            ) {
                Text(text = "Далі")
            }

            if (openBottomSheet) {
                CountryPickerBottomSheet(
                    bottomSheetTitle = {
                        Text(
                            modifier = Modifier
                                .imePadding()
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "Виберіть код",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    onItemSelected = {
                        setCode(it.dial_code)
                        openBottomSheet = false
                    }, onDismissRequest = {
                        openBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun UserInfoPreview() {
    UserInfo(
        onCreateUser = {},
        onEditPhoto = {}
    )
}