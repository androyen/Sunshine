package com.androyen.sunshine;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //Launch SettingsActivity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        //Constant for #SunshineApp
        private final String HASHTAG_SUNSHINE = "#SunshineApp";

        private String mWeatherForecastString;
        private TextView forecastTextView;
        private ShareActionProvider mShareActionProvider;



        //Need to use menu item callback methods in this fragment
        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mWeatherForecastString = intent.getStringExtra(Intent.EXTRA_TEXT);

                forecastTextView = (TextView) rootView.findViewById(R.id.forecastTextView);
                forecastTextView.setText(mWeatherForecastString);


            }
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            //Inflate menu resource
            inflater.inflate(R.menu.detailfragment, menu);

            //Locate MenuItem with ShareActionProvider
            MenuItem item = menu.findItem(R.id.menu_share_action_provider);

            //Fetch and store ShareActionProvider
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            //Start the sharing intent
            //Null check ShareActionProvider
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(sharingIntent());
            }


        }

        //Helper method for sharing Intent
        private Intent sharingIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); //Prevent activity we are sharing to to be place into back stack
            //Send the forecast message in ShareActionProvider
            shareIntent.putExtra(Intent.EXTRA_TEXT, mWeatherForecastString + " " + HASHTAG_SUNSHINE);

            return shareIntent;
        }
    }
}
