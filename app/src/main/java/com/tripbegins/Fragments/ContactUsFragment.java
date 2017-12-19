package com.tripbegins.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripbegins.Listener.OnFragmentInteractionListener;
import com.tripbegins.R;

public class ContactUsFragment extends Fragment {

    View view;
    private OnFragmentInteractionListener mListener;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initializeUI();
        return view;
    }

    private void initializeUI() {
        view.findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallClicked();
            }
        });

        view.findViewById(R.id.btn_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmailClicked();
            }
        });

        view.findViewById(R.id.iv_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.location))));
            }
        });
    }

    private void onEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822e");
        intent.putExtra(Intent.EXTRA_EMAIL, "info@tripbegins.com");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void onCallClicked() {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:+91 860 854 4441")));
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
