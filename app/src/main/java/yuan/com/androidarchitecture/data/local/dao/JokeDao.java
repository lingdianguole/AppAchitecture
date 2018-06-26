package yuan.com.androidarchitecture.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import retrofit2.http.DELETE;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

@Dao
public interface JokeDao {

    @Query("SELECT * FROM jokes where data_type =:type")
    LiveData<List<JokeEntity>> loadJokes(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveJokes(List<JokeEntity> jokeEntities);

    @Query("DELETE  FROM jokes where data_type=:type")
    void deleteJokes(String type);

    @Query("SELECT * FROM jokes WHERE user_id=:user_id")
    LiveData<JokeEntity> getJoke(String user_id);

}
