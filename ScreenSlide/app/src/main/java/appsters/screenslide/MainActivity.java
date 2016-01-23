package appsters.screenslide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {


    private static final String TAG = "sitemessage";
    String s1="",s2="",s3="",s4="",s5="",s6="",s7="";


    TextView textView;
    RequestQueue requestQueue;
    String url = "http://agni.iitd.ernet.in/cop290/assign0/register/";
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    public static final int NUM_PAGES = 4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);

        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });

       }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "submit" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, ((mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.id.action_submit
                        : R.id.action_next), Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_submit
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                if(mPager.getCurrentItem()==0){
                    s1 = ((EditText) findViewById(R.id.team)).getText().toString();
                }else if(mPager.getCurrentItem()==1){
                    s2 = ((EditText) findViewById(R.id.name1)).getText().toString();
                    s3 = ((EditText) findViewById(R.id.entry1)).getText().toString();
                }else if(mPager.getCurrentItem()==2){
                    s4 = ((EditText) findViewById(R.id.name2)).getText().toString();
                    s5 = ((EditText) findViewById(R.id.entry2)).getText().toString();
                }
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;

            case R.id.action_submit:
                s6 = ((EditText) findViewById(R.id.name3)).getText().toString();
                s7 = ((EditText) findViewById(R.id.entry3)).getText().toString();
                textView = ((TextView) findViewById(R.id.t8));


                //Submit the info after all the credentials are correctly filled

                //instantiate the RequestQueue
                requestQueue = Volley.newRequestQueue(this);

                //request a string response from provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Display the first 50 characters of the response string.
                        //textView.setText("Response is :" + response.substring(45, 66));
                        String s = response.toString();
                        Log.i(TAG, "Response is : " + s);
                        String[] y = s.split(" ");

                        if(y[1].equals("1,")){
                            textView.setText(R.string.final_alert_1);
                        }
                        else if ((y[1].equals("0,")) && y[4].equals("already")){

                            textView.setText(R.string.final_alert_2);
                        }
                        else {

                            textView.setText(R.string.final_alert_3);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("teamname", s1);
                        params.put("entry1", s3);
                        params.put("name1", s2);
                        params.put("entry2", s5);
                        params.put("name2", s4);
                        params.put("entry3", s7);
                        params.put("name3", s6);

                        return params;
                    }
                };

                //Add the request to the RequestQueue
                requestQueue.add(stringRequest);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
