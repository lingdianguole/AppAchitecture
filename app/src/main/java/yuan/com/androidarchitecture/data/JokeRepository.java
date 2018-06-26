package yuan.com.androidarchitecture.data;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

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

    public LiveData<Resource<List<JokeEntity>>> loadPopularJokes(String type, String page) {
        return new NetworkBoundResource<List<JokeEntity>, JokeResponse>() {

            @Override
            protected void saveCallResult(@NonNull JokeResponse item) {
                if (item != null && item.getData() != null) {
                    for (JokeEntity jokeEntity : item.getData()) {  //由于返回的type和请求的type不一样，所以自定义类型
                        jokeEntity.setData_type(type);
                    }
                    if (page.equals("1")) {   //第一页的时候清空数据,防止一直加载的缓存数据
                        jokeDao.deleteJokes(type);
                    }
                    jokeDao.saveJokes(item.getData());
                }
            }

            @NonNull
            @Override
            protected LiveData<List<JokeEntity>> loadFromDb() {
                return jokeDao.loadJokes(type);
            }

            @NonNull
            @Override
            protected Call<JokeResponse> createCall() {
                return jokeDBService.loadJokes(type, page);
            }
        }.getAsLiveData();
    }

    public LiveData<JokeEntity> getJoke(String user_id) {
        return jokeDao.getJoke(user_id);
    }
}
