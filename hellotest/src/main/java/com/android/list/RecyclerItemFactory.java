package com.android.list;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by andyliu on 2016/3/29.
 */
public class RecyclerItemFactory {

    public static BaseRecyclerItemHolder obtainViewHolder(ViewGroup parent,int viewHolderType,int viewItemType,Context ctx){
        switch (viewHolderType){
            case RecyclerViewHolderType.PIC_VIEWHOLDER_TYPE:
                return new PicRecyclerItemHolder(parent,viewItemType,ctx);
            default:
                return null;

        }
    }

    public static class RecyclerViewHolderType {
        public final static int PIC_VIEWHOLDER_TYPE = 0;
    }
}
