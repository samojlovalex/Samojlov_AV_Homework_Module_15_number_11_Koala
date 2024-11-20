package com.example.samojlov_av_homework_module_15_number_11_koala

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_15_number_11_koala.databinding.ActivityMainBinding
import com.example.samojlov_av_homework_module_15_number_11_koala.models.Song
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var songDescriptionTV: TextView
    private lateinit var volumeSB: SeekBar
    private lateinit var previsionButtonBT: FloatingActionButton
    private lateinit var progressSB: SeekBar
    private lateinit var nextButtonBT: FloatingActionButton
    private lateinit var pauseButtonBT: FloatingActionButton
    private lateinit var playButtonBT: FloatingActionButton
    private lateinit var stopButtonBT: FloatingActionButton

    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var currentSong = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {

        songDescriptionTV = binding.songDescriptionTV
        volumeSB = binding.volumeSB
        previsionButtonBT = binding.previsionButtonBT
        progressSB = binding.progressSB
        nextButtonBT = binding.nextButtonBT
        pauseButtonBT = binding.pauseButtonBT
        playButtonBT = binding.playButtonBT
        stopButtonBT = binding.stopButtonBT

        songDescriptionTV.setSelected(true)

        playSong(Song.listSongs)

        initControls()

    }

    private fun initControls() {
       try {
           audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
           if (audioManager != null){
               volumeSB.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
               volumeSB.progress = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
           }
           volumeSB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
               override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                   audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0)
               }

               override fun onStartTrackingTouch(seekBar: SeekBar?) {

               }

               override fun onStopTrackingTouch(seekBar: SeekBar?) {

               }

           })
       }catch (e: Exception){
           e.printStackTrace()
       }

    }

    private fun playSong(listSongs: MutableList<Song>) {
        playButtonBT.setOnClickListener {
            playCurrentSong(listSongs)
            mediaPlayer?.setOnCompletionListener {
                nextCurrentSong()
                stopCurrentSong()
                playCurrentSong(listSongs)
                songDescriptionTV.text = Song.listSongs[currentSong].description
            }
            songDescriptionTV.text = Song.listSongs[currentSong].description
        }

        pauseButtonBT.setOnClickListener {
            if (mediaPlayer != null) mediaPlayer?.pause()
        }

        stopButtonBT.setOnClickListener {
            stopCurrentSong()
            songDescriptionTV.text = getString(R.string.songDescriptionTV_Text)
        }

        nextButtonBT.setOnClickListener {
            nextCurrentSong()
            stopCurrentSong()
            playCurrentSong(listSongs)
            songDescriptionTV.text = Song.listSongs[currentSong].description
        }

        previsionButtonBT.setOnClickListener {
            previsionCurrentSong()
            stopCurrentSong()
            playCurrentSong(listSongs)
            songDescriptionTV.text = Song.listSongs[currentSong].description
        }

        progressSB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun previsionCurrentSong() {
        if (currentSong == 0) {
            currentSong = Song.listSongs.size - 1
        } else {
            currentSong--
        }
    }

    private fun nextCurrentSong() {
        if (currentSong == Song.listSongs.size - 1) {
            currentSong = 0
        } else {
            currentSong++
        }
    }

    private fun stopCurrentSong() {
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun playCurrentSong(listSongs: MutableList<Song>) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, listSongs[currentSong].song)
            initializeSeekbar()
        }
        mediaPlayer?.start()
        Toast.makeText(this,listSongs[currentSong].description,Toast.LENGTH_LONG).show()
    }

    @Suppress("DEPRECATION")
    private fun initializeSeekbar() {
        if (mediaPlayer != null) {
            progressSB.max = mediaPlayer!!.duration
        }
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    progressSB.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    progressSB.progress = 0
                }
            }

        }, 0)
    }
}