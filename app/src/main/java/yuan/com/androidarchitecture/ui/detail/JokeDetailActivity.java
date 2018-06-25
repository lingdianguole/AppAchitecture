package yuan.com.androidarchitecture.ui.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import yuan.com.androidarchitecture.R;
import yuan.com.androidarchitecture.databinding.ActivityJokeDetailBinding;

public class JokeDetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String KEY_JOKE_ID = "key_joke_id";

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    ActivityJokeDetailBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    JokeDetailViewModel jokeDetailViewModel;

    public static Intent newIntent(Context context, String jokeId) {
        Intent intent = new Intent(context, JokeDetailActivity.class);
        intent.putExtra(KEY_JOKE_ID, jokeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_joke_detail);
        jokeDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(JokeDetailViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int jokeId = getIntent().getIntExtra(KEY_JOKE_ID, 0);
        jokeDetailViewModel.getMovie(jokeId)
                .observe(this, jokeEntity -> binding.setJoke(jokeEntity));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
