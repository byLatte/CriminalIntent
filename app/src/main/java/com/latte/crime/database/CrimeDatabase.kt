package com.latte.crime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.latte.crime.Crime

@Database(entities = [Crime::class], version = 2) // crime 테이블 변경하면서 버전업.
@TypeConverters(CrimeTypeConverters::class) //타입변환기
abstract class CrimeDatabase: RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}

val migration_1_2 = object : Migration(1,2){ // 업데이트 전 버전, 업데이트 할 버전
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT '' "
        )
    }
}