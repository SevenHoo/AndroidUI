package com.github.seven.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Description: TODO <br/>
 * Date: 2017/1/11 <br/>
 *
 * @author seven.hu@ubtrobot.com
 */

public class PageRecyclerView extends RecyclerView {

    private static final String TAG = PageRecyclerView.class.getSimpleName();

    private int mTotalPages;
    private int mCurrentPage;
    private int mPageColumn;
    private int mPageRow;

    /**
     * 超过此距离，实现翻页
     */
    private int mFlipDistance;

    /**
     *  手指按下的X轴坐标
     */
    private float mDownX;
    /**
     * 滑动的距离
     */
    private float mSlideDistance;
    /**
     * X轴当前的位置
     */
    private float mScrollDistance;

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PageRecyclerView(Context context) {
        this(context,null);
    }

    public void setPageSize(int row, int column){

        this.mPageColumn = column;
        this.mPageRow = row;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        mFlipDistance = getMeasuredWidth() / 4;

        Log.d(TAG,"mFlipDistance = " + mFlipDistance);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        mCurrentPage = 0;
        mTotalPages = ((int) Math.ceil(adapter.getItemCount() / (double) (mPageRow * mPageColumn)));
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        mScrollDistance = mScrollDistance + dx;

        Log.d(TAG,"onScrolled = " + dx + " mScrollDistance = " + mScrollDistance);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mDownX = e.getX();
                mScrollDistance = 0;
                Log.d(TAG,"ACTION_DOWN = " + mDownX);
                break;

            case MotionEvent.ACTION_UP:
                mSlideDistance = e.getX() - mDownX;

                Log.d(TAG,"ACTION_UP = " + e.getX());
                Log.d(TAG,"ACTION_UP mSlideDistance = " + mSlideDistance);
                Log.d(TAG,"ACTION_UP mScrollDistance = " + mScrollDistance);

                int scrollDistance = 0;


                if (Math.abs(mSlideDistance) > mFlipDistance) {

                    //mScrollDistance = 0;

                    //向右滑动(from -> to)，上一页，mScrollDistance < 0
                    if (mSlideDistance > 0) {
                        if(mCurrentPage != 0 ){
                            mCurrentPage = mCurrentPage - 1;
                            scrollDistance = (int)(Math.abs(mScrollDistance) - getWidth());
                            Log.d(TAG,"last Page --> getWidth = " + getWidth());
                        }
                        else{
                            mScrollDistance = 0;
                        }
                    }

                    //向左滑动(from -> to)，下一页，mScrollDistance > 0
                    else {
                        if(mCurrentPage != mTotalPages - 1){
                            mCurrentPage = mCurrentPage + 1;
                            scrollDistance = (int) (getWidth() - mScrollDistance);
                            Log.d(TAG,"next Page --> getWidth = " + getWidth());
                        }
                        else {
                            mScrollDistance = 0;
                        }
                    }
                }

                else{
                    scrollDistance = (int)-mScrollDistance;
                    Log.d(TAG,"-- scrollDistance = " + scrollDistance);
                }

                Log.d(TAG,"scrollDistance = " + scrollDistance);

                mScrollDistance = 0;
                smoothScrollBy(scrollDistance, 0);

                return true;

            default:
                break;
        }
        return super.onTouchEvent(e);
    }
}
