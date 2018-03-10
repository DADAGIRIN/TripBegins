package com.tripbegins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tripbegins.Constants.TripConstants;
import com.tripbegins.Fragments.AboutUsFragment;
import com.tripbegins.Fragments.ContactUsFragment;
import com.tripbegins.Fragments.HomeFragment;
import com.tripbegins.Fragments.OkayToBoardFragment;
import com.tripbegins.Fragments.PassengerListEntryFragment;
import com.tripbegins.Fragments.TrackApplicationFragment;
import com.tripbegins.Fragments.VisaServicesFragment;
import com.tripbegins.Listener.OnFragmentInteractionListener;

public class HomeActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        loadHomeFragment();
    }

    private void loadHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, new HomeFragment(), "HomeFragment")
                .commit();
    }

    @Override
    public void homeOptionsSelected(String option) {
        switch (option) {
            case TripConstants.VISA_SERVICES:
                onVisaServices();
                break;
            case TripConstants.OK_TO_BOARD:
                onOkToBoard();
                break;
            case TripConstants.TRACK_APPLICATION:
                onTrackApplication();
                break;
            case TripConstants.ABOUT_US:
                onAboutUs();
                break;
            case TripConstants.CONTACT_US:
                onContactUs();
                break;
        }
    }

//    TODO : add animation for fragment transition
    private void onTrackApplication() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new TrackApplicationFragment(), "TrackApplicationFragment")
                .addToBackStack("TrackApplicationFragment")
                .commit();
    }

    private void onVisaServices() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new VisaServicesFragment(), "VisaServicesFragment")
                .addToBackStack("VisaServicesFragment")
                .commit();
    }

    private void onOkToBoard() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new OkayToBoardFragment(), "OkayToBoardFragment")
                .addToBackStack("OkayToBoardFragment")
                .commit();
    }

    private void onAboutUs() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new AboutUsFragment(), "AboutUsFragment")
                .addToBackStack("AboutUsFragment")
                .commit();
    }

    private void onContactUs() {
       /* getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new ContactUsFragment(), "ContactUsFragment")
                .addToBackStack("ContactUsFragment")
                .commit();*/

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new PassengerListEntryFragment(), "PassengerEntryContainerFragment")
                .addToBackStack("PassengerEntryContainerFragment")
                .commit();
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void shouldShowTitle(boolean show) {
        tvTitle.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
