package com.dm.coordinatedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author FT
 * @time 2016-07-18 16:49
 */

// JUMP 
public class DemoFragment extends Fragment {
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private Context context;
    private int mPage;
    public static final String MERCHANT_DETAILS_PAGE = "MERCHANT_DETAILS_PAGE";
    private DemoAdapter mAdapter;

    List<String> mData;

    public static DemoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MERCHANT_DETAILS_PAGE, page);
        DemoFragment tripFragment = new DemoFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(MERCHANT_DETAILS_PAGE);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this, view);
        mData = new ArrayList<>();
        for(int i=0; i<30;i++){
            mData.add("标题"+i);
        }
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new DemoAdapter(getActivity(), R.layout.item_ification_class, mData);
        initAdapter();
        return view;
    }
    /**
     * 设置RecyclerView属性
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);//设置adapter
        //设置item点击事件
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }
    /**
     * 商家详情adapter
     */
    class DemoAdapter extends BaseQuickAdapter<String> {
        int mLayoutId;
        public DemoAdapter(Context context, int LayoutId, List<String> str) {
            super(context, LayoutId, str);
            mLayoutId = LayoutId;
        }

        @Override
        public void convert(BaseViewHolder helper, String str) {
            helper.setText(R.id.tvTitle,str);
        }
    }

}
