package com.example.musicplayer.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.databinding.ActivityTestDatabaseBinding
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.User
import com.example.musicplayer.model.relation.FavouriteSongCrossRef
import com.example.musicplayer.model.relation.SongPlaylistCrossRef
import kotlinx.coroutines.launch

class TestDatabaseActivity : AppCompatActivity() {
    private val TAG: String = "DHP"
    private lateinit var binding: ActivityTestDatabaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //test edit
        val dao = MusicDatabase.getInstance(this).songDao()
        var playlists = listOf<Playlist>(
            Playlist(null, 1, "a", "aa"),
            Playlist(null, 2, "b", "b"),
        )
//        var songs = listOf<Song>(
//            Song(null, "A", "A", "A", "A", "A", "A", "A", "A", 1),
//            Song(null, "B", "B", "A", "A", "A", "A", "A", "A", 2),
//            Song(null, "C", "C", "A", "A", "A", "A", "A", "A", 3),
//        )

        var songPlaylistCrossRef = listOf<SongPlaylistCrossRef>(
            SongPlaylistCrossRef(1, 1),
            SongPlaylistCrossRef(2, 1),
            SongPlaylistCrossRef(3, 1),
            SongPlaylistCrossRef(2, 2),
        )
        var users = listOf<User>(
            User(null, "a", "a"),
            User(null, "b", "b"),
        )
        val favourites = listOf<Favourite>(
            Favourite(1, 1),
            Favourite(2, 2),
        )
        val favouriteSongCrossRef = listOf<FavouriteSongCrossRef>(
            FavouriteSongCrossRef(1, 1),
            FavouriteSongCrossRef(1, 2),
            FavouriteSongCrossRef(1, 3),
            FavouriteSongCrossRef(2, 2),
        )
        binding.add.setOnClickListener() {
            lifecycleScope.launch {
                playlists.forEach { dao.insertPlaylist(it) }
                //songs.forEach { dao.insertSong(it) }
                songPlaylistCrossRef.forEach { dao.insertSongPlaylistCrossRef(it) }
                users.forEach { dao.insertUser(it) }
                favourites.forEach { dao.insertFavourite(it) }
                favouriteSongCrossRef.forEach { dao.insertFavouriteSongCrossRef(it) }
                // userPlaylistCrossRef.forEach{dao.insertUserPlaylistCrossRef(it)}
                /*val l1 = dao.getSongsOfPlaylist(1)
                val l2 = dao.getSongsOfPlaylist(2)
                Log.d("DHP","1: ${l1.toString()} ")
                Log.d("DHP","2: ${l2.toString()} ")*/

            }
        }
        binding.show.setOnClickListener() {
            lifecycleScope.launch {
                val l1 = dao.getUserWithFavouriteAndSongs(1)
                val l2 = dao.getUserWithFavouriteAndSongs(2)
                val l3 = dao.getUserWithPlaylistsAndSongs(1)
                val l4 = dao.getUserWithPlaylistsAndSongs(2)
                Log.d(TAG, "user1: ${l1.toString()} ")
                Log.d(TAG, "user2: ${l2.toString()} ")
                Log.d(TAG, "user1: ${l3.toString()} ")
                Log.d(TAG, "user2: ${l4.toString()} ")
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                //Light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                //Night
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}