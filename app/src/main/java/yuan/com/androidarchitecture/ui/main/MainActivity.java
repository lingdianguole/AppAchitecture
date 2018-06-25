package yuan.com.androidarchitecture.ui.main;

import android.os.Bundle;

import yuan.com.androidarchitecture.R;
import yuan.com.androidarchitecture.databinding.ActivityMainBinding;
import yuan.com.androidarchitecture.ui.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding.viewPager.setAdapter(new JokesPagerAdapter(getSupportFragmentManager()));
        dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
        dataBinding.viewPager.setOffscreenPageLimit(3);
    }
}
