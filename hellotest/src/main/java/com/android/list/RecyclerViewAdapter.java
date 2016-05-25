package com.android.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.hellotest.MainActivity;

import java.util.ArrayList;

/**
 * Created by andyliu on 2016/3/29.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerItemHolder> {
    private ArrayList<BaseRecyclerSource> mListResource;

    private Context mCtx;
    private int mViewHolderType;

    public RecyclerViewAdapter(Context ctx,int viewHolderType){
        mCtx = ctx;
        mViewHolderType = viewHolderType;
    }

    public void setListResource(ArrayList<BaseRecyclerSource> listResource){
        mListResource = listResource;
    }
    @Override
    public BaseRecyclerItemHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        return RecyclerItemFactory.obtainViewHolder(viewGroup,mViewHolderType,itemType,mCtx);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerItemHolder baseListItemHolder, int position) {
        if(null != mListResource){
            baseListItemHolder.setViewResource(mListResource.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(null != mListResource){
            return mListResource.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(null != mListResource){
            return mListResource.get(position).getType();
        }
        return 0;
    }
}
