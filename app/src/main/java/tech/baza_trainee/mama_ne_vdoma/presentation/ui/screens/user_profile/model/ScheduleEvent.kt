package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.model

import tech.baza_trainee.mama_ne_vdoma.domain.model.Period
import java.time.DayOfWeek

sealed interface ScheduleEvent {
    object SetCurrentChildSchedule: ScheduleEvent
    data class UpdateChildSchedule(val day: DayOfWeek, val period: Period) : ScheduleEvent
    data class UpdateChildComment(val comment: String): ScheduleEvent
}