package com.android.fetchtest.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchtest.data.Item
import com.android.fetchtest.data.NoteState
import com.android.fetchtest.usecase.ApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val apiUseCase: ApiUseCase) : ViewModel() {
    private val _allItems = MutableStateFlow(NoteState())
    val stateFlow: StateFlow<NoteState> = _allItems

    init {
        fetchItems()
    }

    fun fetchItems() {
        //Here we no need to change the dispatcher of the coroutine bez i'm using coroutine 1.7.3
        //version so internal of suspend function will automatically change the dispatcher of the coroutine.
        viewModelScope.launch {
            _allItems.value = _allItems.value.copy(isLoading = true, isFailed = false)
            try {
                val fetchedItems = apiUseCase.getItems()
                val finalItemsList = fetchedItems.filter {
                        !it.name.isNullOrBlank()
                    }
                    .sortedWith(compareBy<Item> {
                        it.listId
                    }.thenBy {
                        it.name
                    })
                _allItems.value = _allItems.value.copy(itemList = finalItemsList, isLoading = false, isFailed = false)
            }
            catch (ex: Exception) {
                _allItems.value = _allItems.value.copy(isLoading = false, isFailed = true, errorMsg = ex.message ?: "SomeThing Went Wrong Try Again Later!")
            }
        }
    }

}