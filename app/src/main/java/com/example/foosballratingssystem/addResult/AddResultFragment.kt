package com.example.foosballratingssystem.addResult

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foosballratingssystem.MyApplication
import com.example.foosballratingssystem.R
import com.example.foosballratingssystem.database.ResultsRepository
import com.example.foosballratingssystem.databinding.FragmentAddResultBinding
import javax.inject.Inject

class AddResultFragment : Fragment() {

    @Inject
    lateinit var resultsRepository: ResultsRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddResultBinding.inflate(inflater, container, false)
        val viewModel = getResultViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeNavigateToMainFragment(viewModel)
        observeShowEnterAllFieldsToastMessage(viewModel)

        return binding.root
    }

    private fun initDependencies() {
        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)
    }

    private fun getResultViewModel(): AddResultViewModel {
        val arguments = AddResultFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = AddResultViewModelFactory(arguments.resultId, resultsRepository)
        return ViewModelProvider(this, viewModelFactory)[AddResultViewModel::class.java]
    }

    private fun observeNavigateToMainFragment(viewModel: AddResultViewModel) {
        viewModel.navigateToMainFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().popBackStack()
                viewModel.onNavigatedToMainFragment()
            }
        })
    }

    private fun observeShowEnterAllFieldsToastMessage(viewModel: AddResultViewModel) {
        viewModel.showEnterValidDataToastMessage.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    context,
                    resources.getText(R.string.please_enter_valid_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}