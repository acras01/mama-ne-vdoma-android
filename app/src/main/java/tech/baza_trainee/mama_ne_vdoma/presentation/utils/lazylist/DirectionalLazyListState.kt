package tech.baza_trainee.mama_ne_vdoma.presentation.utils.lazylist

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

class DirectionalLazyListState(
    private val lazyListState: LazyListState
) {
    private var positionY = lazyListState.firstVisibleItemScrollOffset
    private var visibleItem = lazyListState.firstVisibleItemIndex


    val scrollDirection by derivedStateOf {
        if (lazyListState.isScrollInProgress.not()) {
            ScrollDirection.None
        } else {
            val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset =
                lazyListState.firstVisibleItemScrollOffset

            // We are scrolling while first visible item hasn't changed yet
            if (firstVisibleItemIndex == visibleItem) {
                val direction = if (firstVisibleItemScrollOffset > positionY) {
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }
                positionY = firstVisibleItemScrollOffset

                direction
            } else {
                val direction = if (firstVisibleItemIndex > visibleItem) {
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }

                positionY = firstVisibleItemScrollOffset
                visibleItem = firstVisibleItemIndex

                direction
            }
        }
    }
}

@Composable
fun rememberDirectionalLazyListState(lazyListState: LazyListState) =
    remember { DirectionalLazyListState(lazyListState) }

enum class ScrollDirection {
    Up, Down, None
}