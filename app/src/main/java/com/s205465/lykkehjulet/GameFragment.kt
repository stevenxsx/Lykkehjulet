package com.s205465.lykkehjulet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    /*override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
        findNavController().navigate(R.id.action_gameFragment_to_gameLostFragment)
    }*/

}