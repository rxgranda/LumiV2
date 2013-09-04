package com.qualcomm.QCARSamples.CloudRecognition;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import com.giandroid.lumi.model.Author;
import com.giandroid.lumi.model.Control;
import com.giandroid.lumi.model.Picture;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Detalle extends FragmentActivity implements ActionBar.TabListener {
	static String server="http://192.168.0.101:8080/lumi";
	static String nameAutor="";
	static Author autor;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the app, one at a
	 * time.
	 */
	ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		setContentView(R.layout.detalle_info);




		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by the adapter.
			// Also specify this Activity object, which implements the TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	 * sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:               
				return new LaunchpadSectionFragment();

			default:
				// The other sections of the app are dummy placeholders.
				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position==0){
				return "Pintura";
			}else
				return "Autor";
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class LaunchpadSectionFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.pintura, container, false);
			Picture pintura=Control.pintura;

			ImageView view=(ImageView) rootView.findViewById(R.id.detallePintura);   
			TextView titulo=(TextView)  rootView.findViewById(R.id.textView2); 
			TextView anio=(TextView)  rootView.findViewById(R.id.textView4);
			TextView tecnica=(TextView)  rootView.findViewById(R.id.textView6); 
			TextView desc=(TextView)  rootView.findViewById(R.id.textView7); 
			titulo.setText(pintura.getTitle());
			tecnica.setText(pintura.getTechnique());
			anio.setText(pintura.getYear());
			desc.setText(pintura.getDescription());
			nameAutor=pintura.getAuthor();
			Bitmap bitmap = ((BitmapDrawable) pintura.getImage()).getBitmap();;
			Drawable d = new BitmapDrawable(getResources(),bitmap); 
			if(view!=null)
				view.setBackground(d);

			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.autor, container, false);
			
			ImageView view=(ImageView) rootView.findViewById(R.id.imageView2);   
			TextView nombre=(TextView)  rootView.findViewById(R.id.textView22); 
			TextView anio=(TextView)  rootView.findViewById(R.id.textView24);
			TextView movimiento=(TextView)  rootView.findViewById(R.id.textView26); 
			TextView bio=(TextView)  rootView.findViewById(R.id.textView27); 
			getAutor(nameAutor);
			if(autor!=null){
			nombre.setText(autor.getName());
			anio.setText(autor.getNationality()+ "  "+ autor.getLife_period());
			movimiento.setText(autor.getMovimiento());
			String blank="                                                                                                                                  ";
			bio.setText(autor.getBio()+"\n\n\n\n\n\n\n\n\n\n\n  "+blank+blank+blank+blank+blank+blank+blank+blank+blank);  
			view.setBackground(autor.getImagen());
			}
			return rootView;
		}
	}



	public static String getJSON(String name){
		URL url;
		try {
			url = new URL(name);
			URLConnection urlConnection = url.openConnection();			
			InputStream is= new BufferedInputStream(urlConnection.getInputStream());
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(64); 
			int current = 0;
			while((current = bis.read()) != -1) {
				bab.append((byte)current); 
			}
			return new String(bab.toByteArray());
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return null;
	}

	public static Drawable getImagen( String name){
		URL url;

		try {
			url = new URL(server+"/imagenes/"+name);
			InputStream is = (InputStream) url.getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  



	public static void getAutor(String name){
		String json  = getJSON(server+"/autor/"+name);
		JSONObject jObject;
		try {
			jObject = new JSONObject(json);

			String urlImagen = jObject.getString("picUrl");		
				
			Drawable imagen = getImagen(urlImagen);			
			Author autor = new Author();
			autor.setName(jObject.getString("name"));
			autor.setLife_period(jObject.getString("life_period"));
			autor.setMovimiento(jObject.getString("movimiento"));
			autor.setBio(jObject.getString("bio"));
			autor.setNationality(jObject.getString("nationality"));
			autor.setImagen(imagen);
			Detalle.autor=autor;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
