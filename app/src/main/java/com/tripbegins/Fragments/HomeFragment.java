package com.tripbegins.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripbegins.Constants.TripConstants;
import com.tripbegins.Listener.OnFragmentInteractionListener;
import com.tripbegins.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    ViewPager pager;
    HomePagerAdapter adapter;
    int currentPage = 0;
    List<String> imagesList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeUI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initializeUI() {
        pager = (ViewPager) view.findViewById(R.id.pager_home);
        adapter = new HomePagerAdapter(getFragmentManager());
        getImages();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_indicator);
        tabLayout.setupWithViewPager(pager, true);

//        TODO : add ripple effect for buttons

        view.findViewById(R.id.layout_visa).setOnClickListener(this);
        view.findViewById(R.id.layout_okay).setOnClickListener(this);
        view.findViewById(R.id.layout_track).setOnClickListener(this);
        view.findViewById(R.id.layout_about).setOnClickListener(this);
        view.findViewById(R.id.layout_contact).setOnClickListener(this);
        setupBannerImages();


    }

    private void setupBannerImages() {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == imagesList.size()) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 60000);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.setTitle("Trip Begins");
            mListener.shouldShowTitle(true);
        }
    }

    private void getImages() {
        if (imagesList.isEmpty()) {
            imagesList.add("file:///android_asset/dubai.jpg");
            imagesList.add("file:///android_asset/singapore.jpg");
            imagesList.add("file:///android_asset/dubai.jpg");
        }
    }

    @Override
    public void onClick(View view) {
        String selected = "";
        switch (view.getId()) {
            case R.id.layout_okay:
                selected = TripConstants.OK_TO_BOARD;
                break;
            case R.id.layout_contact:
                selected = TripConstants.CONTACT_US;
                break;
            case R.id.layout_visa:
                selected = TripConstants.VISA_SERVICES;
                break;
            case R.id.layout_track:
                selected = TripConstants.TRACK_APPLICATION;
                break;
            case R.id.layout_about:
                selected = TripConstants.ABOUT_US;
                break;
        }
        if (mListener != null) {
            mListener.homeOptionsSelected(selected);
        }
    }

    private class HomePagerAdapter extends FragmentStatePagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BannerFragment.newInstance(imagesList.get(position));
        }

        @Override
        public int getCount() {
            return imagesList.size();
        }
    }

}
