package hu.bme.aut.android.roomstudentdemo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GradeDAO {
    @Query("SELECT * FROM grade")
    public List<Grade> getAllGrades();

    @Insert
    void insertAll(Grade... grades);

    @Delete
    void delete(Grade grade);
}
