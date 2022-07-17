package hr.fer.zemris.java.gui.charts;

import java.awt.event.ComponentAdapter;

import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



public class BarChartDemo extends JFrame {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private Path path;
	private List<String> fileData = new LinkedList<String>();
	private BarChart barChart;
	private BarChartComponent component;
	
	public BarChartDemo(Path path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.path = path;
		if(path.toFile().exists()) {
			readFile();
		}
		this.setLocation(500, 500);
		this.setSize(500,500);

		
		this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            		component.repaint();
            }
        });
	}

	private void readFile() {
		try {

			fileData = Files.readAllLines(path, StandardCharsets.UTF_8);
			
			if(fileData.size()<6) throw new IllegalArgumentException("File doesn't contain all required lines");
			String xOpis;
			String yOpis;
			List<XYValue> listXY;
			int yMin;
			int yMax;
			int razmak;
			xOpis = fileData.get(0);
			yOpis = fileData.get(1);
			listXY = checkList(fileData.get(2));
			yMin = checkNumber(fileData.get(3));
			yMax = checkNumber(fileData.get(4));
			razmak = checkNumber(fileData.get(5));
			
			barChart = new BarChart(listXY, xOpis, yOpis, yMin, yMax, razmak);
			
			component = new BarChartComponent(barChart);

			add(component);
			
			component.repaint();

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int checkNumber(String string) {
		try {
			int number = Integer.parseInt(string);
			return number;
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Given value is incorrect");
		}
		
	}

	private List<XYValue >checkList(String string) {
		List<XYValue> list = new LinkedList<XYValue>();
		String[] values = string.split(" ");
		for(String s : values) {
			if(!s.isEmpty()) {
				if(!s.contains(",")) throw new IllegalArgumentException("Incorrect XYValue form");
				String[] numbers = s.split(",");
				if(numbers.length !=2) throw new IllegalArgumentException("Incorrect XYValue form");
				try {
					int x = Integer.valueOf(numbers[0]);
					int y = Integer.valueOf(numbers[1]);
					list.add(new XYValue(x, y));
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Value should be a number, but isn't");
				}
			}
		}
		return list;
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()->{
			new BarChartDemo(Paths.get("myFile.txt")).setVisible(true);
		});
	}
}
