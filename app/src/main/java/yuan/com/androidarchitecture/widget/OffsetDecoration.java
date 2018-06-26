package yuan.com.androidarchitecture.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public OffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(offset, offset, offset, offset);
    }
}
