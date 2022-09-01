package com.example.android_room_with_a_view.common

sealed class StateAction {
    class SUCCESS<T>(val response: T, val message: String) : StateAction()
    class ERROR(val error: Exception) : StateAction()
}
