package com.example.android_room_with_a_view.common

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android_room_with_a_view.view_model.ViewModel

open class BaseFragment : Fragment() {
    protected val viewModel: ViewModel by activityViewModels()


    fun displayErrors(
        message: String = "A moment please! Working on the issues",
        retry: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error occurred")
            .setPositiveButton("RETRY") { dialog, _ ->
                dialog.dismiss()
                retry()
            }
            .setNegativeButton("DISMISS") { dialog, _ ->
                dialog.dismiss()
            }
            .setMessage(message)
            .create()
            .show()
    }

    fun showToastMessage(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}