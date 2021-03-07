package com.example.superpiano

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import java.io.File
import java.io.FileOutputStream

class PianoLayout : Fragment() {
    /* View binding is a feature that allows you to more easily write code that interacts with
    views. Once view binding is enabled in a module, it generates a binding class for each
    XML layout file present in that module. An instance of a binding class contains direct
    references to all views that have an ID in the corresponding layout. In most cases, view
    binding replaces findViewById. */
    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!
    private var score = mutableListOf<Note>()

    private var musicStart:Long = 0
    private var isPlaying:Boolean = false

    /*private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2","D2", "E2", "F2", "G2")
    private val halfTones = listOf("C#", "D#", "F#", "G#", "A#", "C2#", "D2#", "F2#")*/
    private val allTones = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
            "C2", "C2#", "D2", "D2#", "E2", "F2", "F2#", "G2")

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    /* onCreateView(LayoutInflater, ViewGroup, Bundle) creates and returns the view hierarchy
       associated with the fragment.
       LayoutInflater: Instantiates a layout XML file into its corresponding View object.
       ViewGroup: A ViewGroup is a special view that can contain other views (called children.)*/
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        allTones.forEach(){ orgNoteValue ->
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(orgNoteValue)
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(orgNoteValue)
            var startPlay: Long =0

            val pattern = ".*#".toRegex()

            if(pattern.containsMatchIn(orgNoteValue)){
                halfTonePianoKey.onKeyDown = {
                    if(!isPlaying){
                        startMusic()
                    } else {
                        startPlay = System.nanoTime() - musicStart
                    }
                    println("Piano key down $it")
                }

                halfTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime() - musicStart
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it")
                }
                ft.add(view.pianoKeys.id, halfTonePianoKey, "note_$orgNoteValue")

            } else {
                fullTonePianoKey.onKeyDown = {
                    if(!isPlaying){
                        startMusic()
                    } else {
                        startPlay = System.nanoTime() - musicStart
                    }
                    println("Piano key down $it")
                }

                fullTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime()
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it")
                }
            }
                ft.add(view.pianoKeys.id,fullTonePianoKey,"note_$orgNoteValue")
            }
            ft.commit()

        // Sjekk om det finnes bedre maate aa aapne filen paa (sjekk video-forelesning)
        view.saveScoreBt.setOnClickListener {
            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            saveToFile(score as ArrayList<Note>, fileName)
        /*
            if(score.count() > 0 && fileName.isNotEmpty() && path != null) {
                // Add prefix and change name if file already exists
                if(!File(path,"$fileName.music").exists()){
                    fileName = "$fileName.music"
                } else { // if fileName already exist, add System.nanoTime() to end of name
                    fileName = fileName + System.nanoTime() + ".music"
                    Log.d("saveScoreBt", "Filename already exists, saving to $fileName instead: ")
                }

                FileOutputStream(File(path,fileName), true).bufferedWriter().use { writer ->
                    // buffered writer level here
                    score.forEach {
                        writer.write("${it}\n")
                    }
                    writer.close()
                }
            }*/
        }

        return view
    }
    private fun startMusic(){
        musicStart = System.nanoTime()
        isPlaying = true
    }

    public fun saveToFile(notes: ArrayList<Note>, filename: String):Long{//(notes: mutableListOf<note>()){
        var fileName = filename
        val path = this.activity?.getExternalFilesDir(null)

        if(notes.count() > 0 && fileName.isNotEmpty() && path != null) {
            // Add prefix and change name if file already exists
            if(!File(path,"$fileName.music").exists()){
                fileName = "$fileName.music"
            } else { // if fileName already exist, add System.nanoTime() to end of name
                fileName = fileName + System.nanoTime() + ".music"
                Log.d("saveScoreBt", "Filename already exists, saving to $fileName instead: ")
            }

            FileOutputStream(File(path,fileName), true).bufferedWriter().use { writer ->
                // buffered writer level here
                notes.forEach {
                    writer.write("${it}\n")
                }
                writer.close()
            }
        }
        return 99
    }

}