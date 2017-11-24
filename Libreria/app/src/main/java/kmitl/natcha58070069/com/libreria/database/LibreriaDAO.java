package kmitl.natcha58070069.com.libreria.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

@Dao
public interface LibreriaDAO {

    @Insert
    void insert(LibreriaInfo libreriaInfo);

    @Query("SELECT * FROM LibreriaInfo")
    List<LibreriaInfo> findAll();

    @Update
    void update(LibreriaInfo libreriaInfo);

    @Delete
    void delete(LibreriaInfo libreriaInfo);

}
