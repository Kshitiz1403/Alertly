package com.niraj.alertly.presentation.screens.groupscreen

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niraj.alertly.DI.APIRepository
import com.niraj.alertly.MyApplication
import com.niraj.alertly.data.GroupData
import com.niraj.alertly.data.groupalerts.Alert
import com.niraj.alertly.data.groupalerts.AlertLoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupScreenViewModel : ViewModel() {
    val apiRepository = APIRepository()

    private val _groupList = MutableStateFlow<List<GroupData>>(emptyList())
    val groupList = _groupList.asStateFlow()

    private val _alertList = MutableStateFlow<List<Alert>>(emptyList())
    val alertList = _alertList.asStateFlow()

    private val _alertLoadingState = MutableStateFlow(AlertLoadingState.LOADING)
    val alertLoadingState = _alertLoadingState.asStateFlow()

    private var groupId: Int = 0
    init {
        getGroupList()
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
        getGroupAlerts()
    }
    fun getGroupList() {
        viewModelScope.launch {
            val resp = apiRepository.getGroups()
            if(resp.success) {
                _groupList.emit(resp.data)
            }
        }
    }

    fun getGroupAlerts() {
        val pageNumber = 1
        val pageSize = 30
        viewModelScope.launch {
            _alertLoadingState.emit(AlertLoadingState.LOADING)
            val resp = apiRepository.getGroupAlerts(groupId, pageNumber, pageSize)
            if(resp.success) {
                _alertList.emit(resp.data)
                _alertLoadingState.emit(AlertLoadingState.DONE)
            } else {
                _alertLoadingState.emit(AlertLoadingState.FAILED)
            }
        }
    }
}