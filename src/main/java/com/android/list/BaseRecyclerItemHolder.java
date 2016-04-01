package com.android.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by andyliu on 2016/3/29.
 */
public abstract class BaseRecyclerItemHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerItemHolder(View itemView) {
        super(itemView);
        findView(itemView);
    }

    public abstract void findView(View v);
    public abstract void setViewResource(BaseRecyclerSource source);
}
