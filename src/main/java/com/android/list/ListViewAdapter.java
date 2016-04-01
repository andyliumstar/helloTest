
package com.android.list;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.list.BaseListItemHolder;

/**
 * @author andy.liu 
 * we use one adapter for all the list view,so we should have different list view itemholder
 */
public class ListViewAdapter extends BaseAdapter {
    /*
     * mListResource : the resource of listview item mListItemHolder: the item of listview
     */
    private ArrayList<Object> mListResource;

    private LayoutInflater mInflater;

    private BaseListItemHolder mListItemHolder;

    private int mSelected;

    public ListViewAdapter(Context context, BaseListItemHolder listItemHolder) {
        if(null == context || null == listItemHolder){
            return;
        }
        mInflater = LayoutInflater.from(context);
        this.mListItemHolder = listItemHolder;
    }

    public void setListResource(ArrayList<Object> listItem) {
        if (listItem != mListResource && null != mListResource) {
            mListResource.clear();
        }
        mListResource = listItem;
    }

    public void deleteListItem(int pos) {
        if (mListResource != null) {
            if (pos >= 0 && pos < mListResource.size()) {
                mListResource.remove(pos);
            }
        }

    }

    public void addListItem(Object obj) {
        if (mListResource == null) {
            mListResource = new ArrayList<Object>();
        }
        mListResource.add(obj);
    }

    public void setSelected(int pos) {
        mSelected = pos;
    }

    public int getSelected() {
        return mSelected;
    }

    @Override
    public int getCount() {

        if (mListResource != null) {
            return mListResource.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        if (mListResource != null && position < mListResource.size()) {
            return mListResource.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mInflater){
            return null;
        }

        if (convertView == null) {
            /*
             * the mListItemHolder's first instance just using for createNewViewHolder(); findView are the abstract
             * functions ,so it can adapt for different listview
             */
            mListItemHolder = mListItemHolder.createNewViewHolder();
            convertView = mInflater.inflate(mListItemHolder.getLayoutId(), null);
            mListItemHolder.findView(convertView);
            convertView.setTag(mListItemHolder);
        } else {
            mListItemHolder = (BaseListItemHolder) convertView.getTag();
        }

        if (mListResource != null && position < mListResource.size()) {

            mListItemHolder.setViewResource(mListResource.get(position));
        }

        return convertView;
    }

}
