package com.coin_collection;

import java.time.LocalDate;

public class Coin {
	private String country;
	private String denomination;
	private int yearOfMinting;
	private double currentValue;
	private LocalDate acquiredDate;
	
	public Coin(String country, String denomination, int yearOfMinting, double currentValue, LocalDate acquiredDate) {
		this.country = country;
		this.denomination = denomination;
		this.yearOfMinting = yearOfMinting;
		this.currentValue = currentValue;
		this.acquiredDate = acquiredDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getYearOfMinting() {
		return yearOfMinting;
	}

	public void setYearOfMinting(int yearOfMinting) {
		this.yearOfMinting = yearOfMinting;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public LocalDate getAcquiredDate() {
		return acquiredDate;
	}

	public void setAcquiredDate(LocalDate acquiredDate) {
		this.acquiredDate = acquiredDate;
	}

	@Override
	public String toString() {
		return
		"---------------------------------------------------------------------------------------------------------------------------------------\n" +
		"Country : " + country + " | " +
		"Denomination : " + denomination + " | " +
		"Year of Minting: " + yearOfMinting + " | " +
		"Current Value : " + currentValue + " | " +
		"Acquired Date : " + acquiredDate + " | ";
		}
	
	
}
