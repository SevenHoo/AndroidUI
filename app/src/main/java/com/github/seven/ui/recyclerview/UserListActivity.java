package com.github.seven.ui.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.github.seven.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO <br/>
 * Date: 2017/1/9 <br/>
 *
 * @author seven.hu@ubtrobot.com
 */

public class UserListActivity extends Activity implements UserInfoPageAdapter.OnItemClickListener, UserInfoPageAdapter.OnItemLongClickListener{

    private static final String TAG = UserListActivity.class.getSimpleName();

    private static final int USER_INFO_COLUMN = 2;

    private PageRecyclerView mUserRecyclerView;

    private List<String> mData;
    private UserInfoPageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list);

        mUserRecyclerView = (PageRecyclerView) findViewById(R.id.user_list_recycler_view);
        initData();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,USER_INFO_COLUMN, GridLayoutManager.HORIZONTAL,false);

        mUserRecyclerView.setLayoutManager(gridLayoutManager);
//        mUserRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(USER_INFO_COLUMN,
//                StaggeredGridLayoutManager.HORIZONTAL));

        //去除边界的蓝色阴影
        mUserRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mUserRecyclerView.setPageSize(4,2);

        mAdapter = new UserInfoPageAdapter(this);
        mAdapter.setDataSource(mData);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);

        mUserRecyclerView.setAdapter(mAdapter);
        mUserRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

    }

    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mData.add("" + (char) i);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"click: " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this,"Long click: " + position,Toast.LENGTH_SHORT).show();
    }
}
