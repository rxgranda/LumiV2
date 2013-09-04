package com.giandroid.lumi.model;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

@SuppressWarnings("serial")
public class Picture implements Comparable<Picture>, Serializable {
	private static int sort=0;
	private String picUrl;
	private String title;
	private String author;
	private String year;
	private String description;
	private String relations;
	private String technique;
	private Bitmap picture;
	private Drawable image;
	
	public Bitmap getPicture() {
		return picture;
	}
	public void setPicture(Bitmap picture) {
		this.picture = picture;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTechnique() {
		return technique;
	}
	public void setTechnique(String technique) {
		this.technique = technique;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
	}
	public Picture() {
		
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	
	@Override
	public int compareTo(Picture another) {
		if (another==null)
			return 0;
		if (sort==0){
			return getTitle().compareTo(another.getTitle());
		}else if(sort==1){
			return getYear().compareTo(another.getYear());
		}else if(sort ==2) {
			return getAuthor().compareTo(another.getAuthor());
		}else{
			return getTechnique().compareTo(another.getTechnique());
			
		}		
	}
	public static int getSort() {
		return sort;
	}
	public static void setSort(int sort) {
		Picture.sort = sort;
	}	
	
	

}
