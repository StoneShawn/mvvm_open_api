package com.shawn.mvvm_cathybk.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Engine.LoadStatus
import com.shawn.common.fragment.BaseFragment
import com.shawn.common.utils.LanguageUtils
import com.shawn.mvvm_cathybk.R
import com.shawn.mvvm_cathybk.adapter.ReposLoadStateAdapter
import com.shawn.mvvm_cathybk.databinding.FragmentHomeBinding
import com.shawn.mvvm_cathybk.main.HomeActivity
import com.shawn.network.NetWorkManager
import com.shawn.network.model.Attraction
import com.shawn.network.repository.AttractionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment(), HomeFragmentHandler {

    private lateinit var binding: FragmentHomeBinding
    private var viewModel: HomeViewModel? = null
    private val repository: AttractionRepository get() = AttractionRepository()
    private lateinit var pagingAdapter: HomeAdapter

    private var language: String = "zh-tw"

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = HomeViewModel(repository)
        initView()
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initView() {
        pagingAdapter = HomeAdapter(object : HomeAdapter.ClickListener {
            override fun onClick(data: Attraction) {
                goDetail(data)
            }
        })

        val adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter {
                pagingAdapter.retry()
            },
            footer = ReposLoadStateAdapter { pagingAdapter.retry() }
        )
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }
    }

    private fun initViewModel() {
        // fragment
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel?.getAttractions(LanguageUtils.lang.ZH_TW.lang)?.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && pagingAdapter.itemCount == 0
                // show empty list
                binding.emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds.
                binding.recyclerView.isVisible = !isListEmpty
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading || loadState.source.append is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

    }

    fun changeLanguage(language: String) {
        this.language = language
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel?.getAttractions(language)?.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun goDetail(data: Attraction) {
        getActivityHandler(HomeActivity::class.java)?.goDetail(data)
    }
}