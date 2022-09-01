package com.example.android_room_with_a_view.view_model

import androidx.lifecycle.ViewModel
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.UserUseCase
import com.example.android_room_with_a_view.domain.response.DataDomain
import com.example.android_room_with_a_view.domain.response.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val handler: CoroutineExceptionHandler,
    private val userUseCase: UserUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _userResponse: MutableStateFlow<StateAction?> = MutableStateFlow(null)
    val userResponse: StateFlow<StateAction?>
        get() = _userResponse.asStateFlow()


    init {
        getUserList()
    }


    fun getUserList() {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    userUseCase().collect { stateAction ->
                        when (stateAction) {
                            is StateAction.SUCCESS<*> -> {
                                val retrievedUser = stateAction.response as List<DataDomain>
                                val retrievedMessage = stateAction.message
                                _userResponse.value =
                                    StateAction.SUCCESS(retrievedUser, retrievedMessage)
                                _isLoading.value = false
                            }
                            is StateAction.ERROR -> {
                                val retrievedFailure = stateAction.error
                                _userResponse.value = StateAction.ERROR(retrievedFailure)
                                _isLoading.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}








