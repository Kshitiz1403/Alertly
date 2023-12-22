package com.niraj.alertly.presentation.screens.HomeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niraj.alertly.DI.APIRepository
import com.niraj.alertly.data.GroupData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val apiRepository = APIRepository()

    private val _groupList = MutableStateFlow<List<GroupData>>(emptyList())
    val groupList = _groupList.asStateFlow()

    init {
        getGroupList()
    }
    fun getGroupList() {
        viewModelScope.launch {
            val resp = apiRepository.getGroups()
            if(resp.success) {
                _groupList.emit(resp.data)
            }
            Log.d("RESP", resp.toString())
        }
    }

}