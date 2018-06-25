package yuan.com.androidarchitecture.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import yuan.com.androidarchitecture.R;
import yuan.com.androidarchitecture.data.remote.ApiConstants;

public final class ImageBindingAdapter {

    @BindingAdapter(value = "url")
    public static void loadImageUrl(ImageView view, String url) {
        if (url != null && !url.equals(""))
            Picasso.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .into(view);
    }

}
