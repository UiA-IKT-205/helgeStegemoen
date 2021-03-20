package com.example.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.superpiano.databinding.FragmentFullTonePianoKeyBinding
import kotlinx.android.synthetic.main.fragment_full_tone_piano_key.view.*

class FullTonePianoKeyFragment : Fragment() {
    private var _binding:FragmentFullTonePianoKeyBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:String

    /* ((note:String) -> Unit) is a function declaration,that takes in a String parameter, and
    // returns something/Unit (kinda like void)
    // val lambdaName : Type = { argumentList -> codeBody }*/
    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?" // if(it..) else "?" | lambda
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullTonePianoKeyBinding.inflate(inflater)
        val view = binding.root

        view.fullToneKey.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    // Since we're in many layers of anonymous layers, we have to use this, to tell
                    // the compiler that we want to use this as this instance of the
                    // FullTonePianoKeyFragment class
                    // (invoke = run)
                    MotionEvent.ACTION_DOWN -> this@FullTonePianoKeyFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@FullTonePianoKeyFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_full_tone_piano_key, container, false)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(note: String) =
                FullTonePianoKeyFragment().apply {
                    arguments = Bundle().apply {
                        putString("NOTE", note)
                    }
                }
    }
}