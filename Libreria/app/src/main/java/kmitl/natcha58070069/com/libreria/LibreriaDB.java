package kmitl.natcha58070069.com.libreria;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Nacha on 18-Nov-17.
 */

@Database(entities = {LibreriaInfo.class}, version = 1)
public abstract class LibreriaDB extends RoomDatabase {
    public abstract LibreriaDAO getLibreriaDAO();
}
