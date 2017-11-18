package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity

@Database(entities = arrayOf(WatchedEpisodeEntity::class,
        SeasonTrackerEntity::class,
        EpisodeReminderEntity::class,
        PremiereReminderEntity::class),
        version = 1)
@TypeConverters(Converters::class)
abstract class RoomService : RoomDatabase() {

    abstract val watchedEpisodeDao: WatchedEpisodeDao

    abstract val runtimeDao: RuntimeDao

    abstract val seasonTrackerDao: SeasonTrackerDao

    abstract val episodeReminderDao: EpisodeReminderDao

    abstract val premiereReminderDao: PremiereReminderDao

    companion object {

        @Volatile private var INSTANCE: RoomService? = null

        fun getInstance(context: Context, dbName: String): RoomService =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context, dbName).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context, dbName: String) =
                Room.databaseBuilder(context.applicationContext, RoomService::class.java, dbName)
                        .build()
    }

}
