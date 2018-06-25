package yuan.com.androidarchitecture.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import yuan.com.androidarchitecture.data.JokeRepository;
import yuan.com.androidarchitecture.data.Resource;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;


public class JokeDetailViewModel extends ViewModel{
    private final LiveData<Resource<JokeEntity>>  movieDetail = new MutableLiveData<>();
    private final JokeRepository jokeRepository;

    @Inject
    public JokeDetailViewModel(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public LiveData<JokeEntity> getMovie(int id){
        return jokeRepository.getMovie(id);
    }
}
