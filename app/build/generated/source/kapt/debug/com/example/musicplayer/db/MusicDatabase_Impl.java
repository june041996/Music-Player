package com.example.musicplayer.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MusicDatabase_Impl extends MusicDatabase {
  private volatile MusicDao _musicDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tb_user` (`idUser` INTEGER PRIMARY KEY AUTOINCREMENT, `username` TEXT NOT NULL, `password` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tb_song` (`idSong` INTEGER PRIMARY KEY AUTOINCREMENT, `titleName` TEXT NOT NULL, `urlImage` TEXT NOT NULL, `urlSong` TEXT NOT NULL, `category` TEXT NOT NULL, `artist` TEXT NOT NULL, `singer` TEXT NOT NULL, `album` TEXT NOT NULL, `duration` TEXT NOT NULL, `views` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tb_playlist` (`idPlaylist` INTEGER PRIMARY KEY AUTOINCREMENT, `idUserCreator` INTEGER NOT NULL, `name` TEXT NOT NULL, `dateTimeCreate` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SongPlaylistCrossRef` (`idSong` INTEGER NOT NULL, `idPlaylist` INTEGER NOT NULL, PRIMARY KEY(`idSong`, `idPlaylist`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tb_favourite` (`idFavourite` INTEGER NOT NULL, `idUser` INTEGER NOT NULL, PRIMARY KEY(`idFavourite`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `FavouriteSongCrossRef` (`idFavourite` INTEGER NOT NULL, `idSong` INTEGER NOT NULL, PRIMARY KEY(`idFavourite`, `idSong`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '79309e4c327c459f0aa50eb49d8114d9')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tb_user`");
        _db.execSQL("DROP TABLE IF EXISTS `tb_song`");
        _db.execSQL("DROP TABLE IF EXISTS `tb_playlist`");
        _db.execSQL("DROP TABLE IF EXISTS `SongPlaylistCrossRef`");
        _db.execSQL("DROP TABLE IF EXISTS `tb_favourite`");
        _db.execSQL("DROP TABLE IF EXISTS `FavouriteSongCrossRef`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTbUser = new HashMap<String, TableInfo.Column>(3);
        _columnsTbUser.put("idUser", new TableInfo.Column("idUser", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbUser.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbUser.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbUser = new TableInfo("tb_user", _columnsTbUser, _foreignKeysTbUser, _indicesTbUser);
        final TableInfo _existingTbUser = TableInfo.read(_db, "tb_user");
        if (! _infoTbUser.equals(_existingTbUser)) {
          return new RoomOpenHelper.ValidationResult(false, "tb_user(com.example.musicplayer.model.User).\n"
                  + " Expected:\n" + _infoTbUser + "\n"
                  + " Found:\n" + _existingTbUser);
        }
        final HashMap<String, TableInfo.Column> _columnsTbSong = new HashMap<String, TableInfo.Column>(10);
        _columnsTbSong.put("idSong", new TableInfo.Column("idSong", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("titleName", new TableInfo.Column("titleName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("urlImage", new TableInfo.Column("urlImage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("urlSong", new TableInfo.Column("urlSong", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("singer", new TableInfo.Column("singer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("album", new TableInfo.Column("album", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("duration", new TableInfo.Column("duration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbSong.put("views", new TableInfo.Column("views", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbSong = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbSong = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbSong = new TableInfo("tb_song", _columnsTbSong, _foreignKeysTbSong, _indicesTbSong);
        final TableInfo _existingTbSong = TableInfo.read(_db, "tb_song");
        if (! _infoTbSong.equals(_existingTbSong)) {
          return new RoomOpenHelper.ValidationResult(false, "tb_song(com.example.musicplayer.model.Song).\n"
                  + " Expected:\n" + _infoTbSong + "\n"
                  + " Found:\n" + _existingTbSong);
        }
        final HashMap<String, TableInfo.Column> _columnsTbPlaylist = new HashMap<String, TableInfo.Column>(4);
        _columnsTbPlaylist.put("idPlaylist", new TableInfo.Column("idPlaylist", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbPlaylist.put("idUserCreator", new TableInfo.Column("idUserCreator", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbPlaylist.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbPlaylist.put("dateTimeCreate", new TableInfo.Column("dateTimeCreate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbPlaylist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbPlaylist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbPlaylist = new TableInfo("tb_playlist", _columnsTbPlaylist, _foreignKeysTbPlaylist, _indicesTbPlaylist);
        final TableInfo _existingTbPlaylist = TableInfo.read(_db, "tb_playlist");
        if (! _infoTbPlaylist.equals(_existingTbPlaylist)) {
          return new RoomOpenHelper.ValidationResult(false, "tb_playlist(com.example.musicplayer.model.Playlist).\n"
                  + " Expected:\n" + _infoTbPlaylist + "\n"
                  + " Found:\n" + _existingTbPlaylist);
        }
        final HashMap<String, TableInfo.Column> _columnsSongPlaylistCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsSongPlaylistCrossRef.put("idSong", new TableInfo.Column("idSong", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongPlaylistCrossRef.put("idPlaylist", new TableInfo.Column("idPlaylist", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSongPlaylistCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSongPlaylistCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSongPlaylistCrossRef = new TableInfo("SongPlaylistCrossRef", _columnsSongPlaylistCrossRef, _foreignKeysSongPlaylistCrossRef, _indicesSongPlaylistCrossRef);
        final TableInfo _existingSongPlaylistCrossRef = TableInfo.read(_db, "SongPlaylistCrossRef");
        if (! _infoSongPlaylistCrossRef.equals(_existingSongPlaylistCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "SongPlaylistCrossRef(com.example.musicplayer.model.relation.SongPlaylistCrossRef).\n"
                  + " Expected:\n" + _infoSongPlaylistCrossRef + "\n"
                  + " Found:\n" + _existingSongPlaylistCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsTbFavourite = new HashMap<String, TableInfo.Column>(2);
        _columnsTbFavourite.put("idFavourite", new TableInfo.Column("idFavourite", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTbFavourite.put("idUser", new TableInfo.Column("idUser", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbFavourite = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbFavourite = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbFavourite = new TableInfo("tb_favourite", _columnsTbFavourite, _foreignKeysTbFavourite, _indicesTbFavourite);
        final TableInfo _existingTbFavourite = TableInfo.read(_db, "tb_favourite");
        if (! _infoTbFavourite.equals(_existingTbFavourite)) {
          return new RoomOpenHelper.ValidationResult(false, "tb_favourite(com.example.musicplayer.model.Favourite).\n"
                  + " Expected:\n" + _infoTbFavourite + "\n"
                  + " Found:\n" + _existingTbFavourite);
        }
        final HashMap<String, TableInfo.Column> _columnsFavouriteSongCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsFavouriteSongCrossRef.put("idFavourite", new TableInfo.Column("idFavourite", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavouriteSongCrossRef.put("idSong", new TableInfo.Column("idSong", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavouriteSongCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavouriteSongCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavouriteSongCrossRef = new TableInfo("FavouriteSongCrossRef", _columnsFavouriteSongCrossRef, _foreignKeysFavouriteSongCrossRef, _indicesFavouriteSongCrossRef);
        final TableInfo _existingFavouriteSongCrossRef = TableInfo.read(_db, "FavouriteSongCrossRef");
        if (! _infoFavouriteSongCrossRef.equals(_existingFavouriteSongCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "FavouriteSongCrossRef(com.example.musicplayer.model.relation.FavouriteSongCrossRef).\n"
                  + " Expected:\n" + _infoFavouriteSongCrossRef + "\n"
                  + " Found:\n" + _existingFavouriteSongCrossRef);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "79309e4c327c459f0aa50eb49d8114d9", "c0e50243d290f4654dd14e7d6084dc93");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tb_user","tb_song","tb_playlist","SongPlaylistCrossRef","tb_favourite","FavouriteSongCrossRef");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tb_user`");
      _db.execSQL("DELETE FROM `tb_song`");
      _db.execSQL("DELETE FROM `tb_playlist`");
      _db.execSQL("DELETE FROM `SongPlaylistCrossRef`");
      _db.execSQL("DELETE FROM `tb_favourite`");
      _db.execSQL("DELETE FROM `FavouriteSongCrossRef`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MusicDao.class, MusicDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public MusicDao songDao() {
    if (_musicDao != null) {
      return _musicDao;
    } else {
      synchronized(this) {
        if(_musicDao == null) {
          _musicDao = new MusicDao_Impl(this);
        }
        return _musicDao;
      }
    }
  }
}
