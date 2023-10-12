package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.search_group.set_area

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.baza_trainee.mama_ne_vdoma.domain.model.UserLocationEntity
import tech.baza_trainee.mama_ne_vdoma.domain.repository.LocationRepository
import tech.baza_trainee.mama_ne_vdoma.domain.repository.UserProfileRepository
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.navigator.ScreenNavigator
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.routes.UserProfileRoutes
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.common.CommonUiState
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.user_profile.model.UserProfileCommunicator
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.execute
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.networkExecutor
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onError
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onLoading
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onStart
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onSuccess

class SetAreaViewModel(
    private val communicator: UserProfileCommunicator,
    private val navigator: ScreenNavigator,
    private val userProfileRepository: UserProfileRepository,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _locationScreenState = MutableStateFlow(SetAreaViewState())
    val locationScreenState: StateFlow<SetAreaViewState> = _locationScreenState.asStateFlow()

    private val _uiState = mutableStateOf<CommonUiState>(CommonUiState.Idle)
    val uiState: State<CommonUiState>
        get() = _uiState

    init {
        _locationScreenState.update {
            it.copy(
                address = communicator.address
            )
        }
        if (communicator.address.isNotEmpty())
            getLocationFromAddress()
    }

    fun handleUserLocationEvent(event: SetAreaEvent) {
        when(event) {
            SetAreaEvent.ResetUiState -> _uiState.value = CommonUiState.Idle
            SetAreaEvent.GetLocationFromAddress -> getLocationFromAddress()
            SetAreaEvent.RequestSetArea -> requestCurrentLocation()
            is SetAreaEvent.UpdateUserAddress -> updateUserAddress(event.address)
            SetAreaEvent.SaveSetArea -> saveUserLocation()
            is SetAreaEvent.OnMapClick -> setLocation(event.location)
        }
    }

    private fun setLocation(location: LatLng) {
        _locationScreenState.update {
            it.copy(
                currentLocation = location
            )
        }
        getAddressFromLocation(location)
    }

    private fun saveUserLocation() {
        networkExecutor {
            onStart {
                getAddressFromLocation(
                    LatLng(
                        _locationScreenState.value.currentLocation.latitude,
                        _locationScreenState.value.currentLocation.longitude
                    )
                )
            }
            execute {
                userProfileRepository.saveUserLocation(
                    UserLocationEntity(
                        lat = _locationScreenState.value.currentLocation.latitude,
                        lon = _locationScreenState.value.currentLocation.longitude
                    )
                )
            }
            onSuccess {
                navigator.navigateOnMain(viewModelScope, UserProfileRoutes.ParentSchedule)
            }
            onError { error ->
                _uiState.value = CommonUiState.OnError(error)
            }
            onLoading { isLoading ->
                _locationScreenState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
        }
    }

    private fun requestCurrentLocation() {
        networkExecutor<LatLng?> {
            execute {
                locationRepository.getCurrentLocation()
            }
            onSuccess { location ->
                location?.let {
                    _locationScreenState.update {
                        it.copy(
                            currentLocation = location
                        )
                    }
                    getAddressFromLocation(location)
                }
            }
            onError { error ->
                _uiState.value = CommonUiState.OnError(error)
            }
            onLoading { isLoading ->
                _locationScreenState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
        }
    }

    private fun updateUserAddress(address: String) {
        communicator.address = address
        _locationScreenState.update {
            it.copy(
                address = address
            )
        }
    }

    private fun getLocationFromAddress() {
        networkExecutor<LatLng?> {
            execute {
                locationRepository.getLocationFromAddress(_locationScreenState.value.address)
            }
            onSuccess { location ->
                location?.let {
                    if (_locationScreenState.value.currentLocation != location)
                        _locationScreenState.update {
                            it.copy(
                                currentLocation = location
                            )
                        }
                }
            }
            onError { error ->
                _uiState.value = CommonUiState.OnError(error)
            }
            onLoading { isLoading ->
                _locationScreenState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
        }
    }

    private fun getAddressFromLocation(latLng: LatLng) {
        networkExecutor<String?> {
            execute {
                locationRepository.getAddressFromLocation(latLng)
            }
            onSuccess {
                updateUserAddress(it.orEmpty())
            }
            onError { error ->
                _uiState.value = CommonUiState.OnError(error)
            }
            onLoading { isLoading ->
                _locationScreenState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}