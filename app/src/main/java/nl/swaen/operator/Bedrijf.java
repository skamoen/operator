package nl.swaen.operator;

import java.util.ArrayList;

public class Bedrijf {
	int id;
	String naam;
	String longitude;
	String latitude;
	String informatie;
	ArrayList<String> vragen;

	public Bedrijf(int id, String naam, String longitude, String latitude, String informatie, ArrayList<String> vragen) {
		super();
		this.id = id;
		this.naam = naam;
		this.longitude = longitude;
		this.latitude = latitude;
		this.informatie = informatie;
		this.vragen = vragen;
	}

	public int getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getInformatie() {
		return informatie;
	}

	public ArrayList<String> getVragen() {
		return vragen;
	}

}
