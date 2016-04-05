package com.android.list;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.hellotest.R;
import com.android.simpleGlide.MySampleGlide;


/**
 * Created by andyliu on 2016/3/21.
 */
public class TestListItemHolder extends BaseListItemHolder<String> {
    private int mLayoutId;
    private ImageView mIm;
    private Context mCtx;

    public TestListItemHolder(Context ctx, int layoutId){
        mCtx = ctx;
        mLayoutId = layoutId;
    }
    @Override
    public BaseListItemHolder createNewViewHolder() {
        return new TestListItemHolder(mCtx,mLayoutId);
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void findView(View v) {
        mIm = (ImageView) v .findViewById(R.id.image);
    }

    @Override
    public void setViewResource(String source) {
//        Glide
//                .with(mCtx)
//                .load((String)obj)
//                .placeholder(R.drawable.camera_mode)
//                .into(mIm);
        MySampleGlide.getInstance(mCtx).loadInto(source,mIm);
    }
}
