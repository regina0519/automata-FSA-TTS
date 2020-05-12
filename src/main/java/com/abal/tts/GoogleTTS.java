/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.tts;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jimi
 */
public class GoogleTTS {
    public static void tes(){
        try {
            TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();
            SynthesisInput input = SynthesisInput.newBuilder().setText("Hello, World!").build();
            
            // Build the voice request, select the language code ("en-US") and the ssml voice gender
            // ("neutral")
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("en-US")
                            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                            .build();
            
            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
            
            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            
            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();
            
            // Write the response to the output file.
            OutputStream out = new FileOutputStream("output.mp3");
            out.write(audioContents.toByteArray());
            System.out.println("Audio content written to file \"output.mp3\"");
        } catch (IOException ex) {
            Logger.getLogger(GoogleTTS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
