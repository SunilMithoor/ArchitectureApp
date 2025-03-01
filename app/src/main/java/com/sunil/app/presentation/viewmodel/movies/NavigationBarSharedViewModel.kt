package com.sunil.app.presentation.viewmodel.movies

import com.sunil.app.base.BaseViewModel
import com.sunil.app.presentation.ui.screens.movies.navigationbar.BottomNavigationBarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel responsible for managing the state of the bottom navigation bar.
 *
 * This ViewModel handles the selection of items in the bottom navigation bar and
 * exposes the selected item through a SharedFlow.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-28
 */
@HiltViewModel
class NavigationBarSharedViewModel @Inject constructor() : BaseViewModel() {

    /**
     * MutableSharedFlow to hold the currently selected item in the bottom navigation bar.
     *
     * This is private to prevent external modification of the selected item.*
     * replay = 0: No items are replayed to new collectors.
     * extraBufferCapacity = 1: Allows buffering one item if no collector is currently active.
     */
    private val _selectedBottomItem = MutableSharedFlow<BottomNavigationBarItem>(
        replay = 0,
        extraBufferCapacity = 1
    )
//    private val _selectedBottomItem = singleSharedFlow<BottomNavigationBarItem>()

    /**
     * Publicly exposed SharedFlow for observing the currently selected item in the bottom navigation bar.
     *
     * UI components should collect from this flow to update their state.
     */
    val selectedBottomItem: SharedFlow<BottomNavigationBarItem> = _selectedBottomItem.asSharedFlow()

    /**
     * Selects a new item in the bottom navigation bar.
     *
     * This function emits the selected item to the [_selectedBottomItem] SharedFlow.
     *
     * @param bottomItem The item that was selected in the bottom navigation bar.
     */
    fun onBottomItemClicked(bottomItem: BottomNavigationBarItem) = coroutineScope.launch {
        _selectedBottomItem.emit(bottomItem)
    }
}
