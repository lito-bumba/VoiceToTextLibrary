package com.bumba.library

import android.content.Context
import com.bumba.library.interfaces.TextToVoice
import com.bumba.library.interfaces.VoiceToText
import com.bumba.library.texttovoice.TextToVoiceImpl
import com.bumba.library.voicetotext.VoiceToTextCallback
import com.bumba.library.voicetotext.VoiceToTextImpl

class VoiceTextKit(private val context: Context) {

    private val textToVoice: TextToVoice by lazy {
        TextToVoiceImpl(context)
    }
    private val voiceToText: VoiceToText by lazy {
        VoiceToTextImpl(context)
    }

    fun startListening(language: String, callback: VoiceToTextCallback) {
        voiceToText.startListening(language, callback)
    }

    fun stopListening() {
        voiceToText.stopListening()
    }

    fun startSpeaking(language: String, text: String) {
        textToVoice.startSpeaking(
            langCode = language,
            text = text
        )
    }

    fun stopSpeaking() {
        textToVoice.stopSpeaking()
    }
}