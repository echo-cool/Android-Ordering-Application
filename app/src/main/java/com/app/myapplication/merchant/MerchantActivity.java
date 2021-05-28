package com.app.myapplication.merchant;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.beans.MerchantBean;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;
import com.app.myapplication.adapters.MerchantAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MerchantActivity extends AppCompatActivity {

    MerchantAdapter mctAdapter;
    RecyclerView mctView;
    List<MerchantBean> temp = new LinkedList<>();
    Boolean isFin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        mctView = findViewById(R.id.merchant_recycle);
        mctView.setLayoutManager(new LinearLayoutManager(this));
        mctView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 15;
            }
        });


        mctAdapter = new MerchantAdapter(new LinkedList<>());
        mctView.setAdapter(mctAdapter);

        mctAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MerchantActivity.this , ShopActivity.class);
                Bundle b = new Bundle();
                MerchantBean  mb=  mctAdapter.getList().get(position);
                b.putString("name",mb.mctName);
                b.putString("id",mb.id);
                i.putExtras(b);
                startActivity(i);
            }
        });

        mctAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!isFin) {
                            if (temp == null) {
                                mctAdapter.loadMoreFail();
                            } else {
                                mctAdapter.addData(temp);
                                mctAdapter.loadMoreComplete();
                                temp = null;
                            }
                        }else {
                            mctAdapter.addData(temp);
                            mctAdapter.loadMoreEnd();
                        }
                    }
                }, 3000);
            }
        }, mctView);
        //test();
    }

    public void load(List<MerchantBean> mct){
        mctAdapter.addData(mct);
    }
    public void loadMore(List<MerchantBean> mct){
        //加入更多数据，list末尾为null判断所有数据加载完成
        if(mct. get(mct.size()-1) ==null){
            isFin = true;
            mct.remove(mct.size()-1);
            for(MerchantBean mb:mct){
                temp.add(mb);
            }
        }else {
            for (MerchantBean mb : mct) {
                temp.add(mb);
            }
        }
    }



    private void test(){
        List<MerchantBean> mct =new LinkedList<>();
        mct.add(new MerchantBean("111","好吃不贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        load(mct);
        List<MerchantBean> mct1 =new LinkedList<>();
        mct1.add(new MerchantBean("111","好吃不贵"));
        mct1.add(new MerchantBean("111","好吃不贵"));
        mct1.add(new MerchantBean("111","好吃不贵"));
        mct1.add(new MerchantBean("111","好吃不贵"));
        loadMore(mct1);
        List<MerchantBean> mct2 =new LinkedList<>();
        mct2.add(new MerchantBean("333","怎么能这么难吃"));
        mct2.add(null);
        loadMore(mct2);
    }
}