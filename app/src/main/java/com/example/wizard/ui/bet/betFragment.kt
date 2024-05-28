package com.example.wizard.ui.bet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wizard.R

class BetFragment : Fragment() {

    private lateinit var eventTitleTextView: TextView
    private lateinit var eventIdTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bet, container, false)
    }
}