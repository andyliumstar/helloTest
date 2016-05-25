package com.android.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.hellotest.R;
import com.android.simpleGlide.MySampleGlide;

/**
 * Created by andyliu on 2016/3/29.
 */
public class PicRecyclerItemHolder extends BaseRecyclerItemHolder {
    private Context mCtx;
    private ImageView mIm;
    public PicRecyclerItemHolder(ViewGroup parent,int type,Context ctx) {
        super(LayoutInflater
                .from(parent.getContext())
                .inflate(getLayoutId(type), parent, false));
        mCtx = ctx;
    }

    private static int getLayoutId(int type){
        int layout = 0;
        switch(type){
            case TYPE.PIC_TYPE:
                layout = R.layout.list_item;
                break;
            default:
                break;
        }

        return layout;
    }

    @Override
    public void findView(View v) {
        mIm = (ImageView)v.findViewById(R.id.image);
    }

    @Override
    public void setViewResource(BaseRecyclerSource source) {
        switch(source.getType()){
            case TYPE.PIC_TYPE:
                MySampleGlide.getInstance(mCtx)
                        .reSize(mCtx.getResources().getDimensionPixelSize(R.dimen.image_wid))
                        .loadInto(((PicRecyclerSource) source).getUrl(), mIm);
                break;
            default:
                break;
        }
    }

    public static class TYPE{
        public final static int PIC_TYPE = 0;
    }
}
