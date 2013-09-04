package com.giandroid.lumi.model;

import android.graphics.drawable.Drawable;

public class Author {
	private String name;
	private String life_period;
	private String bio;
	private String nationality;
	private String picUrl;
	private String relations;
	private String movimiento;
	private Drawable imagen;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLife_period() {
		return life_period;
	}
	public void setLife_period(String life_period) {
		this.life_period = life_period;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public Drawable getImagen() {
		return imagen;
	}
	public void setImagen(Drawable imagen) {
		this.imagen = imagen;
	}
}
