package com.example.musicplayer.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri.parse
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.service.MusicPlayerService
import com.example.musicplayer.utils.*
import com.example.musicplayer.vm.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


class MusicPlayerFragment : Fragment(), ServiceConnection, MediaPlayer.OnCompletionListener {
    companion object {
        const val LOG: String = "TCR"
        var musicPlayerService: MusicPlayerService? = null
        var isPlaying: Boolean = false

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentMusicPlayerBinding
        lateinit var connectivityObserver: ConnectivityObserver
        lateinit var animation: Animation

        //List Rank
        var listRankSong: ArrayList<Song> = arrayListOf()
        var getID: Int = 0
        var postion: Int = 0

        var repeat: Boolean = false

        var minUser: Boolean = false
        var min15: Boolean = false
        var min30: Boolean = false
        var min60: Boolean = false

        var nowPlayingId: Int = 0
    }

    private val viewModel: MusicPlayerViewModel by viewModels()

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private val songViewModel: SongViewModel by viewModels()
    private val playlistViewModel: PlaylistViewModel by viewModels()
    private val downloadViewModel: DownloadViewModel by viewModels()
    private var onCompleted: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            downloadViewModel.stopDownloadService()
            val url = intent.getStringExtra("downloaded")
            Log.d(Contanst.TAG, "downloaded1: ${url.toString()}")
            //downloadViewModel.updateUrlSong(url!!)
            if (url != null) {
                downloadViewModel.updateUrlSong(url)
            }
            //songViewModel.updateLocalSongs()
            Toast.makeText(context, "Download success", Toast.LENGTH_LONG).show()
            //unregister()
        }
    }

    private fun unregister() {
        requireActivity().unregisterReceiver(onCompleted)
    }

    private var getID: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)

        val extras = activity?.intent?.extras

        //initial check internet
        connectivityObserver = NetworkConnectivityObserver(requireContext())

        when (extras!!.getString("list")) {
            "listRankSong" -> {
                //Start service
                val intent = Intent(requireContext(), MusicPlayerService::class.java)
                activity?.bindService(intent, this, BIND_AUTO_CREATE)
                activity?.startService(intent)

                getID = extras!!.getInt("idSong", 1003)

                Log.d(LOG, getID.toString())

                listRankSong.addAll(RankFragment.listRankSong)
                Log.d(LOG, "Rank $listRankSong")

                postion = extras.getInt("pos", 0)
                Log.d(LOG, "Pos rank $postion")
                //Play music
                controlMusic(getID)
            }
            "listRankSong1" -> {
                //Start service
                val intent = Intent(requireContext(), MusicPlayerService::class.java)
                activity?.bindService(intent, this, BIND_AUTO_CREATE)
                activity?.startService(intent)

                getID = extras!!.getInt("idSong", 1003)

                Log.d(LOG, getID.toString())


                //Play music
                controlMusic(getID)
            }
            "NowPlaying" -> {
                val getSong = extras.getSerializable("song") as Song
                setLayout(getSong)
                binding.timeReal.text =
                    formatSongDuration(musicPlayerService!!.mediaPlayer!!.currentPosition.toLong())
                binding.timeTotal.text =
                    formatSongDuration(musicPlayerService!!.mediaPlayer!!.duration.toLong())
                binding.seekbarTime.progress = musicPlayerService!!.mediaPlayer!!.currentPosition
                binding.seekbarTime.max = musicPlayerService!!.mediaPlayer!!.duration
                if (isPlaying) binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
                else binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
            }
        }


        //Control
        binding.btnPlayStopMusic.setOnClickListener {
            if (isPlaying) {
                pauseMusic()
                binding.musicDisc.clearAnimation()
            } else {
                binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
                playMusic()
                binding.musicDisc.startAnimation(animation)
            }

        }

        binding.btnSkipPrevious.setOnClickListener {
            prevNextSong(false)
        }

        binding.btnSkipNext.setOnClickListener {
            prevNextSong(true)
        }

        binding.btnRepeat.setOnClickListener {
            if (!repeat) {
                repeat = true
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat_one)
            } else {
                repeat = false
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
            }
        }

        binding.btnTimer.setOnClickListener {
            val timer = minUser || min15 || min30 || min60
            if (!timer) {
                showBottomDialogTimer()
            } else {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle("Stop timer")
                    .setMessage("Do you want to stop timer?")
                    .setPositiveButton("Yes") { _, _ ->
                        minUser = false
                        min15 = false
                        min30 = false
                        min60 = false
                        binding.btnTimer.setColorFilter(R.attr.text_color)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                val customDialog = builder.create()
                customDialog.show()
                customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
                customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
            }
        }

        binding.btnFavourite.setOnClickListener {
            binding.btnFavourite.setImageResource(R.drawable.ic_favorite)
            val song = listRankSong[postion]
            favouriteViewModel.insertFavourite(song)
        }

        binding.btnDownload.setOnClickListener() {
            LocalBroadcastManager.getInstance(requireActivity())
                .registerReceiver(onCompleted, IntentFilter("Download"))
            downloadViewModel.startDownloadService(listRankSong[postion])
        }

        //Seekbar
        binding.seekbarTime.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) musicPlayerService!!.mediaPlayer!!.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            }
        )



        Log.d(LOG, "CreateView")

        return binding.root
    }


    private fun setLayout(song: Song) {
        //Databinding
        binding.song = song
        binding.lifecycleOwner = this@MusicPlayerFragment

        //Animation disk
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_rotate)

        animation.repeatCount = Animation.INFINITE
        //smooth animation rotate
        animation.interpolator = LinearInterpolator()
        binding.musicDisc.startAnimation(animation)

        //repeat music
        if (repeat) {
            repeat = true
            binding.btnRepeat.setImageResource(R.drawable.ic_repeat_one)
        } else {
            repeat = false
            binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
        }

        //Timer color
        if (minUser || min15 || min30 || min60) binding.btnTimer.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.timer
            )
        )
    }


    ///initialayout
    @SuppressLint("SetTextI18n")
    private fun controlMusic(id: Int) {
        lifecycleScope.launch {
            connectivityObserver.observer().collect {
                when (it) {
                    ConnectivityObserver.StatusInternet.AVAILABLE -> {
                        viewModel.songById(id).observe(viewLifecycleOwner) { song ->
                            setLayout(song)
                            song.urlSong?.let { url -> createMediaPlayer(url) }

                        }
                    }
                    ConnectivityObserver.StatusInternet.LOSING -> {
                        binding.tvNameSong.text = "$it"
                    }
                    ConnectivityObserver.StatusInternet.LOST -> {
                        binding.tvNameSong.text = "$it"
                    }
                    ConnectivityObserver.StatusInternet.UNAVAILABLE -> {
                        binding.tvNameSong.text = "$it"
                    }
                }
            }
        }
    }

    private fun createMediaPlayer(link: String) {
        try {
            if (musicPlayerService?.mediaPlayer == null) musicPlayerService?.mediaPlayer =
                MediaPlayer()
            musicPlayerService?.mediaPlayer?.reset()
            musicPlayerService?.mediaPlayer?.setDataSource(parse(link).toString())
            musicPlayerService?.mediaPlayer?.prepare()
            musicPlayerService?.mediaPlayer?.start()

            isPlaying = true
            binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
            //seekbar
            binding.timeReal.text = formatSongDuration(
                musicPlayerService?.mediaPlayer?.currentPosition.toString().toLong()
            )
            binding.timeTotal.text =
                formatSongDuration(musicPlayerService?.mediaPlayer?.duration.toString().toLong())
            binding.seekbarTime.progress = 0
            binding.seekbarTime.max = musicPlayerService!!.mediaPlayer!!.duration

            musicPlayerService?.mediaPlayer?.setOnCompletionListener(this)

            nowPlayingId = listRankSong[postion].idSong!!.toInt()
            Log.d(LOG, musicPlayerService?.mediaPlayer?.duration.toString())
            Log.d(LOG, "ID: ${musicPlayerService?.mediaPlayer!!.audioSessionId}")
        } catch (ex: Exception) {

        }
    }

    private fun playMusic() {
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)

        musicPlayerService!!.showNotification(
            R.drawable.ic_pause
        )

        isPlaying = true
        musicPlayerService?.mediaPlayer?.start()
    }

    private fun pauseMusic() {
        binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)

        musicPlayerService!!.showNotification(
            R.drawable.ic_play_arrow
        )

        isPlaying = false
        musicPlayerService?.mediaPlayer?.pause()
    }

    private fun prevNextSong(increment: Boolean) {
        if (increment) {
            setSongPosition(increment)
            val idSong = listRankSong[postion].idSong.toString().toInt()
            controlMusic(idSong)
        } else {
            setSongPosition(increment)
            val idSong = listRankSong[postion].idSong.toString().toInt()
            controlMusic(idSong)
        }
    }

    private fun showBottomDialogTimer() {
        val dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(R.layout.bottom_sheet_timer_dialog)
        dialog.show()
        dialog.findViewById<AppCompatButton>(R.id.btn_minutes)?.setOnClickListener {
            val edtMinutes = dialog.findViewById<EditText>(R.id.edt_minutes)
            val minutes = edtMinutes?.text.toString().toLong()
            minUser = true
            Thread {
                Thread.sleep(minutes * 60000)
                if (min15) exitProcess(1)
                exitApp()
            }.start()

            binding.btnTimer.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.timer
                )
            )

            dialog.dismiss()
        }
        dialog.findViewById<ConstraintLayout>(R.id.btn_15_minutes)?.setOnClickListener {

            min15 = true
            Thread {
                Thread.sleep(15 * 60000)
                if (min15) exitProcess(1)
                exitApp()
            }.start()

            binding.btnTimer.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.timer
                )
            )

            dialog.dismiss()
        }
        dialog.findViewById<ConstraintLayout>(R.id.btn_30_minutes)?.setOnClickListener {
            min30 = true
            Thread {
                Thread.sleep(30 * 60000)
                if (min30) exitProcess(1)
                exitApp()
            }.start()

            binding.btnTimer.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.timer
                )
            )

            dialog.dismiss()
        }
        dialog.findViewById<ConstraintLayout>(R.id.btn_60_minutes)?.setOnClickListener {
            min60 = true
            Thread {
                Thread.sleep(60 * 60000)
                if (min60) exitProcess(1)
                exitApp()
            }.start()

            binding.btnTimer.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.timer
                )
            )

            dialog.dismiss()
        }
    }

    override fun onServiceConnected(componentName: ComponentName?, service: IBinder?) {
        val binder = service as MusicPlayerService.MyBinder
        musicPlayerService = binder.currentService()

        musicPlayerService!!.showNotification(
            R.drawable.ic_pause
        )

        val idSong = listRankSong[postion].idSong.toString().toInt()
        controlMusic(idSong)
        musicPlayerService?.seekBarSetup()
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {
        musicPlayerService = null
    }

    //MediaPlayer.OnCompletionListener
    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        setSongPosition(true)
        createMediaPlayer(listRankSong[postion].urlSong.toString())
        setLayout(listRankSong[postion])

    }
}