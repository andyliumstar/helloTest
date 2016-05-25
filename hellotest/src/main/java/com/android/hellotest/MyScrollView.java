package com.android.hellotest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.simpleGlide.MySampleGlide;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by andyliu on 2016/3/28.
 */
public class MyScrollView extends ScrollView {
    private String TAG = "liuy";

    private LinearLayout mContainer;
    private int PAGESIZE = 4;
    private int mCurIndex = -1;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ArrayList<String> mResourceList = null;
    private LinkedList<ImageView> mImageViewList = null;
    private LinkedList<ImageView> mImageViewListReuse = null;
    LinearLayout.LayoutParams mParams = null;
    public void initView(ArrayList<String> resourceList){
        mResourceList = resourceList;
        mImageViewList = new LinkedList<ImageView>();
        mImageViewListReuse = new LinkedList<ImageView>();
        mParams = new LinearLayout.LayoutParams(
                1000, LayoutParams.WRAP_CONTENT);
        mContainer = (LinearLayout)findViewById(R.id.view_container);
        loadMoreImages(PAGESIZE,true);
    }

    protected void loadBottomImage(int loadSize){
        if (!mImageViewList.isEmpty()) {
            mCurIndex =  (int) mImageViewList.peekLast().getTag(R.string.index_tag);
        }

        int i = mCurIndex + 1;
        int j = mCurIndex + 1 + loadSize;

        Log.d(TAG, "loadMoreImages:" + j);
        while(i<j && i < mResourceList.size()){
            final String url = mResourceList.get(i);

            if(mImageViewListReuse.isEmpty()){
                imageView = new ImageView(getContext());
                imageView.setLayoutParams(mParams);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }else{
                imageView = mImageViewListReuse.pollFirst();
            }

            imageView.setTag(R.string.index_tag, i);
            mContainer.addView(imageView);
            mImageViewList.add(imageView);
            MySampleGlide.getInstance(getContext())
                    .reSize(1000, 1000)
                    .loadInto(url, imageView);
            ++i;
            ++mCurIndex;
        }

        while(mImageViewList.size() > PAGESIZE * 2) {
            Log.d(TAG, "checkResueView:" + "reuse view");
            imageView = mImageViewList.pollFirst();
            mImageViewListReuse.add(imageView);
            mContainer.removeView(imageView);
            scrollBy(0, -imageView.getHeight());
        }
    }

    protected void loadTopImage(int loadSize){
        if (!mImageViewList.isEmpty()) {
            mCurIndex =  (int) mImageViewList.peekFirst().getTag(R.string.index_tag);
        }

        int i = mCurIndex - 1;
        int j = mCurIndex - 1 - loadSize;

        Log.d(TAG, "loadMoreImages:" + j);
        while(i >=0 && i>j){
            final String url = mResourceList.get(i);

            if(mImageViewListReuse.isEmpty()){
                imageView = new ImageView(getContext());
                imageView.setLayoutParams(mParams);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }else{
                imageView = mImageViewListReuse.pollFirst();
            }

            imageView.setTag(R.string.index_tag, i);
            mContainer.addView(imageView,0);
            mImageViewList.addFirst(imageView);
            MySampleGlide.getInstance(getContext())
                    .reSize(1000, 1000)
                    .loadInto(url, imageView);
            --i;
            --mCurIndex;
        }

        while(mImageViewList.size() > PAGESIZE * 2) {
            Log.d(TAG, "checkResueView:" + "reuse view");
            imageView = mImageViewList.pollLast();
            mImageViewListReuse.add(imageView);
            mContainer.removeView(imageView);
            scrollBy(0,imageView.getHeight());
        }
    }

    ImageView imageView = null;
    protected void loadMoreImages(int loadSize,boolean scrollToBottom){
        if(scrollToBottom){
            loadBottomImage(loadSize);
        }else{
            loadTopImage(loadSize);
        }
    }


    interface ScrollChangeListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private boolean mScrollToBottom = false;
    private boolean mScrollToTop = false;
    private ScrollChangeListener mScrollChangeListener = new ScrollChangeListener() {
        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            Log.d(TAG,t+"/  "+getHeight()  +"/  "+mContainer.getHeight() + "/  "+(t + getHeight()-mContainer.getHeight()));
            if(t + getHeight()+800 >= mContainer.getHeight() ){
                mScrollToBottom = true;
            }else if(t <= 400){
                Log.d(TAG,"onScrollChanged ===top");
                mScrollToTop = true;
            }
        }
    };

    public void setOnScrollChangeListener(ScrollChangeListener scrollChangeListener){
       // mScrollChangeListener = scrollChangeListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(null != mScrollChangeListener){
            mScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP)
        if ( mScrollToBottom){
            mScrollToBottom = false;
            loadMoreImages(3,true);
        }else if(mScrollToTop){
            mScrollToTop = false;
            loadMoreImages(3,false);
        }
        return super.onTouchEvent(ev);
    }
}
