package yuan.com.androidarchitecture.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import yuan.com.androidarchitecture.data.local.dao.JokeDao;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;
import yuan.com.androidarchitecture.data.remote.JokeDBService;
import yuan.com.androidarchitecture.data.remote.model.JokeResponse;
import retrofit2.Call;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class JokeRepository {

    private final JokeDao jokeDao;
    private final JokeDBService jokeDBService;

    @Inject
    public JokeRepository(JokeDao jokeDao, JokeDBService jokeDBService) {
        this.jokeDao = jokeDao;
        this.jokeDBService = jokeDBService;
    }

    public LiveData<Resource<List<JokeEntity>>> loadPopularMovies(String type) {
        return new NetworkBoundResource<List<JokeEntity>, JokeResponse>() {

            @Override
            protected void saveCallResult(@NonNull JokeResponse item) {
                if (item != null && item.getData() != null) {
                    jokeDao.saveMovies(item.getData());
                }
            }

            @NonNull
            @Override
            protected LiveData<List<JokeEntity>> loadFromDb() {
                return jokeDao.loadJokes();
            }

            @NonNull
            @Override
            protected Call<JokeResponse> createCall() {
                return jokeDBService.loadJokes(type, "1");
            }
        }.getAsLiveData();
    }

    public LiveData<JokeEntity> getMovie(int id) {
        return jokeDao.getJoke(id);
    }
}
