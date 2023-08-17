package com.sis.base.presention.main.home.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sis.base.databinding.FragmentMovieDetailBinding
import com.sis.base.presention.base.BaseFragment
import com.sis.base.presention.main.home.movieDetail.viewmodel.MovieDetailListIntent
import com.sis.base.presention.main.home.movieDetail.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel: MovieDetailViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailBinding
        get() = FragmentMovieDetailBinding::inflate

    override fun setupUi() {
        with(binding) {
            vm = viewModel
            lifecycleOwner = this@MovieDetailFragment
        }
    }


    override fun setupData() {
        arguments?.let {
            viewModel.onTriggerEvent(MovieDetailListIntent.RequestItemDetail(MovieDetailFragmentArgs.fromBundle(it).movieId))
        }?: kotlin.run {
            activity?.onBackPressed()
        }

    }

    override fun isFetchData(): Boolean {
        return viewModel.isFetchData()
    }


}