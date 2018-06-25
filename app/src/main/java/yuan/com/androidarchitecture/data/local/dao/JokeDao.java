package yuan.com.androidarchitecture.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

@Dao
public interface JokeDao {

    @Query("SELECT * FROM jokes")
    LiveData<List<JokeEntity>> loadJokes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<JokeEntity> movieEntities);

    @Query("SELECT * FROM jokes WHERE user_id=:user_id")
    LiveData<JokeEntity> getJoke(int user_id);

}
