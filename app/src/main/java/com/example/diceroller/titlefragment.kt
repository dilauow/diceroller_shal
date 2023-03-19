package com.example.diceroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.example.diceroller.databinding.FragmentTitlefragmentBinding

// TODO: Rename parameter arguments, choose names that match

class titlefragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentTitlefragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_titlefragment,container,false)
        binding.newGameBtn.setOnClickListener { view : View ->
            Navigation.findNavController(view).navigate(R.id.action_titlefragment_to_playGameFragment)
        }
        binding.about.setOnClickListener {
            val popUp = AboutDialog.newInstance()
            popUp.show(childFragmentManager,"about text")
        }

 // add functions(here) before the return
        return binding.root
    }



}