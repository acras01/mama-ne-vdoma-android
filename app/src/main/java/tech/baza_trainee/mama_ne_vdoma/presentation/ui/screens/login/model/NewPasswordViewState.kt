package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.login.model

import tech.baza_trainee.mama_ne_vdoma.presentation.utils.ValidField

data class NewPasswordViewState(
    val password: String = "",
    val confirmPassword: String = "",
    val passwordValid: ValidField = ValidField.EMPTY,
    val confirmPasswordValid: ValidField = ValidField.EMPTY
)