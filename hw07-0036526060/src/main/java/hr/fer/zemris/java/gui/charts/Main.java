package hr.fer.zemris.java.gui.charts;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		BarChart model = new BarChart(
				 Arrays.asList(
				 new XYValue(1,8), new XYValue(2,20), new XYValue(3,22), 
				 new XYValue(4,10), new XYValue(5,4)
				 ),
				 "Number of people in the car",
				 "Frequency",
				 0, // y-os kreće od 0
				 22, // y-os ide do 22
				 2
				);
		String[] splitted = ("a   b".split(" "));
		for(String s : splitted) {
			System.out.println(s);
		}
	}
	
}

	


