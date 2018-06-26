package yuan.com.androidarchitecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yuan.com.androidarchitecture.ui.detail.JokeDetailActivity;
import yuan.com.androidarchitecture.ui.main.MainActivity;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract JokeDetailActivity movieDetailActivity();
}
