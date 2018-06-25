package yuan.com.androidarchitecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yuan.com.androidarchitecture.ui.main.JokeListFragment;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract JokeListFragment contributeMovieListFragment();
}
