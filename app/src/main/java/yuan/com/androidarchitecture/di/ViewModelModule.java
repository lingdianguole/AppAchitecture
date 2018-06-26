package yuan.com.androidarchitecture.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import yuan.com.androidarchitecture.ui.detail.JokeDetailViewModel;
import yuan.com.androidarchitecture.ui.main.JokeListViewModel;
import yuan.com.androidarchitecture.viewmodel.JokeViewModelFactory;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(JokeListViewModel.class)
    abstract ViewModel bindsMovieListViewModel(JokeListViewModel jokeListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(JokeDetailViewModel.class)
    abstract  ViewModel bindsMovieDetailViewModel(JokeDetailViewModel jokeDetailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(JokeViewModelFactory movieViewModelFactory);
}
