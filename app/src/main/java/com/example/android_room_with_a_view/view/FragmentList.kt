package com.example.android_room_with_a_view.view

import com.example.android_room_with_a_view.R
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
import com.example.android_room_with_a_view.common.OnPokemonClicked
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.databinding.FragmentListBinding
import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentList : BaseFragment(), OnPokemonClicked {


    private val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    private val pokemonAdapter by lazy {
        PokemonAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding.recomendedRecycler.apply {
            adapter = pokemonAdapter
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonResponse.collect { state ->
                    when (state) {
                        is StateAction.SUCCESS<*> -> {
                            showToastMessage(state.message)
                            val retrievedUser = state.response as List<ResultDomain>
                            binding.apply {
                                pokemonAdapter.erraseData()
                                pokemonAdapter.updateData(retrievedUser)
                                swipeRefresh.visibility = View.VISIBLE
                                recomendedRecycler.visibility = View.VISIBLE

                            }
                        }
                        is StateAction.ERROR -> {
                            binding.apply {
                                recomendedRecycler.visibility = View.GONE
                                swipeRefresh.visibility = View.GONE
                            }
                            displayErrors(state.error.localizedMessage) {
                                viewModel.getPokemonList()
                            }
                        }

                    }
                }
            }
        }



        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.swipeRefresh.apply {
            setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_green_dark,
            )
            binding.swipeRefresh.setOnRefreshListener {
                viewModel.getPokemonList()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun pokemonClicked(pokemon: ResultDomain) {
        viewModel.pokemon = pokemon
        Log.d("Fragment", "pokemonClicked: $pokemon")
        findNavController().navigate(R.id.action_fragmentList_to_fragmentDetails)
    }


}