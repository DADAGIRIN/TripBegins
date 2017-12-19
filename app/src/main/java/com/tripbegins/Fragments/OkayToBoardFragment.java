package com.tripbegins.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripbegins.Listener.OnFragmentInteractionListener;
import com.tripbegins.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OkayToBoardFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public OkayToBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_okay_to_board, container, false);
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

    @Override
    public void onResume() {
        super.onResume();
        mListener.shouldShowTitle(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener.shouldShowTitle(true);
    }
}
