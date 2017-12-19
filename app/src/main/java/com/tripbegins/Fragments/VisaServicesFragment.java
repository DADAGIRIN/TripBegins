package com.tripbegins.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripbegins.Data.Country;
import com.tripbegins.Data.VisaServiceResponse;
import com.tripbegins.Data.VisaServices;
import com.tripbegins.Listener.OnFragmentInteractionListener;
import com.tripbegins.Listener.OnVisaServiceSelect;
import com.tripbegins.Network.ApiClient;
import com.tripbegins.Network.ApiInterface;
import com.tripbegins.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisaServicesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View view;
    private ViewPager pager;
    private RecyclerView rvVisaOptions;
    private VisaOptionsAdapter adapter;
    private CountryPagerAdapter pagerAdapter;
    private ProgressDialog loading;
    private List<VisaServices> visaServicesList = new ArrayList<>();
    private List<VisaServices> currentVisaServicesList = new ArrayList<>();
    private List<Country> countryList = new ArrayList<>();
    private Country selectedCountry;

    public VisaServicesFragment() {
        // Required empty public constructor
    }

    public static VisaServicesFragment newInstance() {
        VisaServicesFragment fragment = new VisaServicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visa, container, false);
        initialize();
        return view;
    }

    private void initialize() {

        pager = (ViewPager) view.findViewById(R.id.pager_country);
        rvVisaOptions = (RecyclerView) view.findViewById(R.id.rv_visa_options);

        adapter = new VisaOptionsAdapter(new OnVisaServiceSelect() {
            @Override
            public void onOptionSelect(VisaServices data) {
// yet to implement
            }
        });

        rvVisaOptions.setAdapter(adapter);
        rvVisaOptions.setLayoutManager(new LinearLayoutManager(getContext()));

        pagerAdapter = new CountryPagerAdapter(getFragmentManager());
        pager.addOnPageChangeListener(new CountrySelectListener());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);

        pager.setClipToPadding(false);
        pager.setPadding((int) getResources().getDimension(R.dimen._50dp)
                ,(int) getResources().getDimension(R.dimen._20dp)
                ,(int) getResources().getDimension(R.dimen._50dp)
                ,(int) getResources().getDimension(R.dimen._20dp));
        pager.setPageMargin((int) getResources().getDimension(R.dimen._20dp));

        getVisaServices();
    }

    private void getVisaServices() {

        loading = ProgressDialog.show(getContext(), "", "Loading...", false);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<VisaServiceResponse> call = apiInterface.getVisaServices();
        call.enqueue(new Callback<VisaServiceResponse>() {
            @Override
            public void onResponse(Call<VisaServiceResponse> call, Response<VisaServiceResponse> response) {
                Log.v("getVisaService Response", response.toString());
                countryList = response.body().country;
                countryList.addAll(response.body().country);
                visaServicesList = response.body().visa;
                pagerAdapter.notifyDataSetChanged();
                selectedCountry = countryList.get(0);
                getVisaServicesForSelectedCountry();
                dismissLoading();
            }

            @Override
            public void onFailure(Call<VisaServiceResponse> call, Throwable t) {
                Log.e("getVisaServiceError", t.toString());
                dismissLoading();
            }
        });
    }

    private void dismissLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
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

    public class VisaOptionsAdapter extends RecyclerView.Adapter<VisaOptionsAdapter.ViewHolder> {

        private OnVisaServiceSelect listener;

        public VisaOptionsAdapter(OnVisaServiceSelect listener) {
            this.listener = listener;
        }

        @Override
        public VisaOptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VisaOptionsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_visa_service_options, parent, false));
        }

        @Override
        public void onBindViewHolder(final VisaOptionsAdapter.ViewHolder holder, int position) {

            holder.tvVisaService.setText(currentVisaServicesList.get(position).description);

            holder.tvVisaService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onOptionSelect(currentVisaServicesList.get(holder.getAdapterPosition()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return currentVisaServicesList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvVisaService;

            public ViewHolder(View itemView) {
                super(itemView);
                tvVisaService = (TextView) itemView.findViewById(R.id.tv_visa_service_option);
            }
        }
    }

    private class CountryPagerAdapter extends FragmentStatePagerAdapter {

        public CountryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String imageUrl = "file:///android_asset/singapore.png";
            String countryName = countryList.get(position).country;
            return BannerFragment.newInstance(imageUrl, countryName);
        }

        @Override
        public int getCount() {
            return countryList.size();
        }
    }

    private class CountrySelectListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            selectedCountry = countryList.get(position);
            getVisaServicesForSelectedCountry();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private void getVisaServicesForSelectedCountry() {

        if (!currentVisaServicesList.isEmpty()) {
            currentVisaServicesList.clear();
        }

        String countryID = selectedCountry.id;

        for (VisaServices services : visaServicesList) {
            if (services.country_id.equalsIgnoreCase(countryID)) {
                currentVisaServicesList.add(services);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
