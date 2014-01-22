package com.fuzz.simpleapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	static String WEB_PAGE    = "webpage activity";

	SectionsPagerAdapter mSectionsPagerAdapter;
	static public Context thisMain;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisMain = this;
        
        // Get JSON data 
        getData();

        // Test ---------
        Model.LoadModel();
        
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        Tab tabAll =   actionBar.newTab();
        tabAll.setText(mSectionsPagerAdapter.getPageTitle(SectionFragment.ALL_ITEMS));
        tabAll.setTabListener(this);
        
        Tab tabText =  actionBar.newTab();
        tabText.setText(mSectionsPagerAdapter.getPageTitle(SectionFragment.TEXT_ONLY));
        tabText.setTabListener(this);
        
        Tab tabImage = actionBar.newTab();
        tabImage.setText(mSectionsPagerAdapter.getPageTitle(SectionFragment.IMAGE_ONLY));
        tabImage.setTabListener(this);
        
        ArrayList<String> textList = new ArrayList<String>();

        actionBar.addTab(tabAll);
        actionBar.addTab(tabText);
        actionBar.addTab(tabImage);

    } // onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
    	
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new SectionFragment();
            Bundle args = new Bundle();
            args.putInt(SectionFragment.ARG_SECTION_NUMBER, position );
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case SectionFragment.ALL_ITEMS:
                    return getString(R.string.title_all).toUpperCase(l);
                case SectionFragment.TEXT_ONLY:
                    return getString(R.string.title_text).toUpperCase(l);
                case SectionFragment.IMAGE_ONLY:
                    return getString(R.string.title_image).toUpperCase(l);
            }
            return null;
        }
    }
    
    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class SectionFragment extends Fragment {
    	static final public int ALL_ITEMS  = 0;
    	static final public int TEXT_ONLY  = 1;
    	static final public int IMAGE_ONLY = 2;
    	
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
    	int tabPos;
        public static final String ARG_SECTION_NUMBER = "section_number";

        Context mainThisLoc;
        
        public SectionFragment() {

        	try {
        		// wait for data to be retrieved 
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            
            int tabType = getArguments().getInt(ARG_SECTION_NUMBER);
            // dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            // dummyTextView.setText(getArguments().getString(ARG_SECTION_NUMBER));
            
            if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, setOnItemSelectedListener, tab: " + tabType);
            
            String[] ids = new String[Model.Items.size()];
            
            // Test ----------
            for (int i= 0; i < ids.length; i++){

                ids[i] = Integer.toString(i+1);
            }
            // Test ------
            
            switch(tabType) {
            case ALL_ITEMS:
                ListView listV1 = new ListView(getActivity() );
    	        listV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1,
    						int arg2, long arg3) {
    					Toast toast = Toast.makeText(getActivity(), "All List"+ (arg2+1) +"!", Toast.LENGTH_LONG);
    					toast.show();					
    					((MainActivity)thisMain).callUrl("http://www.fuzzproductions.com/");
    				}
    	        }); 

    	        AllItemsAdapter allAdapter = new AllItemsAdapter(getActivity(),R.layout.row, ids);
                listV1.setAdapter(allAdapter);
                
                rootView.addView(listV1);

    	        break;
            case TEXT_ONLY:
                ListView listV2 = new ListView(getActivity() );
    	        listV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1,
    						int arg2, long arg3) {
    					Toast toast = Toast.makeText(getActivity(), "Text List"+ (arg2+1) +"!", Toast.LENGTH_LONG);
    					toast.show();					
    					((MainActivity)thisMain).callUrl("http://www.fuzzproductions.com/");
    				}
    	        });

                listV2.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, 
                		((MainActivity)thisMain).getTextTypes()) ); // Model.GetNameList()));
                rootView.addView(listV2);

    	        break;
            case IMAGE_ONLY: 

                ListView listV3 = new ListView(getActivity() );
    	        listV3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1,
    						int arg2, long arg3) {
    					Toast toast = Toast.makeText(getActivity(), "Image List"+ (arg2+1) +"!", Toast.LENGTH_LONG);
    					toast.show();	
    					((MainActivity)thisMain).callUrl("http://www.fuzzproductions.com/");
    				}
    	        }); 

    	        listV3.setAdapter( new ImageOnlyAdapter(getActivity(),R.layout.row, ids));
                rootView.addView(listV3);

    	        break;
            }
                        
            
            return (View)rootView;
        }
    }
    

    public void callUrl(String url) {
    	try {
    		  Intent intent = new Intent(this, WebActivity.class);
    		  intent.putExtra(MainActivity.WEB_PAGE, url);
    		  startActivity(intent);
    		  
    		} catch (ActivityNotFoundException e) {
    		  Toast.makeText(this, "Web application not found."
    		    + " Please configure a webbrowser",  Toast.LENGTH_LONG).show();
    		  e.printStackTrace();
    		}
    }
    
    
	static String FUZZ_TEST_DATA = "http://dev.fuzzproductions.com/MobileTest/test.json";
	static String RECORD_ID   = "id";
	static String RECORD_TYPE = "type";
	static String RECORD_DATA = "data";

    JsonParser jsonParser = new JsonParser();
    JSONArray sample = null;
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    ArrayList<HashMap<String, String>> sampleList;
    
	private void getData() {

	    sampleList = new ArrayList<HashMap<String, String>>();

	    new GetSampleData().execute();

	}
	
	
    class GetSampleData extends AsyncTask<String, String, String> {
    	
        protected String doInBackground(String... args) {
        	// getting JSON string from URL
		    JSONObject json = jsonParser.makeHttpRequest(FUZZ_TEST_DATA, "GET",
		            params);
		
		    // Check your log cat for JSON reponse
		    Log.d("sample JSON: ", json.toString());
		
		    try {
		        sample = json.getJSONArray("sampledata");
		        // looping through All messages
		        for (int i = 0; i < sample.length(); i++) {
		            JSONObject c = sample.getJSONObject(i);
		
		            // Storing each json item in variable
		            String id = c.getString(RECORD_ID);
		            String type = c.getString(RECORD_TYPE);
		            String data = c.getString(RECORD_DATA);
		
		            // creating new HashMap
		            HashMap<String, String> map = new HashMap<String, String>();
		
		            // adding each child node to HashMap key => value
		            map.put(RECORD_ID, id);
		            map.put(RECORD_TYPE, type);
		            map.put(RECORD_DATA, data);
		
		            // adding HashList to ArrayList
		            sampleList.add(map);
		        }
		
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
		    
		    return null;
	     }
    } // class GetSampleData extends AsyncTask
    
    
    private ArrayList<String> getTextTypes() {
    	ArrayList<String> textArray = new ArrayList<String>();
    	
    	for(HashMap<String, String> typeHM : sampleList) {
    		if( typeHM.get("type") != null &&  typeHM.get("type").equals("text")) {
    			textArray.add(typeHM.get("data"));
    		}
    	}
       	
       	if(GlobalSettings.mainActivity) Log.d("MainActivity", "getTexTypes: "+ textArray);
       	
    	return textArray;
    }
    
    
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
