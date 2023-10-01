package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.login.model

import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField

data class LoginViewState(
    val emailValid: ValidField = ValidField.EMPTY,
    val passwordValid: ValidField = ValidField.EMPTY
)
