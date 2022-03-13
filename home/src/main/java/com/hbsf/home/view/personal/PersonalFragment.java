package com.hbsf.home.view.personal;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbsf.arouter_api.manager.RouterManager;
import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.common.utils.UserUtils;
import com.hbsf.home.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends BaseMVPFragment implements View.OnClickListener{

    private ImageView settingBtn;
    private ImageView userIcon;
    private TextView userNameTV;
    private TextView userDescTV;
    private LinearLayout groupLY;
    private LinearLayout postsLY;
    private LinearLayout collectLY;
    private List<LinearLayout> actionViews;

    @Override
    protected void initView(View view) {
        settingBtn = view.findViewById(R.id.setting_btn);
        userIcon = view.findViewById(R.id.personal_icon);
        userNameTV = view.findViewById(R.id.user_name);
        userDescTV = view.findViewById(R.id.user_desc);
        initUserInfo();
        groupLY = view.findViewById(R.id.group);
        postsLY = view.findViewById(R.id.posts);
        collectLY = view.findViewById(R.id.collection);
        actionViews = new ArrayList<>();
        actionViews.add(groupLY);
        actionViews.add(postsLY);
        actionViews.add(collectLY);
        for (LinearLayout ly : actionViews) {
            initPersonalAction(ly);
        }

    }

    private void initUserInfo() {
        userNameTV.setText(UserUtils.getInstance().getUsername());
        userDescTV.setText(UserUtils.getInstance().getDesc());
        Glide.with(getContext())
                .load(R.drawable.test)
//                .load(UserUtils.getInstance().getIcon())
                .transition(withCrossFade())
                .into(userIcon);
    }

    private void initPersonalAction(View view) {
        if (view == null) return;
        ImageView img = view.findViewById(R.id.item_img);
        TextView tv = view.findViewById(R.id.item_text);
        if (view.getId() == R.id.group) {
            img.setImageResource(R.drawable.my_group);
            tv.setText("我的小组");
        } else if (view.getId() == R.id.posts) {
            img.setImageResource(R.drawable.my_posts);
            tv.setText("我的帖子");
        } else {
            img.setImageResource(R.drawable.my_collection);
            tv.setText("我的收藏");
        }
        view.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }


    @Override
    public void onClick(View v) {
        String type = null;
        if (v.getId() == R.id.group) {
            type = "group";
        } else if (v.getId() == R.id.posts) {
            type = "posts";
        } else {
            type = "collection";
        }

        RouterManager.getInstance()
                .build("/posts/MomentListActivity")
                .withString("userId", "11111")
                .withString("type", type)
                .navigation(getContext());
    }
}
