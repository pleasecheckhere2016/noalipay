package com.ctrun.alipayhome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView mTopBar1;
    private ImageView mTopBar2;
    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopBar1 = (ImageView) findViewById(R.id.topBar1);
        mTopBar2 = (ImageView) findViewById(R.id.topBar2);
        mListView = (ListView) findViewById(R.id.listView);

        initHead();

        ArrayList<Integer> titles = new ArrayList<>();
        titles.add(R.drawable.main333);
        titles.add(R.drawable.main444);
        titles.add(R.drawable.main777);

        mAdapter = new MyAdapter(titles);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mScrollListener);
    }

    private View mMenu1;
    private View mMenu1Mask;
    private ImageView footerImg;
    private void initHead() {
        View head = getLayoutInflater().inflate(R.layout.home_head, null);
        mMenu1 = head.findViewById(R.id.menu1);
        footerImg = (ImageView) findViewById(R.id.footerImg);
        footerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),MyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        mMenu1Mask = head.findViewById(R.id.menu1_mask);
        mListView.addHeaderView(head);

        head.findViewById(R.id.menu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ZhuanZhang1Activity.class);
                startActivity(intent);
            }
        });

    }

    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {
        private int mHeadScrollY;
        private boolean mMenu1Visible = true;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {//手指抛动时,即手指用力滑动,在离开后ListView由于惯性继续滑动的状态.
                Log.d("onScrollStateChanged", "fling mHeadScrollY="+mHeadScrollY);
            } else if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                Log.d("onScrollStateChanged", "idle mHeadScrollY="+mHeadScrollY);

                final int height = mMenu1.getHeight();
                final int halfHeight = height / 2;

                if(mHeadScrollY > 0 && mMenu1Visible) {
                    final int distance;
                    if(mHeadScrollY > halfHeight) {
                        distance = height - mHeadScrollY;
                    } else {
                        distance = -mHeadScrollY;
                    }

                    mListView.postOnAnimation(new Runnable() {
                        @Override
                        public void run() {
                            mListView.smoothScrollBy(distance, 300);
                        }
                    });
                }

            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(firstVisibleItem == 0) {
                final View firstItemView = view.getChildAt(0);
                if (firstItemView == null) {
                    return;
                }

                mHeadScrollY = -firstItemView.getTop() + view.getPaddingTop();

                final int height = mMenu1.getHeight();

                if(mMenu1Visible) {
                    final int offsetHeight = Math.min(mHeadScrollY, height);//[0-height]
                    final float ratio = 1.f * offsetHeight / height;//[0-1]
                    final float topBar1Alpha = 1 - Math.min(1, ratio / 0.2f);
                    final float topBar2Alpha = Math.max((ratio - 0.2f) / 0.8f, 0);
                    final float menu1MaskAlpha = Math.min(255, ratio / 0.75f * 255);//[0-255]
                    final int menu1MaskBGColor = Color.argb((int) menu1MaskAlpha, 27, 130, 210);//透明渐变到蓝色

                    mTopBar1.setAlpha(topBar1Alpha);
                    mTopBar2.setAlpha(topBar2Alpha);

                    mMenu1.setTranslationY(offsetHeight / 2);
                    mMenu1Mask.setBackgroundColor(menu1MaskBGColor);
                }

                mMenu1Visible = mHeadScrollY < height;

                Log.d("onScroll", "mHeadScrollY="+mHeadScrollY);
            }
        }
    };

    private static class MyAdapter extends BaseAdapter {

        private List<Integer> ids;

        public MyAdapter(List<Integer> ids) {
            this.ids = ids;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
                holder = new ItemViewHolder(convertView);
            } else {
                holder = (ItemViewHolder) convertView.getTag();
            }

            holder.imageView.setImageResource(ids.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return ids == null ? 0 : ids.size();
        }

        @Override
        public Integer getItem(int position) {
            return ids.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    static class ItemViewHolder {

        ImageView imageView;

        public ItemViewHolder(View itemView) {
            itemView.setTag(this);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
