package com.fuzz.simpleapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// Leroy

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	static String WEB_PAGE    = "webpage activity";

    public ArrayList<HashMap<String, String>> sampleList=null;
    public Hashtable<Integer, Bitmap> imageHM=null;
    public ArrayList<String> textList = null;
    ArrayAdapter<String> textOnlyAdapter ; 

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

        textOnlyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
        		((MainActivity)thisMain).textList);

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
     * Display the list view of data: text images and both
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

        	// Get data before displaying
        }

        private AdapterView.OnItemClickListener goToFuzz = new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, android.view.View arg1,
					int arg2, long arg3) {
                if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, OnItemClick Clicked , ALL_ITEMS");
				((MainActivity)thisMain).callUrl("http://www.fuzzproductions.com/");
			}
        }; 

        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_main_dummy, container, false);
            
            int tabType = getArguments().getInt(ARG_SECTION_NUMBER);
            
            if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, setOnItemSelectedListener, tab: " + tabType);
            
            String[] ids;
            ArrayList<AllDataIndex> allDataIndex = new ArrayList<AllDataIndex>(); 
            
            ((MainActivity)thisMain).textList = ((MainActivity)thisMain).getTextTypes();
            ((MainActivity)thisMain).textOnlyAdapter.notifyDataSetChanged();

            switch(tabType) {
            case ALL_ITEMS:
                if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, setOnItemSelectedListener, ALL_ITEMS");

            	int textSize = ((MainActivity)thisMain).textList.size();
            	
            	int size = textSize + ((MainActivity)thisMain).imageHM.size();
            	int j = 0;
                for (int i= 0; i < size; i++) {
                	if(i < textSize)
                		allDataIndex.add( new MainActivity().new AllDataIndex(i, "text") );
                	else {
                		allDataIndex.add( new MainActivity().new AllDataIndex(j, "image") );
                		j++;
                	}
                }
                
                ListView listV1 = new ListView(getActivity() );
                
    	        listV1.setOnItemClickListener(goToFuzz);

    	        AllItemsAdapter allAdapter = new AllItemsAdapter(getActivity(),R.layout.row, allDataIndex);
                listV1.setAdapter(allAdapter);
                rootView.addView(listV1);

    	        break;
            case TEXT_ONLY:
                if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, setOnItemSelectedListener, TEXT_ONLY");

                ids = new String[((MainActivity)thisMain).textList.size()];

                for (int i= 0; i < ids.length; i++){
                    ids[i] = Integer.toString(i);
                }
                
                ListView listV2 = new ListView(getActivity() );
    	        listV2.setOnItemClickListener(goToFuzz);

    	     // listV2.setAdapter( new ArrayAdapter(((MainActivity)thisMain),android.R.layout.simple_list_item_1, ((MainActivity)thisMain).textList)); 
                listV2.setAdapter( new TextOnlyAdapter(getActivity(),R.layout.row, ids)); 
                rootView.addView(listV2);

    	        break;
            case IMAGE_ONLY: 
                if(GlobalSettings.mainActivity) Log.d("SectionFragment", "onCreateView, setOnItemSelectedListener, IMAGE_ONLY");

                ids = new String[((MainActivity)thisMain).imageHM.size()];

                for (int i= 0; i < ids.length; i++){
                    ids[i] = "";
                }
                
                ListView listV3 = new ListView(getActivity() );
    	        listV3.setOnItemClickListener(goToFuzz); 

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
	static String RECORD_ICON_NAME = "icon_name";

	private void getData() {
		if(sampleList != null)
			return;
		
		imageHM = new Hashtable<Integer, Bitmap>();
		
	    sampleList = new ArrayList<HashMap<String, String>>();
	    
	    new GetSampleData().execute();
	    
    	try {
    		if(GlobalSettings.mainActivity) Log.d("MainActivity", "getData: sleep 3s");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    textList = getTextTypes();
	}
	
	public boolean getSampleDataNotDone = true;
	
    class GetSampleData extends AsyncTask<String, String, String> {
        JsonParser jsonParser = new JsonParser();
        JSONArray sample = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
    	
        protected String doInBackground(String... args) {
        	// getting JSON string from URL
		    JSONObject json = jsonParser.makeHttpRequest(FUZZ_TEST_DATA, "GET",
		            params);
		
		    // Check log cat for JSON reponse
            if(GlobalSettings.mainActivity) Log.d("sample JSON: ", json.toString());
		
		    try {
		        sample = json.getJSONArray("sampledata");
		        // looping through All messages
	            String id = "";
	            String type = "";
	            String data = "";

		        for (int i = 0; i < sample.length(); i++) {
		            JSONObject c = sample.getJSONObject(i);
		
		            // init variable if error reading  
		            id = "";
		            type = "";
		            data = "";
		            try {
			            id = c.getString(RECORD_ID);
			            type = c.getString(RECORD_TYPE);
			            data = c.getString(RECORD_DATA);
				    } catch (JSONException e) {
			            if(GlobalSettings.mainActivity) Log.e("GetSampleData", 
			            		"doInBackground, cannot find field " + e.getMessage() +", "+ e.toString() );
				    }
		
		            // creating new HashMap
		            HashMap<String, String> map = new HashMap<String, String>();
		
		            // adding each child node to HashMap key => value
		            map.put(RECORD_ID, id);
		            map.put(RECORD_TYPE, type);
		            map.put(RECORD_DATA, data);
		            map.put(RECORD_ICON_NAME, "");
		            
		            
		            // adding HashList (need synchronization) to ArrayList
		            sampleList.add(map);
		        }
		
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
		    
		    return null;
	     }
        
        protected void onPostExecute(String result) {
        	String url;
        	String fileName;
        	int i = 0;
            for(HashMap<String, String> hmRec : sampleList) {
            	if(hmRec.get(RECORD_TYPE).equals("image")) {
            		url = hmRec.get(RECORD_DATA);
            		if( ! url.equals("")) {
            			// use the file name to store images 
            			fileName = url.substring(url.lastIndexOf("/") + 1);
            			hmRec.put(RECORD_ICON_NAME, fileName);
            			new DownloadImageTask(imageHM, new Integer(i) ).execute(url);
                    	i++;
            		}
            	}
            } // for
            if(GlobalSettings.mainActivity) Log.d("GetSampleData", "Set imageHM imageHM.size"+ imageHM.size());
            ((MainActivity)thisMain).getSampleDataNotDone = false;
            ((MainActivity)thisMain).textOnlyAdapter.notifyDataSetChanged();
        }
        
    } // class GetSampleData extends AsyncTask
    
    
    private ArrayList<String> getTextTypes() {
    	ArrayList<String> textArray = new ArrayList<String>();
    	
    	for(HashMap<String, String> typeHM : sampleList) {
    		if( typeHM.get("type") != null &&  typeHM.get("type").equals("text")) {
    			textArray.add(typeHM.get("data"));
    		}
    	}
       	
       	if(GlobalSettings.falseValue) Log.d("MainActivity", "getTextTypes: "+ textArray);
       	
    	return textArray;
    }
    
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        // ImageView bmImage;
    	Hashtable<Integer, Bitmap> imageHM;
    	Integer intKey;

        public DownloadImageTask(Hashtable<Integer, Bitmap> imageHM_in, Integer intKey_in) { // ImageView bmImage) {
            // this.bmImage = bmImage;
            this.imageHM = imageHM_in;
            this.intKey = intKey_in;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                if(GlobalSettings.mainActivity) Log.d("DownloadImageTask", "doInBackground processing, "+ urldisplay);
            } catch (Exception e) {
                if(GlobalSettings.mainActivity) Log.e("DownloadImageTask", "Error: "+ e.getMessage() +
                		", "+ e.toString());
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
        	if(result != null)
        		imageHM.put(intKey, result);
        }
    }
    
    class AllDataIndex {
    	AllDataIndex(int index, String type_in ) {
    		type = type_in;
    		i = index;
    	}
    	int i;
    	String type;
    }
    
    
}
