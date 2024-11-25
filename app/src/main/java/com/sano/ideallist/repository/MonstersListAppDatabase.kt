package com.sano.ideallist.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Dao
interface FavouriteMonstersDao {
    @Query("SELECT * FROM MonsterData")
    fun getAll(): LiveData<List<MonsterData>>

    @Insert
    fun insertFavouriteMonsters(vararg monster: MonsterData)

    @Delete
    fun deleteFavouriteMonster(monster: MonsterData)
}

@Database(entities = [MonsterData::class], version = 1)
@TypeConverters(MonsterAbilityDataConverter::class)
abstract class MonstersListAppDatabase : RoomDatabase() {
    abstract fun favouriteMonsters(): FavouriteMonstersDao
}