package com.sandyzfeaklab.breakdown_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;

@Database(entities = Material_List.class, version = 1)
    public abstract class Material_Database extends RoomDatabase {

        //TODO need to update the DB from server
        private static Material_Database instance;

        public abstract Material_list_DAO material_list_dao();

        public static synchronized Material_Database getInstance(Context context){
            if (instance==null){
                instance= Room.databaseBuilder(context.getApplicationContext(),
                        Material_Database.class, "spares.db").createFromAsset("databases/spares.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
            return instance;
        }

        public static synchronized void updateDatabae(Context context, File localefile){
            if (instance==null){
                instance= Room.databaseBuilder(context.getApplicationContext(),
                                Material_Database.class, "spares.db").createFromFile(localefile)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }






}
