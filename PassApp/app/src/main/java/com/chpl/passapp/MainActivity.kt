package com.chpl.passapp

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pass_prompt.*
import kotlinx.android.synthetic.main.welcome_message.*
import android.content.Intent




class MainActivity : AppCompatActivity() {

    private var EMPTY = "";
    private var ERROR_INPUT_EMPTY = "Please fill all fields";
    private var ERR_WRONG_PASS = "Please fill all fields";
    private var SAVED = "Saved!";
    private var myPreferences = "myPrefs"
    private var MYPASS = "pass";
    private var MYNOTE = "mynote";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        //Jesli jest ustawione hasło, wyswietl prompt
        if (sharedPreferences.getString(MYPASS, EMPTY) != EMPTY) {
            if (sharedPreferences.getString(MYPASS, EMPTY) != EMPTY) {
                setContentView(R.layout.pass_prompt)

                btn_enter.setOnClickListener {
                    //jelsi ktores pole puste wyswietl err
                    if (testpassin.text.toString() == sharedPreferences.getString(MYPASS, EMPTY)) {
                        setContentView(R.layout.welcome_message)
                        val pass = sharedPreferences.getString(MYPASS, EMPTY)
                        val mynote = sharedPreferences.getString(MYNOTE, EMPTY)
                        text1.text = "Notatka: "
                        note_display.text = "" + mynote

                        btn_new.setOnClickListener {
                            val editor = sharedPreferences.edit()
                            editor.clear().apply()
                            val intent = intent
                            overridePendingTransition(0, 0)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            finish()
                            overridePendingTransition(0, 0)
                            startActivity(intent)
                        }

                    } else {
                        Toast.makeText(applicationContext, ERR_WRONG_PASS, Toast.LENGTH_LONG).show()
                    }
                }

            }

        } else {
            //jesli nie ma notatki to wyswietl
            setContentView(R.layout.activity_main)

            btn_submit.setOnClickListener {
                //jelsi ktores pole puste wyswietl err
                if (edit_text_note.text.toString() == EMPTY
                        || edit_text_pass.text.toString() == EMPTY) {
                    Toast.makeText(applicationContext, ERROR_INPUT_EMPTY, Toast.LENGTH_LONG).show()

                } else {
                    //jesli ok przypisz wartosci
                    val editor = sharedPreferences.edit()
                    editor.putString(MYNOTE, edit_text_note.text.toString())
                    editor.putString(MYPASS, edit_text_pass.text.toString())
                    editor.apply()

                    //Clear the text boxes and show Toast message
                    edit_text_note.setText(EMPTY)
                    edit_text_pass.setText(EMPTY)
                    Toast.makeText(applicationContext, SAVED, Toast.LENGTH_LONG).show()
                    setContentView(R.layout.pass_prompt)
                }
            }

        }
    }
}