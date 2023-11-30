package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.standalone.found_group

import android.net.Uri
import androidx.compose.runtime.Stable
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.model.GroupUiModel

data class FoundGroupViewState(
    @Stable val avatar: Uri = Uri.EMPTY,
    val currentUserId: String = "",
    @Stable val groups: List<GroupUiModel> = emptyList(),
    val notifications: Int = 0,
    val isLoading: Boolean = false
)
