package com.example.superpiano

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.superpiano.data.Note
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class PianoLayoutTest {

    @Test
    fun saveFileTest(){
        val note1:Note = Note("C", 1, 2)
        val note2:Note = Note("A#", 2, 3)
        val note3:Note = Note("H", 3, 4)
        val notes = ArrayList<Note>()
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        val pianoLayout = PianoLayout()
        pianoLayout.saveToFile(notes, "MyFile")
    }

}