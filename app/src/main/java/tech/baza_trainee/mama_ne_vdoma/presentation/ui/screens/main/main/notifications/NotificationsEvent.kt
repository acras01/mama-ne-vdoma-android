package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.main.notifications

sealed interface NotificationsEvent {
    data class AcceptUser(val group: String, val child: String): NotificationsEvent
    data class DeclineUser(val group: String, val child: String): NotificationsEvent

    data object ResetUiState: NotificationsEvent

    data object OnBack: NotificationsEvent
}