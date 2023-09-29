package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.create_user.vm.UserSettingsViewModel

@Composable
fun ParentScheduleFunc(
    viewModel: UserSettingsViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    ScheduleScreen(
        title = "Визначіть свій графік, коли можете доглядати дітей",
        screenState = viewModel.parentScheduleScreenState.collectAsStateWithLifecycle(),
        onUpdateSchedule = { day, period -> viewModel.updateChildSchedule(day, period) },
        onNext = onNext,
        onBack = onBack
    )
}