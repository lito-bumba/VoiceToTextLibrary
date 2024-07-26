package com.bumba.library.interfaces

internal interface TextToVoice {

    fun startSpeaking(langCode: String, text: String)

    fun stopSpeaking()

}