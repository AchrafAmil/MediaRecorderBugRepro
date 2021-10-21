package com.nabla.experiment.mediarecorderbugrepro

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

// think about granting audio permission manually from device :-)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Hello")
        val targetFile = File(cacheDir, "test_file_${System.currentTimeMillis()}")
        targetFile.createNewFile()
        println("Target file created")
        val recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFile(targetFile)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
            println("preparation done")
            start()
            println("started recording")
        }

        GlobalScope.launch {
            delay(1000)
            recorder.pause()
            println("paused recording")
            //recorder.resume()
            //println("resumed recording")
            recorder.stop()
            println("stopped recording")
            recorder.release()
            println("released recorder")
        }
    }
}
