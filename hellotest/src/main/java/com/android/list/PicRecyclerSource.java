package com.android.list;

/**
 * Created by andyliu on 2016/3/29.
 */
public class PicRecyclerSource extends BaseRecyclerSource{
    private String mPicUrl;
    private int mItemType;
    public PicRecyclerSource(String url, int type) {
        mPicUrl = url;
        mItemType = type;
    }

    @Override
    int getType() {
        return mItemType;
    }

    public String getUrl(){
        return mPicUrl;
    }
}
