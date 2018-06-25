package yuan.com.androidarchitecture.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import yuan.com.androidarchitecture.data.Resource;
import yuan.com.androidarchitecture.ui.BaseAdapter;

public final class ListBindingAdapter{
    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if(adapter == null)
            return;

        if(resource == null || resource.data == null)
            return;

        if(adapter instanceof BaseAdapter){
            ((BaseAdapter)adapter).setData((List) resource.data);
        }
    }
}
