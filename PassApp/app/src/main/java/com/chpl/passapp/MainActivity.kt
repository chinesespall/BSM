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
import android.util.Log
import javax.crypto.Cipher
import java.security.MessageDigest
import java.util.UUID
import android.provider.SyncStateContract.Helpers.update
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private var EMPTY = ""
    private var ERROR_INPUT_EMPTY = "Please fill all fields"
    private var ERR_WRONG_PASS = "wrong pass"
    private var SAVED = "Saved!"
    private var myPreferences = "myPrefs"
    private var MYPASS = "pass"
    private var MYNOTE = "mynote"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        //val editor1 = sharedPreferences.edit()
        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(bytes)
        Log.d("ADebugTag", "Ided: " + ided)
        Log.d("ADebugTag", "Hash: " + digest)
        Log.d("ADebugTag", "Hash: " + digest)

        //editor1.putString(MYPASS, shash)
        //editor1.apply()

        //Jesli jest ustawione hasło, wyswietl prompt
        if (sharedPreferences.contains(MYPASS)) {
            setContentView(R.layout.pass_prompt)
            btn_enter.setOnClickListener {
                if (testpassin.text.toString() == sharedPreferences.getString(MYPASS, null)) {
                    setContentView(R.layout.welcome_message)
                    val pass = sharedPreferences.getString(MYPASS, null)
                    val mynote = sharedPreferences.getString(MYNOTE, null)
                    val passwordChar = pass.toCharArray() //Turn password into char[] array
                    val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256) //1324 iterations
                    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).getEncoded()
                    val keySpec = SecretKeySpec(keyBytes, "AES")
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
        else {
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
                    val toencrypt =
                    Log.d("ADebugTag", "Value: " + edit_text_note.text.toString())
                    editor.putString(MYNOTE, edit_text_note.text.toString())
                    editor.putString(MYPASS, edit_text_pass.text.toString())
                    editor.apply()
                    val intent = intent
                    overridePendingTransition(0, 0)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(intent)

                    //Clear the text boxes and show Toast message

                }
            }
        }
    }
}