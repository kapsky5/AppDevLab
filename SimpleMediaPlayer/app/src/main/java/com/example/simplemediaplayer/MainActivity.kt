package com.example.simplemediaplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var mMusicButton: MaterialButton
    private lateinit var mVideoButton: MaterialButton

    private lateinit var mVideoView: VideoView

    private lateinit var mMusicPlayer: MediaPlayer

    var mPlayingMusic = false
    var mPlayingVideo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMusicPlayer = MediaPlayer.create(applicationContext, R.raw.audio)
        mMusicPlayer.isLooping = false

        mMusicButton = findViewById(R.id.music_button)
        mVideoButton = findViewById(R.id.video_button)

        mVideoView = findViewById(R.id.video_view)
        val path = "android.resource://" + packageName + "/" + R.raw.video
        mVideoView.setVideoURI(Uri.parse(path))

        mMusicButton.setOnClickListener { playMusic() }
        mVideoButton.setOnClickListener { playVideo() }
    }

    private fun playMusic() {
        if (mPlayingVideo) {
            Toast.makeText(
                applicationContext,
                "Stop the video first!",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (mPlayingMusic) {
            // Stop the music
            mMusicPlayer.pause()
            mMusicButton.text = getString(R.string.play_music)
        } else {
            mMusicPlayer.seekTo(0)
            mMusicPlayer.start()
            mMusicButton.text = getString(R.string.stop_music)
        }

        mPlayingMusic = !mPlayingMusic
    }

    private fun playVideo() {
        if (mPlayingMusic) {
            Toast.makeText(
                applicationContext,
                "Stop the music first!",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (mPlayingVideo) {
            mVideoView.pause()
        } else {
            mVideoView.seekTo(0)
            mVideoView.start()
        }

        mPlayingVideo = !mPlayingVideo
        if (mPlayingVideo) {
            mVideoView.visibility = View.VISIBLE
        } else {
            mVideoView.visibility = View.GONE
        }
    }


}