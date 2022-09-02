package com.example.musicplayer.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.musicplayer.model.Favourite;
import com.example.musicplayer.model.Playlist;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.model.User;
import com.example.musicplayer.model.relation.FavouriteSongCrossRef;
import com.example.musicplayer.model.relation.SongPlaylistCrossRef;

@androidx.room.Database(entities = {com.example.musicplayer.model.User.class, com.example.musicplayer.model.Song.class, com.example.musicplayer.model.Playlist.class, com.example.musicplayer.model.relation.SongPlaylistCrossRef.class, com.example.musicplayer.model.Favourite.class, com.example.musicplayer.model.relation.FavouriteSongCrossRef.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/musicplayer/db/MusicDatabase;", "Landroidx/room/RoomDatabase;", "()V", "songDao", "Lcom/example/musicplayer/db/MusicDao;", "Companion", "app_debug"})
public abstract class MusicDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.musicplayer.db.MusicDatabase.Companion Companion = null;
    private static com.example.musicplayer.db.MusicDatabase instance;
    
    public MusicDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.musicplayer.db.MusicDao songDao();
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/musicplayer/db/MusicDatabase$Companion;", "", "()V", "instance", "Lcom/example/musicplayer/db/MusicDatabase;", "getInstance", "ctx", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.Synchronized()
        public final synchronized com.example.musicplayer.db.MusicDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx) {
            return null;
        }
    }
}