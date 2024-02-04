package com.niraj.alertly.presentation.screens.groupscreen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    private var groupId: Int = 0
    init {
        getGroupList()
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
        getGroupAlerts()
        getAccessToken()
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
                _alertList.emit(resp.data.reversed())
                _alertLoadingState.emit(AlertLoadingState.DONE)
            } else {
                _alertLoadingState.emit(AlertLoadingState.FAILED)
            }
        }
    }

    fun createAlert(
        title: String,
        description: String,
        severity: String,
    ) {
        vibratePhone(severity)
        viewModelScope.launch {
            val resp = apiRepository.createAlert(groupId, title, description, severity)
            if(resp.success) {
                Toast.makeText(MyApplication.appContext, "Alert Created Successfully", Toast.LENGTH_SHORT).show()
                getGroupAlerts()
            } else {
                Toast.makeText(MyApplication.appContext, "Failed to create an alert", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getAccessToken() {
        viewModelScope.launch {
            _accessToken.emit("")
            val resp = apiRepository.getAccessToken(groupId)
            if(resp.success) {
                _accessToken.emit(resp.data)
            } else {
                Toast.makeText(MyApplication.appContext, "Failed to get access token", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun vibratePhone(severity: String) {
        val time: Long = if(severity == "Normal") 200 else if(severity == "Elevated") 500 else 800
        val vibrator = MyApplication.appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE))
    }

}