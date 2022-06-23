package com.example.herobattler

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.herobattler.databinding.FragmentMainMenuBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainMenu : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.buttonPlay.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_play)
        }

        binding.buttonCharacters.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_characters)
        }

        binding.buttonHowToPlay.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_howToPlay)
        }

        binding.buttonSettings.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}