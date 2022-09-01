package com.example.android_room_with_a_view.common


class NullResponseException(
    message: String = "Response is null"
) : Exception(message)

class FailedNetworkResponseException(
    message: String = "Error: failure in the network response"
) : Exception(message)

class FailedCacheResponseException(
    message: String = "Error: failure in the cache response"
) : Exception(message)