package com.android.slidingMenu;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hellotest.R;

/**
 * Created by andyliu on 2016/4/5.
 */
public class SlidingMenuFragment extends Fragment implements  View.OnClickListener{
    private TextView mlvTextV;
    private TextView mreTextV;
    private TextView mcuTextV;

    private View mContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = inflater.inflate(R.layout.left_menu_fragment, container, false);

        return mContainer;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mlvTextV = (TextView) mContainer.findViewById(R.id.lvTextV);
        mcuTextV = (TextView) mContainer.findViewById(R.id.cuTextV);
        mreTextV = (TextView) mContainer.findViewById(R.id.reTextV);

        mlvTextV.setOnClickListener(this);
        mcuTextV.setOnClickListener(this);
        mreTextV.setOnClickListener(this);

    }

    OnLoadMathSelectedListener mListener;
    public void setSelectedListener(OnLoadMathSelectedListener listener){
        mListener = listener;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.lvTextV:
                mListener.onLVSelected();
                break;
            case R.id.cuTextV:
                mListener.onCUSelected();
                break;
            case R.id.reTextV:
                mListener.onRESelected();
                break;
        }
    }

    public interface OnLoadMathSelectedListener{
        public void onLVSelected();
        public void onCUSelected();
        public void onRESelected();
    }
}
