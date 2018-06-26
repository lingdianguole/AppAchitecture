package yuan.com.androidarchitecture.ui.main;

import android.content.Intent;
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
import yuan.com.androidarchitecture.widget.OffsetDecoration;

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
        dataBinding.recyclerView.setAdapter(new JokeListAdapter(this));
        dataBinding.recyclerView.addItemDecoration(new OffsetDecoration(10));
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String type = getArguments().getString(INTENT_TYPE);
        //用liveData观察数据，改变UI
        viewModel.loadJokes(type);
        viewModel.jokeLiveData
                .observe(this, listResource -> dataBinding.setResource(listResource));
    }

    @Override
    public void onJokeClicked(JokeEntity jokeEntity) {
        Intent intent = new Intent(getContext(), JokeDetailActivity.class);
        intent.putExtra(JokeDetailActivity.KEY_JOKE_ID, jokeEntity.getUser_id());
        startActivity(intent);
    }
}
