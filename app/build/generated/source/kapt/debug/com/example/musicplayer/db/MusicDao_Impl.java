package com.example.musicplayer.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.musicplayer.model.Favourite;
import com.example.musicplayer.model.Playlist;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.model.User;
import com.example.musicplayer.model.relation.FavouriteSongCrossRef;
import com.example.musicplayer.model.relation.FavouriteWithSongs;
import com.example.musicplayer.model.relation.PlaylistWithSongs;
import com.example.musicplayer.model.relation.SongPlaylistCrossRef;
import com.example.musicplayer.model.relation.UserWithFavouriteAndSongs;
import com.example.musicplayer.model.relation.UserWithPlaylistsAndSongs;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MusicDao_Impl implements MusicDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Song> __insertionAdapterOfSong;

  private final EntityInsertionAdapter<Playlist> __insertionAdapterOfPlaylist;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<Favourite> __insertionAdapterOfFavourite;

  private final EntityInsertionAdapter<SongPlaylistCrossRef> __insertionAdapterOfSongPlaylistCrossRef;

  private final EntityInsertionAdapter<FavouriteSongCrossRef> __insertionAdapterOfFavouriteSongCrossRef;

  private final EntityDeletionOrUpdateAdapter<Song> __updateAdapterOfSong;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSong;

  public MusicDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSong = new EntityInsertionAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_song` (`idSong`,`titleName`,`urlImage`,`urlSong`,`category`,`artist`,`singer`,`album`,`duration`,`views`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        if (value.getIdSong() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getIdSong());
        }
        if (value.getTitleName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitleName());
        }
        if (value.getUrlImage() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrlImage());
        }
        if (value.getUrlSong() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUrlSong());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory());
        }
        if (value.getArtist() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getArtist());
        }
        if (value.getSinger() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSinger());
        }
        if (value.getAlbum() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAlbum());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDuration());
        }
        stmt.bindLong(10, value.getViews());
      }
    };
    this.__insertionAdapterOfPlaylist = new EntityInsertionAdapter<Playlist>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_playlist` (`idPlaylist`,`idUserCreator`,`name`,`dateTimeCreate`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Playlist value) {
        if (value.getIdPlaylist() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getIdPlaylist());
        }
        stmt.bindLong(2, value.getIdUserCreator());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDateTimeCreate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDateTimeCreate());
        }
      }
    };
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_user` (`idUser`,`username`,`password`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getIdUser() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getIdUser());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPassword());
        }
      }
    };
    this.__insertionAdapterOfFavourite = new EntityInsertionAdapter<Favourite>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tb_favourite` (`idFavourite`,`idUser`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favourite value) {
        stmt.bindLong(1, value.getIdFavourite());
        stmt.bindLong(2, value.getIdUser());
      }
    };
    this.__insertionAdapterOfSongPlaylistCrossRef = new EntityInsertionAdapter<SongPlaylistCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `SongPlaylistCrossRef` (`idSong`,`idPlaylist`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SongPlaylistCrossRef value) {
        stmt.bindLong(1, value.getIdSong());
        stmt.bindLong(2, value.getIdPlaylist());
      }
    };
    this.__insertionAdapterOfFavouriteSongCrossRef = new EntityInsertionAdapter<FavouriteSongCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `FavouriteSongCrossRef` (`idFavourite`,`idSong`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavouriteSongCrossRef value) {
        stmt.bindLong(1, value.getIdFavourite());
        stmt.bindLong(2, value.getIdSong());
      }
    };
    this.__updateAdapterOfSong = new EntityDeletionOrUpdateAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `tb_song` SET `idSong` = ?,`titleName` = ?,`urlImage` = ?,`urlSong` = ?,`category` = ?,`artist` = ?,`singer` = ?,`album` = ?,`duration` = ?,`views` = ? WHERE `idSong` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        if (value.getIdSong() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getIdSong());
        }
        if (value.getTitleName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitleName());
        }
        if (value.getUrlImage() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrlImage());
        }
        if (value.getUrlSong() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUrlSong());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory());
        }
        if (value.getArtist() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getArtist());
        }
        if (value.getSinger() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSinger());
        }
        if (value.getAlbum() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAlbum());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDuration());
        }
        stmt.bindLong(10, value.getViews());
        if (value.getIdSong() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.getIdSong());
        }
      }
    };
    this.__preparedStmtOfDeleteSong = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_song WHERE idSong=?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSong(final Song song, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSong.insert(song);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertPlaylist(final Playlist playlist,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaylist.insert(playlist);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertFavourite(final Favourite favourite,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavourite.insert(favourite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertSongPlaylistCrossRef(final SongPlaylistCrossRef songPlaylistCrossRef,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSongPlaylistCrossRef.insert(songPlaylistCrossRef);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertFavouriteSongCrossRef(final FavouriteSongCrossRef favouriteSongCrossRef,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavouriteSongCrossRef.insert(favouriteSongCrossRef);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateSong(final Song song, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSong.handle(song);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSong(final int id, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSong.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSong.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Song>> getAllSongs() {
    final String _sql = "SELECT * FROM tb_song";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"tb_song"}, false, new Callable<List<Song>>() {
      @Override
      public List<Song> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdSong = CursorUtil.getColumnIndexOrThrow(_cursor, "idSong");
          final int _cursorIndexOfTitleName = CursorUtil.getColumnIndexOrThrow(_cursor, "titleName");
          final int _cursorIndexOfUrlImage = CursorUtil.getColumnIndexOrThrow(_cursor, "urlImage");
          final int _cursorIndexOfUrlSong = CursorUtil.getColumnIndexOrThrow(_cursor, "urlSong");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
          final int _cursorIndexOfSinger = CursorUtil.getColumnIndexOrThrow(_cursor, "singer");
          final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViews = CursorUtil.getColumnIndexOrThrow(_cursor, "views");
          final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Song _item;
            final Integer _tmpIdSong;
            if (_cursor.isNull(_cursorIndexOfIdSong)) {
              _tmpIdSong = null;
            } else {
              _tmpIdSong = _cursor.getInt(_cursorIndexOfIdSong);
            }
            final String _tmpTitleName;
            if (_cursor.isNull(_cursorIndexOfTitleName)) {
              _tmpTitleName = null;
            } else {
              _tmpTitleName = _cursor.getString(_cursorIndexOfTitleName);
            }
            final String _tmpUrlImage;
            if (_cursor.isNull(_cursorIndexOfUrlImage)) {
              _tmpUrlImage = null;
            } else {
              _tmpUrlImage = _cursor.getString(_cursorIndexOfUrlImage);
            }
            final String _tmpUrlSong;
            if (_cursor.isNull(_cursorIndexOfUrlSong)) {
              _tmpUrlSong = null;
            } else {
              _tmpUrlSong = _cursor.getString(_cursorIndexOfUrlSong);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpArtist;
            if (_cursor.isNull(_cursorIndexOfArtist)) {
              _tmpArtist = null;
            } else {
              _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
            }
            final String _tmpSinger;
            if (_cursor.isNull(_cursorIndexOfSinger)) {
              _tmpSinger = null;
            } else {
              _tmpSinger = _cursor.getString(_cursorIndexOfSinger);
            }
            final String _tmpAlbum;
            if (_cursor.isNull(_cursorIndexOfAlbum)) {
              _tmpAlbum = null;
            } else {
              _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final int _tmpViews;
            _tmpViews = _cursor.getInt(_cursorIndexOfViews);
            _item = new Song(_tmpIdSong,_tmpTitleName,_tmpUrlImage,_tmpUrlSong,_tmpCategory,_tmpArtist,_tmpSinger,_tmpAlbum,_tmpDuration,_tmpViews);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSongsOfPlaylist(final int id,
      final Continuation<? super List<PlaylistWithSongs>> continuation) {
    final String _sql = "SELECT * FROM tb_playlist WHERE idPlaylist=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<List<PlaylistWithSongs>>() {
      @Override
      public List<PlaylistWithSongs> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfIdPlaylist = CursorUtil.getColumnIndexOrThrow(_cursor, "idPlaylist");
            final int _cursorIndexOfIdUserCreator = CursorUtil.getColumnIndexOrThrow(_cursor, "idUserCreator");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfDateTimeCreate = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTimeCreate");
            final LongSparseArray<ArrayList<Song>> _collectionSongs = new LongSparseArray<ArrayList<Song>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfIdPlaylist)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfIdPlaylist);
                ArrayList<Song> _tmpSongsCollection = _collectionSongs.get(_tmpKey);
                if (_tmpSongsCollection == null) {
                  _tmpSongsCollection = new ArrayList<Song>();
                  _collectionSongs.put(_tmpKey, _tmpSongsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptbSongAscomExampleMusicplayerModelSong(_collectionSongs);
            final List<PlaylistWithSongs> _result = new ArrayList<PlaylistWithSongs>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final PlaylistWithSongs _item;
              final Playlist _tmpPlaylist;
              if (! (_cursor.isNull(_cursorIndexOfIdPlaylist) && _cursor.isNull(_cursorIndexOfIdUserCreator) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfDateTimeCreate))) {
                final Integer _tmpIdPlaylist;
                if (_cursor.isNull(_cursorIndexOfIdPlaylist)) {
                  _tmpIdPlaylist = null;
                } else {
                  _tmpIdPlaylist = _cursor.getInt(_cursorIndexOfIdPlaylist);
                }
                final int _tmpIdUserCreator;
                _tmpIdUserCreator = _cursor.getInt(_cursorIndexOfIdUserCreator);
                final String _tmpName;
                if (_cursor.isNull(_cursorIndexOfName)) {
                  _tmpName = null;
                } else {
                  _tmpName = _cursor.getString(_cursorIndexOfName);
                }
                final String _tmpDateTimeCreate;
                if (_cursor.isNull(_cursorIndexOfDateTimeCreate)) {
                  _tmpDateTimeCreate = null;
                } else {
                  _tmpDateTimeCreate = _cursor.getString(_cursorIndexOfDateTimeCreate);
                }
                _tmpPlaylist = new Playlist(_tmpIdPlaylist,_tmpIdUserCreator,_tmpName,_tmpDateTimeCreate);
              }  else  {
                _tmpPlaylist = null;
              }
              ArrayList<Song> _tmpSongsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfIdPlaylist)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfIdPlaylist);
                _tmpSongsCollection_1 = _collectionSongs.get(_tmpKey_1);
              }
              if (_tmpSongsCollection_1 == null) {
                _tmpSongsCollection_1 = new ArrayList<Song>();
              }
              _item = new PlaylistWithSongs(_tmpPlaylist,_tmpSongsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getUserWithPlaylistsAndSongs(final int id,
      final Continuation<? super List<UserWithPlaylistsAndSongs>> continuation) {
    final String _sql = "SELECT * FROM tb_user WHERE idUser=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<List<UserWithPlaylistsAndSongs>>() {
      @Override
      public List<UserWithPlaylistsAndSongs> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "idUser");
            final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
            final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
            final LongSparseArray<ArrayList<PlaylistWithSongs>> _collectionPlaylists = new LongSparseArray<ArrayList<PlaylistWithSongs>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfIdUser)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfIdUser);
                ArrayList<PlaylistWithSongs> _tmpPlaylistsCollection = _collectionPlaylists.get(_tmpKey);
                if (_tmpPlaylistsCollection == null) {
                  _tmpPlaylistsCollection = new ArrayList<PlaylistWithSongs>();
                  _collectionPlaylists.put(_tmpKey, _tmpPlaylistsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptbPlaylistAscomExampleMusicplayerModelRelationPlaylistWithSongs(_collectionPlaylists);
            final List<UserWithPlaylistsAndSongs> _result = new ArrayList<UserWithPlaylistsAndSongs>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final UserWithPlaylistsAndSongs _item;
              final User _tmpUser;
              if (! (_cursor.isNull(_cursorIndexOfIdUser) && _cursor.isNull(_cursorIndexOfUsername) && _cursor.isNull(_cursorIndexOfPassword))) {
                final Integer _tmpIdUser;
                if (_cursor.isNull(_cursorIndexOfIdUser)) {
                  _tmpIdUser = null;
                } else {
                  _tmpIdUser = _cursor.getInt(_cursorIndexOfIdUser);
                }
                final String _tmpUsername;
                if (_cursor.isNull(_cursorIndexOfUsername)) {
                  _tmpUsername = null;
                } else {
                  _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
                }
                final String _tmpPassword;
                if (_cursor.isNull(_cursorIndexOfPassword)) {
                  _tmpPassword = null;
                } else {
                  _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
                }
                _tmpUser = new User(_tmpIdUser,_tmpUsername,_tmpPassword);
              }  else  {
                _tmpUser = null;
              }
              ArrayList<PlaylistWithSongs> _tmpPlaylistsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfIdUser)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfIdUser);
                _tmpPlaylistsCollection_1 = _collectionPlaylists.get(_tmpKey_1);
              }
              if (_tmpPlaylistsCollection_1 == null) {
                _tmpPlaylistsCollection_1 = new ArrayList<PlaylistWithSongs>();
              }
              _item = new UserWithPlaylistsAndSongs(_tmpUser,_tmpPlaylistsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSongsOfFavourite(final int id,
      final Continuation<? super List<FavouriteWithSongs>> continuation) {
    final String _sql = "SELECT * FROM tb_favourite WHERE idFavourite=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<List<FavouriteWithSongs>>() {
      @Override
      public List<FavouriteWithSongs> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfIdFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "idFavourite");
            final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "idUser");
            final LongSparseArray<ArrayList<Song>> _collectionSongs = new LongSparseArray<ArrayList<Song>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey = _cursor.getLong(_cursorIndexOfIdFavourite);
              ArrayList<Song> _tmpSongsCollection = _collectionSongs.get(_tmpKey);
              if (_tmpSongsCollection == null) {
                _tmpSongsCollection = new ArrayList<Song>();
                _collectionSongs.put(_tmpKey, _tmpSongsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptbSongAscomExampleMusicplayerModelSong_1(_collectionSongs);
            final List<FavouriteWithSongs> _result = new ArrayList<FavouriteWithSongs>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final FavouriteWithSongs _item;
              final Favourite _tmpFavourite;
              if (! (_cursor.isNull(_cursorIndexOfIdFavourite) && _cursor.isNull(_cursorIndexOfIdUser))) {
                final int _tmpIdFavourite;
                _tmpIdFavourite = _cursor.getInt(_cursorIndexOfIdFavourite);
                final int _tmpIdUser;
                _tmpIdUser = _cursor.getInt(_cursorIndexOfIdUser);
                _tmpFavourite = new Favourite(_tmpIdFavourite,_tmpIdUser);
              }  else  {
                _tmpFavourite = null;
              }
              ArrayList<Song> _tmpSongsCollection_1 = null;
              final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfIdFavourite);
              _tmpSongsCollection_1 = _collectionSongs.get(_tmpKey_1);
              if (_tmpSongsCollection_1 == null) {
                _tmpSongsCollection_1 = new ArrayList<Song>();
              }
              _item = new FavouriteWithSongs(_tmpFavourite,_tmpSongsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getUserWithFavouriteAndSongs(final int id,
      final Continuation<? super List<UserWithFavouriteAndSongs>> continuation) {
    final String _sql = "SELECT * FROM tb_user WHERE idUser=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<List<UserWithFavouriteAndSongs>>() {
      @Override
      public List<UserWithFavouriteAndSongs> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "idUser");
            final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
            final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
            final LongSparseArray<ArrayList<FavouriteWithSongs>> _collectionFavourites = new LongSparseArray<ArrayList<FavouriteWithSongs>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfIdUser)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfIdUser);
                ArrayList<FavouriteWithSongs> _tmpFavouritesCollection = _collectionFavourites.get(_tmpKey);
                if (_tmpFavouritesCollection == null) {
                  _tmpFavouritesCollection = new ArrayList<FavouriteWithSongs>();
                  _collectionFavourites.put(_tmpKey, _tmpFavouritesCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptbFavouriteAscomExampleMusicplayerModelRelationFavouriteWithSongs(_collectionFavourites);
            final List<UserWithFavouriteAndSongs> _result = new ArrayList<UserWithFavouriteAndSongs>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final UserWithFavouriteAndSongs _item;
              final User _tmpUser;
              if (! (_cursor.isNull(_cursorIndexOfIdUser) && _cursor.isNull(_cursorIndexOfUsername) && _cursor.isNull(_cursorIndexOfPassword))) {
                final Integer _tmpIdUser;
                if (_cursor.isNull(_cursorIndexOfIdUser)) {
                  _tmpIdUser = null;
                } else {
                  _tmpIdUser = _cursor.getInt(_cursorIndexOfIdUser);
                }
                final String _tmpUsername;
                if (_cursor.isNull(_cursorIndexOfUsername)) {
                  _tmpUsername = null;
                } else {
                  _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
                }
                final String _tmpPassword;
                if (_cursor.isNull(_cursorIndexOfPassword)) {
                  _tmpPassword = null;
                } else {
                  _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
                }
                _tmpUser = new User(_tmpIdUser,_tmpUsername,_tmpPassword);
              }  else  {
                _tmpUser = null;
              }
              ArrayList<FavouriteWithSongs> _tmpFavouritesCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfIdUser)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfIdUser);
                _tmpFavouritesCollection_1 = _collectionFavourites.get(_tmpKey_1);
              }
              if (_tmpFavouritesCollection_1 == null) {
                _tmpFavouritesCollection_1 = new ArrayList<FavouriteWithSongs>();
              }
              _item = new UserWithFavouriteAndSongs(_tmpUser,_tmpFavouritesCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiptbSongAscomExampleMusicplayerModelSong(
      final LongSparseArray<ArrayList<Song>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Song>> _tmpInnerMap = new LongSparseArray<ArrayList<Song>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbSongAscomExampleMusicplayerModelSong(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Song>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbSongAscomExampleMusicplayerModelSong(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `tb_song`.`idSong` AS `idSong`,`tb_song`.`titleName` AS `titleName`,`tb_song`.`urlImage` AS `urlImage`,`tb_song`.`urlSong` AS `urlSong`,`tb_song`.`category` AS `category`,`tb_song`.`artist` AS `artist`,`tb_song`.`singer` AS `singer`,`tb_song`.`album` AS `album`,`tb_song`.`duration` AS `duration`,`tb_song`.`views` AS `views`,_junction.`idPlaylist` FROM `SongPlaylistCrossRef` AS _junction INNER JOIN `tb_song` ON (_junction.`idSong` = `tb_song`.`idSong`) WHERE _junction.`idPlaylist` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 10; // _junction.idPlaylist;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdSong = 0;
      final int _cursorIndexOfTitleName = 1;
      final int _cursorIndexOfUrlImage = 2;
      final int _cursorIndexOfUrlSong = 3;
      final int _cursorIndexOfCategory = 4;
      final int _cursorIndexOfArtist = 5;
      final int _cursorIndexOfSinger = 6;
      final int _cursorIndexOfAlbum = 7;
      final int _cursorIndexOfDuration = 8;
      final int _cursorIndexOfViews = 9;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          ArrayList<Song> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Song _item_1;
            final Integer _tmpIdSong;
            if (_cursor.isNull(_cursorIndexOfIdSong)) {
              _tmpIdSong = null;
            } else {
              _tmpIdSong = _cursor.getInt(_cursorIndexOfIdSong);
            }
            final String _tmpTitleName;
            if (_cursor.isNull(_cursorIndexOfTitleName)) {
              _tmpTitleName = null;
            } else {
              _tmpTitleName = _cursor.getString(_cursorIndexOfTitleName);
            }
            final String _tmpUrlImage;
            if (_cursor.isNull(_cursorIndexOfUrlImage)) {
              _tmpUrlImage = null;
            } else {
              _tmpUrlImage = _cursor.getString(_cursorIndexOfUrlImage);
            }
            final String _tmpUrlSong;
            if (_cursor.isNull(_cursorIndexOfUrlSong)) {
              _tmpUrlSong = null;
            } else {
              _tmpUrlSong = _cursor.getString(_cursorIndexOfUrlSong);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpArtist;
            if (_cursor.isNull(_cursorIndexOfArtist)) {
              _tmpArtist = null;
            } else {
              _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
            }
            final String _tmpSinger;
            if (_cursor.isNull(_cursorIndexOfSinger)) {
              _tmpSinger = null;
            } else {
              _tmpSinger = _cursor.getString(_cursorIndexOfSinger);
            }
            final String _tmpAlbum;
            if (_cursor.isNull(_cursorIndexOfAlbum)) {
              _tmpAlbum = null;
            } else {
              _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final int _tmpViews;
            _tmpViews = _cursor.getInt(_cursorIndexOfViews);
            _item_1 = new Song(_tmpIdSong,_tmpTitleName,_tmpUrlImage,_tmpUrlSong,_tmpCategory,_tmpArtist,_tmpSinger,_tmpAlbum,_tmpDuration,_tmpViews);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptbPlaylistAscomExampleMusicplayerModelRelationPlaylistWithSongs(
      final LongSparseArray<ArrayList<PlaylistWithSongs>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<PlaylistWithSongs>> _tmpInnerMap = new LongSparseArray<ArrayList<PlaylistWithSongs>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbPlaylistAscomExampleMusicplayerModelRelationPlaylistWithSongs(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<PlaylistWithSongs>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbPlaylistAscomExampleMusicplayerModelRelationPlaylistWithSongs(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `idPlaylist`,`idUserCreator`,`name`,`dateTimeCreate` FROM `tb_playlist` WHERE `idUserCreator` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idUserCreator");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdPlaylist = 0;
      final int _cursorIndexOfIdUserCreator = 1;
      final int _cursorIndexOfName = 2;
      final int _cursorIndexOfDateTimeCreate = 3;
      final LongSparseArray<ArrayList<Song>> _collectionSongs = new LongSparseArray<ArrayList<Song>>();
      while (_cursor.moveToNext()) {
        if (!_cursor.isNull(_cursorIndexOfIdPlaylist)) {
          final long _tmpKey = _cursor.getLong(_cursorIndexOfIdPlaylist);
          ArrayList<Song> _tmpSongsCollection = _collectionSongs.get(_tmpKey);
          if (_tmpSongsCollection == null) {
            _tmpSongsCollection = new ArrayList<Song>();
            _collectionSongs.put(_tmpKey, _tmpSongsCollection);
          }
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshiptbSongAscomExampleMusicplayerModelSong(_collectionSongs);
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey_1 = _cursor.getLong(_itemKeyIndex);
          ArrayList<PlaylistWithSongs> _tmpRelation = _map.get(_tmpKey_1);
          if (_tmpRelation != null) {
            final PlaylistWithSongs _item_1;
            final Playlist _tmpPlaylist;
            if (! (_cursor.isNull(_cursorIndexOfIdPlaylist) && _cursor.isNull(_cursorIndexOfIdUserCreator) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfDateTimeCreate))) {
              final Integer _tmpIdPlaylist;
              if (_cursor.isNull(_cursorIndexOfIdPlaylist)) {
                _tmpIdPlaylist = null;
              } else {
                _tmpIdPlaylist = _cursor.getInt(_cursorIndexOfIdPlaylist);
              }
              final int _tmpIdUserCreator;
              _tmpIdUserCreator = _cursor.getInt(_cursorIndexOfIdUserCreator);
              final String _tmpName;
              if (_cursor.isNull(_cursorIndexOfName)) {
                _tmpName = null;
              } else {
                _tmpName = _cursor.getString(_cursorIndexOfName);
              }
              final String _tmpDateTimeCreate;
              if (_cursor.isNull(_cursorIndexOfDateTimeCreate)) {
                _tmpDateTimeCreate = null;
              } else {
                _tmpDateTimeCreate = _cursor.getString(_cursorIndexOfDateTimeCreate);
              }
              _tmpPlaylist = new Playlist(_tmpIdPlaylist,_tmpIdUserCreator,_tmpName,_tmpDateTimeCreate);
            }  else  {
              _tmpPlaylist = null;
            }
            ArrayList<Song> _tmpSongsCollection_1 = null;
            if (!_cursor.isNull(_cursorIndexOfIdPlaylist)) {
              final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfIdPlaylist);
              _tmpSongsCollection_1 = _collectionSongs.get(_tmpKey_2);
            }
            if (_tmpSongsCollection_1 == null) {
              _tmpSongsCollection_1 = new ArrayList<Song>();
            }
            _item_1 = new PlaylistWithSongs(_tmpPlaylist,_tmpSongsCollection_1);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptbSongAscomExampleMusicplayerModelSong_1(
      final LongSparseArray<ArrayList<Song>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Song>> _tmpInnerMap = new LongSparseArray<ArrayList<Song>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbSongAscomExampleMusicplayerModelSong_1(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Song>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbSongAscomExampleMusicplayerModelSong_1(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `tb_song`.`idSong` AS `idSong`,`tb_song`.`titleName` AS `titleName`,`tb_song`.`urlImage` AS `urlImage`,`tb_song`.`urlSong` AS `urlSong`,`tb_song`.`category` AS `category`,`tb_song`.`artist` AS `artist`,`tb_song`.`singer` AS `singer`,`tb_song`.`album` AS `album`,`tb_song`.`duration` AS `duration`,`tb_song`.`views` AS `views`,_junction.`idFavourite` FROM `FavouriteSongCrossRef` AS _junction INNER JOIN `tb_song` ON (_junction.`idSong` = `tb_song`.`idSong`) WHERE _junction.`idFavourite` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 10; // _junction.idFavourite;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdSong = 0;
      final int _cursorIndexOfTitleName = 1;
      final int _cursorIndexOfUrlImage = 2;
      final int _cursorIndexOfUrlSong = 3;
      final int _cursorIndexOfCategory = 4;
      final int _cursorIndexOfArtist = 5;
      final int _cursorIndexOfSinger = 6;
      final int _cursorIndexOfAlbum = 7;
      final int _cursorIndexOfDuration = 8;
      final int _cursorIndexOfViews = 9;
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<Song> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Song _item_1;
          final Integer _tmpIdSong;
          if (_cursor.isNull(_cursorIndexOfIdSong)) {
            _tmpIdSong = null;
          } else {
            _tmpIdSong = _cursor.getInt(_cursorIndexOfIdSong);
          }
          final String _tmpTitleName;
          if (_cursor.isNull(_cursorIndexOfTitleName)) {
            _tmpTitleName = null;
          } else {
            _tmpTitleName = _cursor.getString(_cursorIndexOfTitleName);
          }
          final String _tmpUrlImage;
          if (_cursor.isNull(_cursorIndexOfUrlImage)) {
            _tmpUrlImage = null;
          } else {
            _tmpUrlImage = _cursor.getString(_cursorIndexOfUrlImage);
          }
          final String _tmpUrlSong;
          if (_cursor.isNull(_cursorIndexOfUrlSong)) {
            _tmpUrlSong = null;
          } else {
            _tmpUrlSong = _cursor.getString(_cursorIndexOfUrlSong);
          }
          final String _tmpCategory;
          if (_cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
          }
          final String _tmpArtist;
          if (_cursor.isNull(_cursorIndexOfArtist)) {
            _tmpArtist = null;
          } else {
            _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
          }
          final String _tmpSinger;
          if (_cursor.isNull(_cursorIndexOfSinger)) {
            _tmpSinger = null;
          } else {
            _tmpSinger = _cursor.getString(_cursorIndexOfSinger);
          }
          final String _tmpAlbum;
          if (_cursor.isNull(_cursorIndexOfAlbum)) {
            _tmpAlbum = null;
          } else {
            _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
          }
          final String _tmpDuration;
          if (_cursor.isNull(_cursorIndexOfDuration)) {
            _tmpDuration = null;
          } else {
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
          }
          final int _tmpViews;
          _tmpViews = _cursor.getInt(_cursorIndexOfViews);
          _item_1 = new Song(_tmpIdSong,_tmpTitleName,_tmpUrlImage,_tmpUrlSong,_tmpCategory,_tmpArtist,_tmpSinger,_tmpAlbum,_tmpDuration,_tmpViews);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptbFavouriteAscomExampleMusicplayerModelRelationFavouriteWithSongs(
      final LongSparseArray<ArrayList<FavouriteWithSongs>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<FavouriteWithSongs>> _tmpInnerMap = new LongSparseArray<ArrayList<FavouriteWithSongs>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbFavouriteAscomExampleMusicplayerModelRelationFavouriteWithSongs(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<FavouriteWithSongs>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbFavouriteAscomExampleMusicplayerModelRelationFavouriteWithSongs(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `idFavourite`,`idUser` FROM `tb_favourite` WHERE `idUser` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idUser");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdFavourite = 0;
      final int _cursorIndexOfIdUser = 1;
      final LongSparseArray<ArrayList<Song>> _collectionSongs = new LongSparseArray<ArrayList<Song>>();
      while (_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_cursorIndexOfIdFavourite);
        ArrayList<Song> _tmpSongsCollection = _collectionSongs.get(_tmpKey);
        if (_tmpSongsCollection == null) {
          _tmpSongsCollection = new ArrayList<Song>();
          _collectionSongs.put(_tmpKey, _tmpSongsCollection);
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshiptbSongAscomExampleMusicplayerModelSong_1(_collectionSongs);
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey_1 = _cursor.getLong(_itemKeyIndex);
          ArrayList<FavouriteWithSongs> _tmpRelation = _map.get(_tmpKey_1);
          if (_tmpRelation != null) {
            final FavouriteWithSongs _item_1;
            final Favourite _tmpFavourite;
            if (! (_cursor.isNull(_cursorIndexOfIdFavourite) && _cursor.isNull(_cursorIndexOfIdUser))) {
              final int _tmpIdFavourite;
              _tmpIdFavourite = _cursor.getInt(_cursorIndexOfIdFavourite);
              final int _tmpIdUser;
              _tmpIdUser = _cursor.getInt(_cursorIndexOfIdUser);
              _tmpFavourite = new Favourite(_tmpIdFavourite,_tmpIdUser);
            }  else  {
              _tmpFavourite = null;
            }
            ArrayList<Song> _tmpSongsCollection_1 = null;
            final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfIdFavourite);
            _tmpSongsCollection_1 = _collectionSongs.get(_tmpKey_2);
            if (_tmpSongsCollection_1 == null) {
              _tmpSongsCollection_1 = new ArrayList<Song>();
            }
            _item_1 = new FavouriteWithSongs(_tmpFavourite,_tmpSongsCollection_1);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
