package com.loteria.model;

import java.util.List;

public class Draw {

	private int drawNumber;

	private String drawNumbers;

	private List<String> numbers;

	private int id;

	public Draw() {

	}

	public Draw(String drawNumbers) {

		this.drawNumbers = drawNumbers;
	}

	public Draw(int id, String drawNumbers) {

		this.id = id;
		this.drawNumbers = drawNumbers;
	}

	public Draw(int id, int drawNumber, List<String> numbers) {
		this.id = id;
		this.drawNumber = drawNumber;
		this.numbers = numbers;
	}

	public String getDrawNumbers() {
		return drawNumbers;
	}

	public void setDrawNumbers(String drawNumbers) {
		this.drawNumbers = drawNumbers;
	}

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}