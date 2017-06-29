package yeyeapp.in.mytestproject.listener;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(T item, View view, int position);
}
