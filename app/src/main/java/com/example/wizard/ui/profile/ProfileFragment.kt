package com.example.wizard.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.wizard.R

class ProfileFragment : Fragment() {


    private lateinit var betsFragment: BetsFragment
    private lateinit var statsFragment: StatsFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        betsFragment = BetsFragment()
        statsFragment = StatsFragment()


        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, betsFragment)
            .commit()


        val toggle = view.findViewById<RadioGroup>(R.id.toggle)


        toggle.setOnCheckedChangeListener { _, checkedId ->
            val transaction = childFragmentManager.beginTransaction()
            when (checkedId) {
                R.id.betsposts -> transaction.replace(R.id.fragment_container, betsFragment)
                R.id.stats -> transaction.replace(R.id.fragment_container, statsFragment)
            }
            transaction.commit()
        }
    }


}
