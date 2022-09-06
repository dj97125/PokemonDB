package com.example.android_room_with_a_view.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.DetailsUseCase
import com.example.android_room_with_a_view.domain.PokemonUseCase
import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain
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
    private val pokemonUseCase: PokemonUseCase,
    private val detailsUseCase: DetailsUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _pokemonResponse: MutableStateFlow<StateAction?> = MutableStateFlow(null)
    val pokemonResponse: StateFlow<StateAction?>
        get() = _pokemonResponse.asStateFlow()

    private val _detailsResponse: MutableStateFlow<StateAction?> = MutableStateFlow(null)
    val detailsResponse: StateFlow<StateAction?>
        get() = _detailsResponse.asStateFlow()

    var pokemon: ResultDomain? = null



    init {
        getPokemonList()
    }


    fun getPokemonList() {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    pokemonUseCase().collect { stateAction ->
                        when (stateAction) {
                            is StateAction.SUCCESS<*> -> {
                                val retrievedPokemon = stateAction.response as List<ResultDomain>
                                val retrievedMessage = stateAction.message
                                _pokemonResponse.value =
                                    StateAction.SUCCESS(retrievedPokemon, retrievedMessage)
                                _isLoading.value = false
                            }
                            is StateAction.ERROR -> {
                                val retrievedFailure = stateAction.error
                                _pokemonResponse.value = StateAction.ERROR(retrievedFailure)
                                _isLoading.value = true
                            }
                        }
                    }
                }
            }
        }
    }

    fun getDetailsList(pokemon:String) {
        coroutineScope.launch(handler) {
            supervisorScope {
                launch {
                    detailsUseCase(pokemon).collect { stateAction ->
                        when (stateAction) {
                            is StateAction.SUCCESS<*> -> {
                                val retrievedDetails = stateAction.response as DetailsDomain
                                val retrievedMessage = stateAction.message
                                _detailsResponse.value =
                                    StateAction.SUCCESS(retrievedDetails, retrievedMessage)
                                _isLoading.value = false
                            }
                            is StateAction.ERROR -> {
                                val retrievedFailure = stateAction.error
                                _detailsResponse.value = StateAction.ERROR(retrievedFailure)
                                _isLoading.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}








