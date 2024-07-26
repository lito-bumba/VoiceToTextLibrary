package com.bumba.library.texttovoice

import android.content.Context
import android.speech.tts.TextToSpeech
import com.bumba.library.interfaces.TextToVoice
import java.util.Locale

internal class TextToVoiceImpl(
    private val context: Context
) : TextToVoice {

    private val tts: TextToSpeech by lazy {
        TextToSpeech(context, null)
    }

    override fun startSpeaking(langCode: String, text: String) {
        tts.language = Locale.getDefault()
        tts.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    override fun stopSpeaking() {
        tts.stop()
    }
}