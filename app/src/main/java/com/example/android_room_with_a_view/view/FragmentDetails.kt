package com.example.android_room_with_a_view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.android_room_with_a_view.common.BaseFragment
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.databinding.FragmentDetailsBinding
import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain
import com.example.android_room_with_a_view.view_model.ViewModel
import kotlinx.coroutines.launch


class FragmentDetails : BaseFragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val pokemonInfo = viewModel.pokemon

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailsResponse.collect { state ->
                    when (state) {
                        is StateAction.SUCCESS<*> -> {
                            showToastMessage(state.message)
                            val retrievedDetails = state.response as DetailsDomain
                            val details: DetailsDomain? = retrievedDetails
                            binding.apply {
                                pokemonName.text = pokemonInfo?.name
                                baseHappines.text = details?.baseHappiness.toString()
                                captureRate.text = details?.captureRate.toString()

                            }
                        }
                        is StateAction.ERROR -> {

                            displayErrors(state.error.localizedMessage) {
                                viewModel.getPokemonList()
                            }
                        }

                    }
                }
            }
        }

        if (pokemonInfo != null) {
            viewModel.getDetailsList(pokemonInfo.name)
        }
        return binding.root
    }


}