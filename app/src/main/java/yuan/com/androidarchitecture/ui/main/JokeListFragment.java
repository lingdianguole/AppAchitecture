package yuan.com.androidarchitecture.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yuan.com.androidarchitecture.R;
import yuan.com.androidarchitecture.data.local.entity.JokeEntity;
import yuan.com.androidarchitecture.databinding.FragmentJokeListBinding;
import yuan.com.androidarchitecture.ui.BaseFragment;
import yuan.com.androidarchitecture.ui.detail.JokeDetailActivity;

public class JokeListFragment extends BaseFragment<JokeListViewModel, FragmentJokeListBinding> implements JokeListCallback {
    private static final String INTENT_TYPE = "intent_type";

    public static JokeListFragment newInstance(int type) {
        Bundle args = new Bundle();
        JokeListFragment fragment = new JokeListFragment();
        args.putString(INTENT_TYPE, String.valueOf(type));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Class<JokeListViewModel> getViewModel() {
        return JokeListViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_joke_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dataBinding.recyclerView.setAdapter(new JokeListAdapter(this));
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String type = getArguments().getString(INTENT_TYPE);
        //用liveData观察数据，改变UI
        viewModel.getPopularMovies(type)
                .observe(this, listResource -> dataBinding.setResource(listResource));
    }

    @Override
    public void onJokeClicked(JokeEntity jokeEntity, View sharedView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), sharedView, getString(R.string.shared_image));
        startActivity(JokeDetailActivity.newIntent(getActivity(), jokeEntity.getUser_id()), options.toBundle());
    }
}
