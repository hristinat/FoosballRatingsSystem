package com.example.foosballratingssystem.standings

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foosballratingssystem.MyApplication
import com.example.foosballratingssystem.R
import com.example.foosballratingssystem.database.ResultsRepository
import com.example.foosballratingssystem.databinding.FragmentStandingsBinding
import javax.inject.Inject

class StandingsFragment : Fragment() {

    @Inject
    lateinit var resultsRepository: ResultsRepository

    private val viewModel by viewModels<StandingsViewModel> {
        StandingsViewModelFactory(resultsRepository)
    }

    private val standingsAdapter = StandingsAdapter()
    private lateinit var binding: FragmentStandingsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater, container, false)
        setupStandingsRecyclerView(binding)
        observeStandingsGroupByGameWon()
        setUpOptionsMenu()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getResults()
    }

    private fun initDependencies() {
        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)
    }

    private fun setUpOptionsMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.standings, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_group_games_played -> {
                        setData(
                            viewModel.standingsGroupByGamePlayed.value,
                            resources.getText(R.string.games_played)
                        )
                        true
                    }
                    R.id.action_group_games_won -> {
                        setData(
                            viewModel.standingsGroupByGameWon.value,
                            resources.getText(R.string.games_won)
                        )
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupStandingsRecyclerView(binding: FragmentStandingsBinding) {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.standingsList.layoutManager = linearLayoutManager
        binding.standingsList.adapter = standingsAdapter
    }

    private fun observeStandingsGroupByGameWon() {
        viewModel.standingsGroupByGameWon.observe(viewLifecycleOwner, {
            if (it != null) {
                standingsAdapter.setStandings(it)
            }
        })
    }

    private fun setData(standings: List<Standings>?, scoreText: CharSequence) {
        standingsAdapter.setStandings(standings)
        binding.score.text = scoreText
    }
}