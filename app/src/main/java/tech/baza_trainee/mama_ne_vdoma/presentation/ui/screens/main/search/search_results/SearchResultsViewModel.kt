package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.search.search_results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.baza_trainee.mama_ne_vdoma.domain.repository.UserProfileRepository
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.navigator.ScreenNavigator
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.common.SearchResultsCommunicator
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.RequestState
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.execute
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.networkExecutor
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onError
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onLoading
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.onSuccess

class SearchResultsViewModel(
    private val navigator: ScreenNavigator,
    private val communicator: SearchResultsCommunicator,
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    private val _viewState = MutableStateFlow(SearchResultsViewState())
    val viewState: StateFlow<SearchResultsViewState> = _viewState.asStateFlow()

    private val _uiState = mutableStateOf<RequestState>(RequestState.Idle)
    val uiState: State<RequestState>
        get() = _uiState

    init {
        getUserAvatar(communicator.avatarId, communicator.user.id)
        _viewState.update {
            it.copy(
                parent = communicator.user
            )
        }
    }

    fun handleEvent(event: SearchResultsEvent) {
        when(event) {
            SearchResultsEvent.OnBack -> navigator.goBack()
            SearchResultsEvent.ResetUiState -> _uiState.value = RequestState.Idle
            SearchResultsEvent.OnNewSearch -> navigator.goBack()
        }
    }

    private fun getUserAvatar(avatarId: String, userId: String) {
        networkExecutor {
            execute { userProfileRepository.getUserAvatar(avatarId) }
            onSuccess { bmp ->
                _viewState.update {
                    val currentParent = _viewState.value.parent.copy(
                        avatar = bmp
                    )
                    it.copy(parent = currentParent)
                }
            }
            onError { error ->
                _uiState.value = RequestState.OnError(error)
            }
            onLoading { isLoading ->
                _viewState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}