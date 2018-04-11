package hu.ait.android.todorecylerview.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDAO {
    @Query("SELECT * FROM todo")
    List<Todo> getAll();

    @Insert
    long insertTodo(Todo todo);

    @Delete
    void delete(Todo todo);
}
