package tech.baza_trainee.mama_ne_vdoma.presentation.navigation.navigator


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.routes.CommonRoute
import tech.baza_trainee.mama_ne_vdoma.presentation.navigation.routes.MainScreenRoutes
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.common.MAIN_PAGE
import java.util.Deque
import java.util.LinkedList

interface PageNavigator {

    val navigationChannel: Channel<NavigationIntent>

    val pagesFlow: StateFlow<Int>

    fun goBack()

    fun goBackOnMain(scope: CoroutineScope)

    fun navigate(route: CommonRoute)

    fun navigateOnMain(scope: CoroutineScope, route: CommonRoute)

    fun goToPage(page: Int)

    fun goToPrevious()

    fun getCurrentRoute(): String
}

class PageNavigatorImpl: PageNavigator {

    override val pagesFlow: StateFlow<Int>
        get() = _pagesFlow.asStateFlow()
    private val _pagesFlow = MutableStateFlow(MAIN_PAGE)
    private val pagesQueue: Deque<Int> = LinkedList()

    private val routesQueue: Deque<String> = LinkedList()

    private var goBack = false

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override fun goBack() {
        routesQueue.pollLast()
        navigationChannel.trySend(NavigationIntent.NavigateBack)
    }

    override fun goBackOnMain(scope: CoroutineScope) {
        routesQueue.pollLast()
        scope.launch {
            withContext(Dispatchers.Main) {
                navigationChannel.send(NavigationIntent.NavigateBack)
            }
        }
    }

    override fun navigate(route: CommonRoute) {
        if (!goBack) {
            routesQueue.offerLast(route.destination)
        }
        goBack = false
        navigationChannel.trySend(NavigationIntent.NavigateTo(route))
    }

    override fun navigateOnMain(scope: CoroutineScope, route: CommonRoute) {
        if (!goBack)
            routesQueue.offerLast(route.destination)
        goBack = false
        scope.launch {
            withContext(Dispatchers.Main) {
                navigationChannel.send(NavigationIntent.NavigateTo(route))
            }
        }
    }

    override fun goToPage(page: Int) {
        if (page == MAIN_PAGE) {
            pagesQueue.clear()
            routesQueue.clear()
        }
        pagesQueue.offerLast(page)
        _pagesFlow.update {
            page
        }
    }

    override fun goToPrevious() {
        goBack = true
        pagesQueue.pollLast()
        routesQueue.pollLast()
        val page = pagesQueue.peekLast() ?: MAIN_PAGE
        if (page == MAIN_PAGE) {
            pagesQueue.clear()
            routesQueue.clear()
        }
        _pagesFlow.update {

            page
        }
    }

    override fun getCurrentRoute() = routesQueue.peekLast() ?: MainScreenRoutes.Main.destination
}