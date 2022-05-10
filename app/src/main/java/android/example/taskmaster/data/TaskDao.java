package android.example.taskmaster.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM Tasks WHERE id = :id")
    Tasks getModelsByID(Long id);

    @Insert
    Long insertStudent(Tasks task);
}
