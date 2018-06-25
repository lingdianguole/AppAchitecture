package yuan.com.androidarchitecture.ui.main;

import android.arch.lifecycle.LiveData;
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

    @Inject
    public JokeListViewModel(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    LiveData<Resource<List<JokeEntity>>> getPopularMovies(String type) {
        return jokeRepository.loadPopularMovies(type);
    }
}
