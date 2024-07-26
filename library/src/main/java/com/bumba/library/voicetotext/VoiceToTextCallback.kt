package com.bumba.library.voicetotext

interface VoiceToTextCallback {

    fun onResult(result: String)

    fun onSpeaking(powerRatio: Float)

    fun onError(errorCode: Int, errorMessage: String)

}