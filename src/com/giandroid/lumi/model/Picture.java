package com.giandroid.lumi.model;

import android.graphics.Bitmap;

public class Picture {
	private String picUrl;
	private String title;
	private String author;
	private String year;
	private String description;
	private String relations;
	private String technique;
	private Bitmap picture;
	
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
	
	

}
