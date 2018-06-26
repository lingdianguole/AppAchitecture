package yuan.com.androidarchitecture.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import yuan.com.androidarchitecture.data.JokeRepository;
import yuan.com.androidarchitecture.data.Resource;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class JokeListViewModel extends ViewModel {
    private JokeRepository jokeRepository;
    private MutableLiveData<String> pageLiveData = new MutableLiveData<>();
    public final LiveData<Resource<List<JokeEntity>>> jokeLiveData =
            Transformations.switchMap(pageLiveData, type -> jokeRepository.loadPopularMovies(type, "1"));

    @Inject
    public JokeListViewModel(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public void loadJokes(String type) {
        pageLiveData.setValue(type);
    }
}
