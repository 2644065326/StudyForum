package com.hbsf.home.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hbsf.home.R;


public class NewsChannelFragment extends Fragment {
    private String type = "news";
    public static NewsChannelFragment newInstance(String channelId, String channelName) {
        Bundle args = new Bundle();
        args.putString("id", channelId);
        args.putString("name", channelName);
        NewsChannelFragment fragment = new NewsChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel,container,false);
        return view;
    }



}
