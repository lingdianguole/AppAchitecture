package yuan.com.androidarchitecture.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import yuan.com.androidarchitecture.data.local.dao.JokeDao;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

@Database(entities = {JokeEntity.class}, version = 2, exportSchema = false)
public abstract class JokeDatabase extends RoomDatabase {

    public abstract JokeDao movieDao();
}
