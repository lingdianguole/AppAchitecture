package yuan.com.androidarchitecture.ui.main;

import android.view.View;

import yuan.com.androidarchitecture.data.local.entity.JokeEntity;


public interface JokeListCallback {
    void onJokeClicked(JokeEntity jokeEntity);
}
