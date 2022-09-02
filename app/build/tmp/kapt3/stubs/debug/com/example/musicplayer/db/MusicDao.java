package com.example.musicplayer.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.musicplayer.model.Favourite;
import com.example.musicplayer.model.Playlist;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.model.User;
import com.example.musicplayer.model.relation.*;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u001f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0019\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0019\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0019\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020$H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J\u0019\u0010&\u001a\u00020\u00032\u0006\u0010\'\u001a\u00020(H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010)J\u0019\u0010*\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"Lcom/example/musicplayer/db/MusicDao;", "", "deleteSong", "", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSongs", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/musicplayer/model/Song;", "getSongsOfFavourite", "Lcom/example/musicplayer/model/relation/FavouriteWithSongs;", "getSongsOfPlaylist", "Lcom/example/musicplayer/model/relation/PlaylistWithSongs;", "getUserWithFavouriteAndSongs", "Lcom/example/musicplayer/model/relation/UserWithFavouriteAndSongs;", "getUserWithPlaylistsAndSongs", "Lcom/example/musicplayer/model/relation/UserWithPlaylistsAndSongs;", "insertFavourite", "favourite", "Lcom/example/musicplayer/model/Favourite;", "(Lcom/example/musicplayer/model/Favourite;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertFavouriteSongCrossRef", "favouriteSongCrossRef", "Lcom/example/musicplayer/model/relation/FavouriteSongCrossRef;", "(Lcom/example/musicplayer/model/relation/FavouriteSongCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPlaylist", "playlist", "Lcom/example/musicplayer/model/Playlist;", "(Lcom/example/musicplayer/model/Playlist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSong", "song", "(Lcom/example/musicplayer/model/Song;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSongPlaylistCrossRef", "songPlaylistCrossRef", "Lcom/example/musicplayer/model/relation/SongPlaylistCrossRef;", "(Lcom/example/musicplayer/model/relation/SongPlaylistCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertUser", "user", "Lcom/example/musicplayer/model/User;", "(Lcom/example/musicplayer/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSong", "app_debug"})
public abstract interface MusicDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertSong(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertPlaylist(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.Playlist playlist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertUser(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertFavourite(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.Favourite favourite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertSongPlaylistCrossRef(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.relation.SongPlaylistCrossRef songPlaylistCrossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insertFavouriteSongCrossRef(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.relation.FavouriteSongCrossRef favouriteSongCrossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Update()
    public abstract java.lang.Object updateSong(@org.jetbrains.annotations.NotNull()
    com.example.musicplayer.model.Song song, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "DELETE FROM tb_song WHERE idSong=:id")
    public abstract java.lang.Object deleteSong(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM tb_song")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.musicplayer.model.Song>> getAllSongs();
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM tb_playlist WHERE idPlaylist=:id")
    @androidx.room.Transaction()
    public abstract java.lang.Object getSongsOfPlaylist(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.musicplayer.model.relation.PlaylistWithSongs>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM tb_user WHERE idUser=:id")
    @androidx.room.Transaction()
    public abstract java.lang.Object getUserWithPlaylistsAndSongs(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.musicplayer.model.relation.UserWithPlaylistsAndSongs>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM tb_favourite WHERE idFavourite=:id")
    @androidx.room.Transaction()
    public abstract java.lang.Object getSongsOfFavourite(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.musicplayer.model.relation.FavouriteWithSongs>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM tb_user WHERE idUser=:id")
    @androidx.room.Transaction()
    public abstract java.lang.Object getUserWithFavouriteAndSongs(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.musicplayer.model.relation.UserWithFavouriteAndSongs>> continuation);
}