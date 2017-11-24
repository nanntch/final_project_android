package kmitl.natcha58070069.com.libreria.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;


@Database(entities = {LibreriaInfo.class}, version = 1)
public abstract class LibreriaDB extends RoomDatabase {
    public abstract LibreriaDAO getLibreriaDAO();
}
