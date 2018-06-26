package yuan.com.androidarchitecture.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import yuan.com.androidarchitecture.data.local.entity.JokeEntity;
import yuan.com.androidarchitecture.ui.BaseAdapter;
import yuan.com.androidarchitecture.databinding.ItemJokeListBinding;

public class JokeListAdapter extends BaseAdapter<JokeListAdapter.JokeViewHolder, JokeEntity> {

    private List<JokeEntity> jokeEntities;

    private final JokeListCallback jokeListCallback;

    public JokeListAdapter(@NonNull JokeListCallback jokeListCallback) {
        jokeEntities = new ArrayList<>();
        this.jokeListCallback = jokeListCallback;
    }

    @Override
    public void setData(List<JokeEntity> jokeEntities) {
        this.jokeEntities = jokeEntities;
        notifyDataSetChanged();
    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return JokeViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, jokeListCallback);
    }

    @Override
    public void onBindViewHolder(JokeViewHolder viewHolder, int i) {
        viewHolder.onBind(jokeEntities.get(i));
    }

    @Override
    public int getItemCount() {
        return jokeEntities.size();
    }

    static class JokeViewHolder extends RecyclerView.ViewHolder {

        public static JokeViewHolder create(LayoutInflater inflater, ViewGroup parent, JokeListCallback callback) {
            ItemJokeListBinding itemJokeListBinding = ItemJokeListBinding.inflate(inflater, parent, false);
            return new JokeViewHolder(itemJokeListBinding, callback);
        }

        ItemJokeListBinding binding;

        public JokeViewHolder(ItemJokeListBinding binding, JokeListCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onJokeClicked(binding.getJoke()));
        }

        public void onBind(JokeEntity jokeEntity) {
            binding.setJoke(jokeEntity);
            binding.executePendingBindings();
        }
    }
}
