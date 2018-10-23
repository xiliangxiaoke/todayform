package com.today.todayfarm.pages.main;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.today.todayfarm.R;

import java.util.List;

public class MainPageGalleryPagerAdapter extends PagerAdapter {

    private List<Integer> mDataList;

    public MainPageGalleryPagerAdapter(List<Integer> mDataList) {
        this.mDataList = mDataList;
    }


    @Override
    public int getCount() {
        return mDataList == null ? 0: mDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(
                R.layout.viewpager_item_mainpic,null);
        ImageView imageView = view.findViewById(R.id.pic);
        imageView.setImageResource(mDataList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
