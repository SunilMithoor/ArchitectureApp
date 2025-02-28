package com.sunil.app.presentation.viewmodel.movies

import com.sunil.app.base.BaseViewModel
import com.sunil.app.presentation.ui.screens.movies.navigationbar.BottomNavigationBarItem
import com.sunil.app.presentation.ui.util.singleSharedFlow
import com.sunil.app.presentation.util.CodeSnippet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationBarSharedViewModel @Inject constructor(
    codeSnippet: CodeSnippet,
) : BaseViewModel(codeSnippet) {

    private val _bottomItem = singleSharedFlow<BottomNavigationBarItem>()
    val bottomItem = _bottomItem.asSharedFlow()

    fun onBottomItemClicked(bottomItem: BottomNavigationBarItem) = coroutineScope.launch {
        _bottomItem.emit(bottomItem)
    }
}
