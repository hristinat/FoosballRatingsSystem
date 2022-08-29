package com.example.foosballratingssystem.results

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foosballratingssystem.MainFragmentDirections
import com.example.foosballratingssystem.MyApplication
import com.example.foosballratingssystem.database.Results
import com.example.foosballratingssystem.database.ResultsRepository
import com.example.foosballratingssystem.databinding.FragmentResultsBinding
import javax.inject.Inject

class ResultsFragment : Fragment(), ResultsAdapter.ResultsAdapterOnClickHandler {

    @Inject
    lateinit var resultsRepository: ResultsRepository

    private val viewModel by viewModels<ResultsViewModel> {
        ResultsViewModelFactory(resultsRepository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val resultsAdapter = ResultsAdapter(this)
        setupResultsRecyclerView(binding, resultsAdapter)

        observeNavigateToAddResult()
        observeResults(resultsAdapter)

        return binding.root
    }

    private fun setupResultsRecyclerView(
        binding: FragmentResultsBinding,
        resultsAdapter: ResultsAdapter
    ): ResultsAdapter {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.resultsList.layoutManager = linearLayoutManager
        binding.resultsList.adapter = resultsAdapter
        return resultsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getResults()
    }

    private fun initDependencies() {
        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)
    }

    private fun observeResults(resultsAdapter: ResultsAdapter) {
        viewModel.results.observe(viewLifecycleOwner, {
            if (it != null) {
                resultsAdapter.setResults(it)
            }
        })
    }

    private fun observeNavigateToAddResult() {
        viewModel.navigateToAddResult.observe(viewLifecycleOwner, {
            if (it) {
                navigateToAddResult()
                viewModel.onNavigatedToAddResult()
            }
        })
    }

    private fun navigateToAddResult(resultId: Long = 0) {
        this.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToAddResultFragment(resultId))
    }

    override fun onClick(result: Results) {
        navigateToAddResult(result.id)
    }
}