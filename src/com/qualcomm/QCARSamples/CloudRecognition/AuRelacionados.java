package com.qualcomm.QCARSamples.CloudRecognition;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import com.giandroid.lumi.model.Author;
import com.giandroid.lumi.model.Control;
import com.giandroid.lumi.model.Picture;
import com.qualcomm.QCARSamples.CloudRecognition.utils.DebugLog;
@SuppressLint("NewApi")
public class AuRelacionados extends Activity {

	String server="http://www.soravi.com/__tmp/lumi";
	int numColumnasGrid=0;

	ArrayList<Item> gridArray = new ArrayList<Item>();

	CustomGridViewAdapter customGridAdapter;

	List <Author> autores= new LinkedList<Author>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		setContentView(R.layout.activity_au_relacionados);
		////////////////////////////////////////////
		File root = android.os.Environment.getExternalStorageDirectory(); 
		String FILENAME = root.getAbsolutePath()+"/pinacoteca.txt";
		String string = server+"/pintura/ninallorando.json";
		String segundo=server+"/pintura/almuerzo.json";
		String aJsonString="b";


		//		FileOutputStream fos;
		//		try {
		//			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		//			String tmp=string+"\n"+segundo+"\n"+string+"\n"+segundo;
		//			fos.write(tmp.getBytes());
		//			fos.close();
		//		} catch (FileNotFoundException e) {			
		//			e.printStackTrace();
		//		} catch (IOException e) {			
		//			e.printStackTrace();
		//		}
		///////////////////////////////////////////






		//		TextView txt=(TextView) findViewById(R.id.textView1);
		//		txt.setText(jsonList.get(0));    android:numColumns="3"


		//grid.setNumColumns(numColumnasGrid);

		LinkedList <String> jsonList= new LinkedList<String>();
		String autor=Control.pintura. getAuthor();
		String urlAutor=server+"/autor/"+autor;
		String jsonAutor=getJSON(urlAutor);
		JSONObject jObjectA;
		try {
			jObjectA = new JSONObject(jsonAutor);

			String rel = jObjectA.getString("relations");
			String [] autoresSplit=rel.split("&&");
			for(String au:autoresSplit){
				jsonList.add(server+"/autor/"+au);	
				DebugLog.LOGD(server+"/autor/"+au);
			}


			for(String jsonDir:jsonList){
				String json  = getJSON(jsonDir);
				JSONObject jObject = new JSONObject(json);
				String urlImagen = jObject.getString("picUrl");		
				String titulo = jObject.getString("name");		
				String nacionalidad = jObject.getString("nationality");	
				Drawable imagen = getImagen(urlImagen);			
				Author autorE = new Author();
				autorE.setName(titulo);
				autorE.setImagen(imagen);
				autorE.setNationality(jObject.getString("nationality"));
			

				autores.add(autorE);


			}
			ordenar(0);

		} catch (JSONException e) {		
			e.printStackTrace();
		} 
	}

	public void ordenar(int sort){
		GridView grid = (GridView) findViewById(R.id.gridView81);
		gridArray = new ArrayList<Item>();
		Author.setSort(sort);
		Collections.sort(autores);
		for (Author aut: autores){
			gridArray.add(new Item(aut.getImagen(),aut.getName()));
		}
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray, grid.getWidth());
		grid.setAdapter(customGridAdapter);

		grid.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {

			//	Intent intent = new Intent(getBaseContext(), Detalle.class);		
				
			}

		} );
	}


	public LinkedList<String> getPinturasGuardadas(String filename){		
		String eol = System.getProperty("line.separator");
		BufferedReader input = null;
		StringBuffer buffer=null;
		File a= new File(filename);
		FileInputStream is;
		try {
			is = new FileInputStream(filename);


			// create new input stream reader
			InputStreamReader isr = new InputStreamReader(is);
			try {
				input = new BufferedReader(isr);
				String line;
				buffer = new StringBuffer();

				while ((line = input.readLine()) != null) {
					numColumnasGrid++;

					buffer.append(line + eol);
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String string2=new String(buffer); 
		String[] words = string2.split("\n");
		LinkedList <String> jsonList=new LinkedList<String>(); 
		for (String word: words) {
			DebugLog.LOGD(word);
			jsonList.add(word);
		}
		numColumnasGrid=numColumnasGrid/3+1;
		return jsonList;		
	}


	public String getJSON(String name){
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

	public Drawable getImagen( String name){
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.au_relacionados, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.item81:
			Toast.makeText(this, "Ordenado  po Nombre", Toast.LENGTH_SHORT).show();
			ordenar(0);
			return true;
		case R.id.item82:
			Toast.makeText(this, "Ordenado por Apellido", Toast.LENGTH_SHORT).show();
			ordenar(1);
			//composeMessage();
			return true;
		case R.id.item83:
			Toast.makeText(this, "Ordenado por Tecnica", Toast.LENGTH_SHORT).show();
			ordenar(2);
			//composeMessage();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}




}
