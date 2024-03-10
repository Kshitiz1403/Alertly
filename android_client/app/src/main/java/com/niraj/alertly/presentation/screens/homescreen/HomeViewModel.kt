package com.niraj.alertly.presentation.screens.homescreen

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niraj.alertly.DI.APIRepository
import com.niraj.alertly.MyApplication
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
            Toast.makeText(MyApplication.appContext, "Groups Updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun joinGroup(accessToken: String) {
        viewModelScope.launch {
            val resp = apiRepository.joinGroup(accessToken)
            if(resp.success) {
                Toast.makeText(MyApplication.appContext, "Group Successfully Joined 😄", Toast.LENGTH_SHORT).show()
                getGroupList()
            } else {
                Toast.makeText(MyApplication.appContext, "Failed to join the group 😔", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createGroup(groupName: String, groupDescription: String) {
        viewModelScope.launch {
            val resp = apiRepository.createGroup(groupName, groupDescription)
            if(resp.success) {
                Toast.makeText(MyApplication.appContext, "Create Group $groupName successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(MyApplication.appContext, "Create Group $groupName failed", Toast.LENGTH_LONG).show()
            }
            getGroupList()
        }
    }

}