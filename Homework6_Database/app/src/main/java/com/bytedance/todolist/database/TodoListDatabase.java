package com.bytedance.todolist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Database(entities = {TodoListEntity.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class TodoListDatabase extends RoomDatabase {
    private static volatile TodoListDatabase INSTANCE;

    public abstract TodoListDao todoListDao();

    public TodoListDatabase() {

    }

    public static TodoListDatabase inst(Context context) {
        if (INSTANCE == null) {
            synchronized (TodoListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoListDatabase.class, "todo.db")
                            .addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE todo"
            + " ADD COLUMN Done INTEGER");
        }
    };
}
