package com.tripbegins.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tripbegins.R;

public class BannerFragment extends Fragment {

    View view;
    ImageView imageView;
    TextView tvTitle;

    public BannerFragment() {
        //required empty constructor
    }

    public static BannerFragment newInstance(String url) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    public static BannerFragment newInstance(String url, String title) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        initializeUI();
        return view;
    }

    private void initializeUI() {
        imageView = (ImageView) view.findViewById(R.id.iv_packages);
        tvTitle = (TextView) view.findViewById(R.id.tv_country_name);

        String url = "", title = "";
        if (getArguments() != null) {
            if (getArguments().containsKey("url")) {
                url = getArguments().getString("url");
            }
            if (getArguments().containsKey("title")) {
                title = getArguments().getString("title");
            }
        }

        if (title.length() > 0) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }

        Glide.with(this).load(Uri.parse(url)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

    }

}
