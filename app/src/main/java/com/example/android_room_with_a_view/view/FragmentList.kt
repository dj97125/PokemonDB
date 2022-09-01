package com.example.android_room_with_a_view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_room_with_a_view.common.BaseFragment
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.databinding.FragmentListBinding
import com.example.android_room_with_a_view.domain.response.DataDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentList : BaseFragment() {


    private val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    private val userAdapter by lazy {
        UserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding.recomendedRecycler.apply {
            adapter = userAdapter
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userResponse.collect { state ->
                    when (state) {
                        is StateAction.SUCCESS<*> -> {
                            showToastMessage(state.message)
                            val retrievedUser = state.response as List<DataDomain>
                            binding.apply {
                                userAdapter.erraseData()
                                userAdapter.updateData(retrievedUser)
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
                                viewModel.getUserList()
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
                viewModel.getUserList()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

}