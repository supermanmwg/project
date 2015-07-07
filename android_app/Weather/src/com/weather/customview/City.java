package com.weather.customview;

public class City {
	private String name;
	private String temperature;
	private String iconName;
	
	public City(String name, String temperature, String iconName) {
		super();
		this.name = name;
		this.temperature = temperature;
		this.iconName = iconName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	

}
