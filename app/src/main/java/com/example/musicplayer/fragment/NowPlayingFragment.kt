package com.example.musicplayer.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.databinding.FragmentNowPlayingBinding
import com.example.musicplayer.utils.setSongPosition

class NowPlayingFragment : Fragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        binding.root.visibility = View.INVISIBLE
        binding.btnPlayStopMusic.setOnClickListener {
            if (MusicPlayerFragment.isPlaying) pauseMusic() else playMusic()
        }
        binding.btnSkipNext.setOnClickListener {
            setSongPosition(true)

            MusicPlayerFragment.musicPlayerService!!.createMediaPlayer()
            when (MusicPlayerFragment.checkList) {
                1 -> {
                    binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
                }
                2 -> {
                    binding.song =
                        MusicPlayerFragment.listFavouriteSong[MusicPlayerFragment.postion]
                }
                3 -> {
                    binding.song =
                        MusicPlayerFragment.listPlaylistSong[MusicPlayerFragment.postion]
                }
                4 -> {
                    binding.song =
                        MusicPlayerFragment.listDevice[MusicPlayerFragment.postion]
                }
            }

            binding.lifecycleOwner = this@NowPlayingFragment
            MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
            playMusic()
        }

        binding.btnSkipPrevious.setOnClickListener {
            setSongPosition(false)

            MusicPlayerFragment.musicPlayerService!!.createMediaPlayer()

            when (MusicPlayerFragment.checkList) {
                1 -> {
                    binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
                }
                2 -> {
                    binding.song =
                        MusicPlayerFragment.listFavouriteSong[MusicPlayerFragment.postion]
                }
                3 -> {
                    binding.song =
                        MusicPlayerFragment.listPlaylistSong[MusicPlayerFragment.postion]
                }
                3 -> {
                    binding.song =
                        MusicPlayerFragment.listDevice[MusicPlayerFragment.postion]
                }
            }

            binding.lifecycleOwner = this@NowPlayingFragment
            MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
            playMusic()
        }

        binding.root.setOnClickListener {
            val intentSong = Intent(requireContext(), MusicPlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("pos", MusicPlayerFragment.postion)
            when (MusicPlayerFragment.checkList) {
                1 -> {
                    val song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
                    bundle.putSerializable("song", song)
                }
                2 -> {
                    val song = MusicPlayerFragment.listFavouriteSong[MusicPlayerFragment.postion]
                    bundle.putSerializable("song", song)
                }
                3 -> {
                    val song = MusicPlayerFragment.listPlaylistSong[MusicPlayerFragment.postion]
                    bundle.putSerializable("song", song)
                }
                4 -> {
                    val song = MusicPlayerFragment.listDevice[MusicPlayerFragment.postion]
                    bundle.putSerializable("song", song)
                }
            }

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
            when (MusicPlayerFragment.checkList) {
                1 -> {
                    binding.song = MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion]
                }
                2 -> {
                    binding.song =
                        MusicPlayerFragment.listFavouriteSong[MusicPlayerFragment.postion]
                }
                3 -> {
                    binding.song =
                        MusicPlayerFragment.listPlaylistSong[MusicPlayerFragment.postion]
                }
                4 -> {
                    binding.song =
                        MusicPlayerFragment.listDevice[MusicPlayerFragment.postion]
                }
            }
            binding.lifecycleOwner = this@NowPlayingFragment

            if (MusicPlayerFragment.isPlaying) binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
            else binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        }
    }

    private fun playMusic() {
        MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.start()
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_pause)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.isPlaying = true
    }

    private fun pauseMusic() {
        MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.pause()
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.musicPlayerService!!.showNotification(R.drawable.ic_play_arrow)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.isPlaying = false
    }
}