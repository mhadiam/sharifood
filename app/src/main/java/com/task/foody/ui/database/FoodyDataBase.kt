package com.task.foody.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.CustomerLikeRestaurant
import com.task.foody.ui.database.dto.CustomerMessageRestaurant
import com.task.foody.ui.database.dto.Food

@Database(
    entities = [
        Account::class,
        CustomerLikeRestaurant::class,
        CustomerMessageRestaurant::class,
        Food::class,
        Comment::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class FoodyDataBase : RoomDatabase() {

    public abstract fun FoodyDao(): FoodyDao

    companion object {
        private val DB_NAME = "foody_db"
        private var instance: FoodyDataBase? = null

        @Synchronized
        public fun getInstance(context: Context): FoodyDataBase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodyDataBase::class.java,
                    DB_NAME
                ).build()
            return instance!!
        }
    }

}
