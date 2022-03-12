package com.hbsf.home.view;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.base.view.BaseActivity;
import com.hbsf.common.view.SFViewPager;
import com.hbsf.home.R;
import com.hbsf.home.view.fragment.CommunityFragment;
import com.hbsf.home.view.news.NewsFragment;
import com.hbsf.home.view.personal.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

@ARouter(path = "/home/HomeActivity")
public class HomeActivity extends BaseActivity {
    private SFViewPager viewPager;
    private FragmentManager fragmentManager;
    private LinearLayout mBtnNews;
    private LinearLayout mBtnCommunity;
    private LinearLayout mBtnPersonal;
    private List<LinearLayout> bottomBtnlist;
    private int currentFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        //初始化3个按钮，绑定布局文件的按钮控件并实现其点击事件
        mBtnInit();
        //初始化viewPage
        mVPInit();

        //设置viewPager的起始页面和按钮的起始状态
        setFirstFragment();
    }

    private void setFirstFragment() {
        viewPager.setCurrentItem(0);

        for (LinearLayout btn : bottomBtnlist) {
            if (btn == null) continue;
            if (btn.getId() == R.id.news_button) {
                changeBtnState(btn, true);

                currentFragment = 0;
            } else {
                changeBtnState(btn, false);
            }
        }
    }

    private void mVPInit() {
        //绑定viewPager并获得fragmentManager实例
        viewPager = findViewById(R.id.fragment_view_pager);
        fragmentManager = getSupportFragmentManager();

        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(2);
        //设置viewPager的Adapter，得到当前的Fragment页面
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return createFragment(position);
            }

            @Override
            public int getCount() {
                return bottomBtnlist.size();
            }
        });


        //设置viewPager的界面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                clearAllBtnStatus();

                switch (position){
                    case 0:
                        changeBtnState(mBtnNews, true);
                        currentFragment = 0;
                        break;
                    case 1:
                        changeBtnState(mBtnCommunity, true);
                        currentFragment = 1;
                        break;
                    case 2:
                        changeBtnState(mBtnPersonal, true);
                        currentFragment = 2;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化3个按钮，绑定布局文件的按钮控件并实现其点击事件
    private void mBtnInit() {
        bottomBtnlist = new ArrayList<>();
        mBtnNews = (LinearLayout)findViewById(R.id.news_button);
        mBtnCommunity = (LinearLayout)findViewById(R.id.community_button);
        mBtnPersonal = (LinearLayout)findViewById(R.id.personal_button);
        bottomBtnlist.add(mBtnNews);
        bottomBtnlist.add(mBtnCommunity);
        bottomBtnlist.add(mBtnPersonal);
        addBottomBtnClickListener();
        initBtnText();
    }

    private void addBottomBtnClickListener() {
        mBtnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, false);
            }
        });
        mBtnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, false);
            }
        });
        mBtnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2, false);
            }
        });
    }

    private void initBtnText() {
        TextView tv;
        for (LinearLayout btn : bottomBtnlist) {
            if (btn == null) continue;
            tv = btn.findViewById(R.id.item_text);
            int id = btn.getId();
            if (id == R.id.news_button) {
                tv.setText(R.string.bottom_news_text);

            } else if (id == R.id.community_button) {
                tv.setText(R.string.bottom_community_text);

            } else if (id == R.id.personal_button) {

                tv.setText(R.string.bottom_personal_text);
            }
        }
    }

    //用于清除按钮的背景颜色状态
    private void clearAllBtnStatus() {
        for (LinearLayout btn : bottomBtnlist) {
            if (btn == null) continue;
            changeBtnState(btn, false);
        }
    }

    private  Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new NewsFragment();
            case 1:
                return new CommunityFragment();
            case 2:
                return new PersonalFragment();
            default:
                break;
        }
        return null;
    }

    private void changeBtnState(LinearLayout btn, boolean isSelect) {
        if (btn == null) return;
        int id = btn.getId();
        if (id == R.id.news_button) {
            switchBtnState(btn, R.drawable.news_select, R.drawable.news_normal, isSelect);

        } else if (id == R.id.community_button) {
            switchBtnState(btn, R.drawable.community_select, R.drawable.community_normal, isSelect);

        } else if (id == R.id.personal_button) {

            switchBtnState(btn, R.drawable.personal_select, R.drawable.personal_normal, isSelect);
        }

    }

    private void switchBtnState(LinearLayout btn, @DrawableRes int selectRes, @DrawableRes int normalRes,boolean isSelect) {
        int id = btn.getId();
        ImageView imageView = btn.findViewById(R.id.item_img);
        TextView textView = btn.findViewById(R.id.item_text);
        if (imageView == null || textView == null) return;
        if (isSelect) {
            imageView.setImageResource(selectRes);
            textView.setTextColor(getSelectColor(R.color.bottom_item_select_color));
        } else {
            imageView.setImageResource(normalRes);
            textView.setTextColor(getSelectColor(R.color.bottom_item_normal_color));
        }
    }

    public int getSelectColor(@ColorRes int color) {
        return this.getResources().getColor(color);
    }
}