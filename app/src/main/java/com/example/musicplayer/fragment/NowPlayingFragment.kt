package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.databinding.FragmentNowPlayingBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.setSongPosition

class NowPlayingFragment : Fragment() {
    companion object {
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        binding.root.visibility = View.INVISIBLE
        binding.btnPlayStopMusic.setOnClickListener {
            if (MusicPlayerFragment.isPlaying) pauseMusic() else playMusic()
        }
        binding.btnSkipNext.setOnClickListener {
            setSongPosition(true)

            MusicPlayerFragment.musicPlayerService!!.createMediaPlayer()

            binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
            binding.lifecycleOwner = this@NowPlayingFragment
            MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
            playMusic()
        }

        binding.btnSkipPrevious.setOnClickListener {
            setSongPosition(false)

            MusicPlayerFragment.musicPlayerService!!.createMediaPlayer()

            binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
            binding.lifecycleOwner = this@NowPlayingFragment
            MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
            playMusic()
        }

        binding.root.setOnClickListener {
            val intentSong = Intent(requireContext(), MusicPlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("pos", MusicPlayerFragment.postion)
            val song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
            bundle.putSerializable("song", song)
            bundle.putString("list", "NowPlaying")
            intentSong.putExtras(bundle)
            startActivity(intentSong)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (MusicPlayerFragment.musicPlayerService != null) {
            binding.root.visibility = View.VISIBLE
            binding.tvNameSong.isSelected = true
            binding.tvSinger.isSelected = true
            //dataBinding
            binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
            binding.lifecycleOwner = this@NowPlayingFragment

            if (MusicPlayerFragment.isPlaying) binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
            else binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        }
    }

    fun playMusic() {
        MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.start()
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.isPlaying = true
    }

    fun pauseMusic() {
        MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.pause()
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_play_arrow)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.isPlaying = false
    }
}