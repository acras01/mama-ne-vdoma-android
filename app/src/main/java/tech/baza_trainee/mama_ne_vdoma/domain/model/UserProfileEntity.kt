package tech.baza_trainee.mama_ne_vdoma.domain.model

data class UserProfileEntity(
    val email: String = "",
    val name: String = "",
    val countryCode: String = "",
    val phone: String = "",
    val avatar: String = "",
    val location: LocationEntity = LocationEntity()
)
data class LocationEntity(
    val type: String = "",
    val coordinates: List<Double> = emptyList()
)