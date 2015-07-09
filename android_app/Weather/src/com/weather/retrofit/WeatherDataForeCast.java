package com.weather.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Plain Old Java Object (POJO) used for data transport within
 * the WeatherService app. It represents the response Json obtained from the
 * Open Weather Map API, e.g., a call to
 * http://api.openweathermap.org/data/2.5/weather?q=Nashville,TN might return
 * the following Json data:
 * 
{	"cod":"200",
		"message":0.0333,
		"city":{"id":1816670,
				"name":"Beijing",
				"coord":{
						"lon":116.397232,
						"lat":39.907501
						},
				"country":"CN",
				"population":0,
				"sys":{"population":0}
				},
		"cnt":2,
		"list":[
		        {"dt":1436241600,
		         "temp":{
		        		 "day":33.27,
		        		 "min":21.23,
		        		 "max":33.27,
		        		 "night":21.23,
		        		 "eve":33.27,
		        		 "morn":33.27
		        		 },
		          "pressure":945.13,
		          "humidity":42,
		          "weather":[
		                     {"id":800,
		                      "main":"Clear",
		                      "description":"sky is clear",
		                      "icon":"02d"
		                      }
		                    ],
		          "speed":1.66,
		          "deg":128,
		          "clouds":8
		         },
		       ]}
 * The meaning of these Json fields is documented at
 * http://openweathermap.org/weather-data#current.
 * 
 * The Retrofit library handles automatic conversion from this Json
 * data to this object.  The Java annotations enable this
 * functionality.
 */

public class WeatherDataForeCast {
	private String cod;
	private double message;
	private City city;
	private long cnt;
	private List<DayItem> list =new ArrayList<DayItem>();
	
	public WeatherDataForeCast() {
	}
	
	public WeatherDataForeCast(String cod, double message, City city, long cnt,
			List<DayItem> list) {
		super();
		this.cod = cod;
		this.message = message;
		this.city = city;
		this.cnt = cnt;
		this.list = list;
	}

	public String getCod() {
		return cod;
	}

	public double getMessage() {
		return message;
	}

	public City getCity() {
		return city;
	}

	public long getCnt() {
		return cnt;
	}

	public List<DayItem> getList() {
		return list;
	}



	public static class City {
		
		private long id;
		private String name;
		private Coord coord;
		private String country;
		private long population;
		private Sys sys;


		public City(long id, String name, Coord coord, String country,
				long population, Sys sys) {
			this.id = id;
			this.name = name;
			this.coord = coord;
			this.country = country;
			this.population = population;
			this.sys = sys;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public Coord getCoord() {
			return coord;
		}

		public String getCountry() {
			return country;
		}
		
		public long getPopulation() {
			return population;
		}

		public Sys getSys() {
			return sys;
		}

		public static class Coord {
			
			private double lon;
			private double lat;
			
			public Coord(double lon, double lat) {
				this.lon = lon;
				this.lat = lat;
			}


			public double getLon() {
				return lon;
			}

			public double getLat() {
				return lat;
			}

		}
		
		public static class Sys {

			private int population;
			
			public Sys(int population) {
				this.population = population;
			}

			public int getPopulation() {
				return population;
			}

		}
		
	}
	
	public static class DayItem{
		
		private long dt;
		private Temp temp;
		private double pressure;
		private long humidity;
		private List<Weather> weather = new ArrayList<Weather>();
		private double speed;
		private long deg;
		private long clouds;
		
		public DayItem(long dt, Temp temp, double pressure, long humidity,
				List<Weather> weather, double speed, long deg, long clouds) {
			super();
			this.dt = dt;
			this.temp = temp;
			this.pressure = pressure;
			this.humidity = humidity;
			this.weather = weather;
			this.speed = speed;
			this.deg = deg;
			this.clouds = clouds;
		}

		public long getDt() {
			return dt;
		}

		public Temp getTemp() {
			return temp;
		}

		public double getPressure() {
			return pressure;
		}

		public long getHumidity() {
			return humidity;
		}

		public List<Weather> getWeather() {
			return weather;
		}

		public double getSpeed() {
			return speed;
		}

		public long getDeg() {
			return deg;
		}

		public long getClouds() {
			return clouds;
		}



		public static class Temp {
			private double day;
			private double min;
			private double max;
			private double night;
			private double eve;
			private double morn;
			
			public Temp(double day, double min, double max, double night,
					double eve, double morn) {
				super();
				this.day = day;
				this.min = min;
				this.max = max;
				this.night = night;
				this.eve = eve;
				this.morn = morn;
			}
			
			public double getDay() {
				return day;
			}
			
			public double getMin() {
				return min;
			}
			
			public double getMax() {
				return max;
			}
			
			public double getNight() {
				return night;
			}
			
			public double getEve() {
				return eve;
			}
			
			public double getMorn() {
				return morn;
			}
		}

		public static class Weather{
			private long id;
			private String main;
			private String description;
			private String icon;
			
			public Weather(long id, String main, String description, String icon) {
				super();
				this.id = id;
				this.main = main;
				this.description = description;
				this.icon = icon;
			}

			public long getId() {
				return id;
			}

			public String getMain() {
				return main;
			}

			public String getDescription() {
				return description;
			}

			public String getIcon() {
				return icon;
			}
		}
		
	}
	
}
