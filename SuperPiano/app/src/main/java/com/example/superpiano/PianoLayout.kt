package com.example.superpiano

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_full_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_half_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import java.io.File
import java.io.FileOutputStream

class PianoLayout : Fragment() {

    // Can only use one onSave function (a new one would overwrite the older one)
    var onSave:((file: Uri) -> Unit)? = null

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!
    private var score = mutableListOf<Note>()
    private val TAG:String = "SuperPiano.PianoLayout"

    private var musicStart:Long = 0
    private var isPlaying:Boolean = false

    private val allTones = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
            "C2", "C2#", "D2", "D2#", "E2", "F2", "F2#", "G2")

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
                    println("Piano key down $it / id = " + view.halfToneKey.id)
                }

                halfTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime() - musicStart
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it / id = " + view.halfToneKey.id)
                }
                ft.add(view.pianoKeys.id, halfTonePianoKey, "note_$orgNoteValue")

            } else {
                fullTonePianoKey.onKeyDown = {
                    if(!isPlaying){
                        startMusic()
                    } else {
                        startPlay = System.nanoTime() - musicStart
                    }
                    println("Piano key down $it / id = " + view.fullToneKey.id)
                }

                fullTonePianoKey.onKeyUp = {
                    val endPlay = System.nanoTime()
                    val note = Note(it, startPlay, endPlay)
                    score.add(note)
                    println("Piano key up $it / id = " + view.fullToneKey.id)
                }
                ft.add(view.pianoKeys.id,fullTonePianoKey,"note_$orgNoteValue")
            }
            }
            ft.commit()

        view.saveScoreBt.setOnClickListener {
            var fileName:String = ""
            if(view.fileNameTextEdit.text.toString()!=""){
                fileName = view.fileNameTextEdit.text.toString()
            }
            if(fileName == null || fileName == ""){
                fileName = "Unknown"
            }

            // Add prefix and change name if file already exists
            val path = this.activity?.getExternalFilesDir(null)
            if(score.count() > 0 && fileName.isNotEmpty() && path != null) {
                // Add prefix and change name if file already exists
                if(!File(path,"$fileName.music").exists()){
                    fileName = "$fileName.music"
                } else { // Change filename to avoid over writing older file
                    fileName = fileName + System.nanoTime() + ".music"
                    Log.d("saveScoreBt", "Filename already exists, changing filename to $fileName instead: ")
                    // ToDo: Consider using Toast or Snackbar to warn user
                }

                saveToFile(score as ArrayList<Note>, fileName)
                // ToDo: To learn a bit about map-reduce, try to fix it's newline problem in the
                //  code below, and use content instead (video lecture at 1.09.45):
                //  val content:String = score.map( it.toString()}.reduce { acc, s -> acc + s + "\n" }

            } else if (path == null) {
                Log.e(TAG, "Failed: path = null and no file was saved")
            } else if (score.count() <= 0) {
                Log.e(TAG, "Failed: score is empty and no file was saved")
            }
        }
        return view
    }

    // Can add path as parameter, but not without changing the unit test saveFileTest()
    fun saveToFile(notes: ArrayList<Note>, filename: String){//(notes: mutableListOf<note>()){
        var fileName = filename
        val path = this.activity?.getExternalFilesDir(null)

        if(path != null) {
            val file = File(path,fileName)
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                score.forEach {
                    writer.write("${it}\n")
                }
                writer.close()
            }
            this.onSave?.invoke(file.toUri())
        } else {
            Log.e(TAG, "Could not get external path")
        }
        // Removes all notes from the score (notes) and makes the app ready for a new song
        score.clear()
        isPlaying = false
    }

    private fun startMusic(){
        musicStart = System.nanoTime()
        isPlaying = true
    }
}