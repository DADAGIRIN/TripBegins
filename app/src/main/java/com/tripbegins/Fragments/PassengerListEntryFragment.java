package com.tripbegins.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tripbegins.Listener.OnFragmentInteractionListener;
import com.tripbegins.R;

/**
 * Created on 05/03/2018.
 * Passenger list entry
 */

public class PassengerListEntryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView rvPassengerList;
    private TextView tvPassengerCountry;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_passenger_list_entry, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        rvPassengerList = (RecyclerView) view.findViewById(R.id.rv_passenger_list_container);
        tvPassengerCountry = (TextView) view.findViewById(R.id.tv_passenger_country);
        tvPassengerCountry.setText("Malaysia");

        rvPassengerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPassengerList.setAdapter(new PassengerListEntryAdapter());
    }

    private class PassengerListEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == 1) {
                return new PassengerSubmitBtn(LayoutInflater.from(getActivity()).inflate(R.layout.passenger_submit_btn, parent, false));
            } else {
                return new PassengerEntryRow(LayoutInflater.from(getActivity()).inflate(R.layout.passenger_single_entry_row, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder.getItemViewType() == 1) {
                PassengerSubmitBtn passengerSubmitBtn = (PassengerSubmitBtn) holder;
                passengerSubmitBtn.btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                PassengerEntryRow passengerEntryRow = (PassengerEntryRow) holder;
                passengerEntryRow.tvPassengerTitle.setText("Passenger " + (position + 1));
            }

        }

        @Override
        public int getItemCount() {
            return 4;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 3) {
                return 1;
            } else {
                return 2;
            }
        }

        private class PassengerEntryRow extends RecyclerView.ViewHolder {

            ConstraintLayout clPassengerEntryContainer;
            TextView tvPassengerTitle;

            PassengerEntryRow(View itemView) {
                super(itemView);
                clPassengerEntryContainer = (ConstraintLayout) itemView.findViewById(R.id.cl_passenger_upload);
                tvPassengerTitle = (TextView) itemView.findViewById(R.id.tv_passenger_title);

                tvPassengerTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        private class PassengerSubmitBtn extends RecyclerView.ViewHolder {

            Button btnSubmit;

            PassengerSubmitBtn(View itemView) {
                super(itemView);
                btnSubmit = (Button) itemView.findViewById(R.id.btn_submit);
            }
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
