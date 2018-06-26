package yuan.com.androidarchitecture.ui.main;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mertsimsek on 20/05/2017.
 */

public class JokesPagerAdapter extends FragmentStatePagerAdapter{

    private static final String[] titles = new String[]{"one", "two", "three"};

    public JokesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return JokeListFragment.newInstance(i+1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
