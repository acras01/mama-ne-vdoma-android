package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.common.add_child

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.ChildInfoGroup
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.RequestState

@Composable
fun ChildInfoScreen(
    modifier: Modifier = Modifier,
    screenState: State<ChildInfoViewState> = mutableStateOf(ChildInfoViewState()),
    uiState: State<RequestState> = mutableStateOf(RequestState.Idle),
    handleEvent: (ChildInfoEvent) -> Unit = { _ -> }
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        BackHandler { handleEvent(ChildInfoEvent.OnBack) }

        val context = LocalContext.current

        when (val state = uiState.value) {
            RequestState.Idle -> Unit
            is RequestState.OnError -> {
                if (state.error.isNotBlank()) Toast.makeText(
                    context,
                    state.error,
                    Toast.LENGTH_LONG
                ).show()
                handleEvent(ChildInfoEvent.ResetUiState)
            }
        }

        ChildInfoGroup(
            modifier = Modifier.fillMaxWidth(),
            screenState = screenState,
            handleEvent = handleEvent
        )
    }
}

@Composable
@Preview
fun ChildInfoPreview() {
    ChildInfoScreen()
}