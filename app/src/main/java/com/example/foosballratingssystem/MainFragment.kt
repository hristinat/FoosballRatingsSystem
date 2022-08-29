package com.example.foosballratingssystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.foosballratingssystem.databinding.FragmentMainBinding
import com.example.foosballratingssystem.results.ResultsFragment
import com.example.foosballratingssystem.standings.StandingsFragment

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        setupViewPager(binding.container)
        binding.tabs.setupWithViewPager(binding.container)

        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        adapter.addFragment(StandingsFragment(), getString(R.string.standings))
        adapter.addFragment(ResultsFragment(), getString(R.string.results))
        viewPager.adapter = adapter
    }
}