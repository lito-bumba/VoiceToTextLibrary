package com.bumba.library.interfaces

import com.bumba.library.voicetotext.VoiceToTextCallback

internal interface VoiceToText {

    fun startListening(languageCode: String, callback: VoiceToTextCallback)

    fun stopListening()

}