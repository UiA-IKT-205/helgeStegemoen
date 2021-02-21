package com.example.superpiano

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.superpiano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*

class PianoLayout : Fragment() {
    // build.gradle(module) feature viewBinding, enables the FragmentPianoLayoutBinding class for us
    /* View binding is a feature that allows you to more easily write code that interacts with
    views. Once view binding is enabled in a module, it generates a binding class for each
    XML layout file present in that module. An instance of a binding class contains direct
    references to all views that have an ID in the corresponding layout. In most cases, view
    binding replaces findViewById. */
    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2","D2", "E2", "F2", "G2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /* onCreateView(LayoutInflater, ViewGroup, Bundle) creates and returns the view hierarchy
       associated with the fragment.
       LayoutInflater: Instantiates a layout XML file into its corresponding View object.
       ViewGroup: A ViewGroup is a special view that can contain other views (called children.)*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()  // starts a series of edit operations

        fullTones.forEach(){
            // newInstance() uses FullTonePianoKeyFragment.kt's companion object to set up
            // properties
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            fullTonePianoKey.onKeyDown = {
                println("Piano key down $it")
            }

            fullTonePianoKey.onKeyUp = {
                println("Piano key up $it")
            }

            ft.add(view.pianoKeys.id,fullTonePianoKey,"note_$it")
        }

        ft.commit()

        return view
    }
}