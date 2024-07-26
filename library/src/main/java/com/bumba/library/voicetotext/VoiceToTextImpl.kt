package com.bumba.library.voicetotext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_CLIENT
import com.bumba.library.interfaces.VoiceToText
import com.bumba.library.util.PermissionRequest

internal class VoiceToTextImpl(
    private val context: Context
) : VoiceToText, RecognitionListener {

    private val recognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(context)
    }
    private var callback: VoiceToTextCallback? = null

    override fun startListening(languageCode: String, callback: VoiceToTextCallback) {
        this.callback = callback

        if (!PermissionRequest.grantAudioPermission(context)){
            callback.onError(
                errorCode = 101,
                errorMessage = "Speech recognition is not allowed"
            )
            return
        }

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            callback.onError(
                errorCode = 0,
                errorMessage = "Speech recognition is not available"
            )
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
    }

    override fun stopListening() {
        recognizer.stopListening()
    }

    override fun onReadyForSpeech(p0: Bundle?) = Unit

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsDb: Float) {
        val powerRatio = rmsDb * (1f / (12f - (-2f)))
        callback?.onSpeaking(powerRatio)
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() = Unit

    override fun onError(code: Int) {
        if (code == ERROR_CLIENT) {
            return
        }
        callback?.onError(
            errorCode = code,
            errorMessage = "Speech recognition is not available"
        )
    }

    override fun onResults(result: Bundle?) {
        result
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text -> callback?.onResult(text) }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}