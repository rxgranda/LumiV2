package com.qualcomm.QCARSamples.CloudRecognition;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Item {
	public Item (Drawable b, String ti){
		title=ti;
		image = b;
		
	}
	private String title;
	private Drawable image;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	

}
