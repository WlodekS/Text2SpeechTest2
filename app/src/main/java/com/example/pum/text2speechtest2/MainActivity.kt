package com.example.pum.text2speechtest2

/**
 * Created by Wlodek on 2018-02-14.
 */
import java.util.Locale

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    /** Called when the activity is first created.  */
    private var tts: TextToSpeech? = null
    private var btnSpeak: Button? = null
    private var txtText: EditText? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)
        btnSpeak = findViewById(R.id.btnSpeak)
        txtText = findViewById(R.id.txtText)

        // button on click event
        btnSpeak!!.setOnClickListener { speakOut() }

        //Kliknięcie na klawiszu Informacja:
        btnInformacja!!.setOnClickListener { speakInformation()}
    }

    public override fun onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            //val result = tts!!.setLanguage(Locale.US)
            //To ustawia język polski (bo taki jest domyślny)
            val result = tts!!.setLanguage(Locale.getDefault())

            //Zmiana częstotliwości głosu:
            //tts!!.setPitch(0F)  //typowy głos kobiecy. To samo 0.9
            tts!!.setPitch(0.01F)   //głos niby niski - ale słabiutki (trochę kobiecy)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                btnSpeak!!.isEnabled = true
                speakOut()
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {

        val text = txtText!!.text
        tts!!.setPitch(0.01F)   //głos niby niski - ale słabiutki (trochę kobiecy)
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1")
    }

    private fun speakInformation(){
        val text = "Opowiem kawał. Programista do Szefa Programistów:\n" +
                "- Nie jesteśmy w stanie napisać tego programu w zaproponowanym terminie. \n" +
                "Więcej. Nie jesteśmy w stanie napisać go w ogóle. \n" +
                "Wymagało by to bowiem całkowitej zmiany strategii firmy i wejścia w całkiem nową dla nas dziedzinę, \n" +
                "a nikt z naszego zespołu nie ma odpowiedniego zasobu wiedzy z tej dziedziny. \n" +
                "Moim zdaniem nie powinniśmy byli przyjmować tego zlecenia bez zastanowienia.\n" +
                "Mam nadzieję, że to tylko fragment kawału. A nie rzeczywistość!"
        tts!!.setPitch(0.9F)  //typowy głos kobiecy.
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1")

    }

}