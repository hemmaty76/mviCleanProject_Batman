package com.sis.base.presention.main.home.movieItems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sis.base.databinding.FragmentItemsBinding
import com.sis.base.presention.base.BaseFragment
import com.sis.base.presention.main.home.movieItems.viewModel.ItemsMovieIntent
import com.sis.base.presention.main.home.movieItems.viewModel.ItemsMovieState
import com.sis.base.presention.main.home.movieItems.viewModel.ItemsMovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemsMovieFragment : BaseFragment<FragmentItemsBinding>() {

    private val viewModel: ItemsMovieViewModel by viewModels()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemsBinding
        get() = FragmentItemsBinding::inflate

    override fun setupUi() {
        with(binding) {
            vm = viewModel
            lifecycleOwner = this@ItemsMovieFragment
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is ItemsMovieState.RequestMovieDetail -> {
                    findNavController().navigate(ItemsMovieFragmentDirections.actionItemsMovieFragmentToMovieDetailFragment(it.movieId))
                }

                is ItemsMovieState.Idle -> {}
            }


        }
    }

    override fun setupData() {
        viewModel.onTriggerEvent(ItemsMovieIntent.GetItemList)
    }

    override fun isFetchData(): Boolean {
        return viewModel.isFetchData()
    }

    override fun onStop() {
        viewModel.dataState.postValue(ItemsMovieState.Idle)
        super.onStop()
    }

}