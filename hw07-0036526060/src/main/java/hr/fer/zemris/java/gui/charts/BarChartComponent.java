package hr.fer.zemris.java.gui.charts;


import java.awt.Color;

import java.awt.Dimension;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Component that shows a bar chart
 * 
 * @author 38591
 *
 */
public class BarChartComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model of a bar chart
	 */
	private BarChart barChart;

	/**
	 * Constructor that initializes a bar chart component
	 * Repaint() function used to draw a graph
	 * 
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		int x = this.barChart.getMaxY();
		int y = this.barChart.getMaxY();
		this.setPreferredSize(new Dimension(x,y));
		
		repaint();
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
	
		int width = (int)this.getParent().getWidth();
		int height = (int)this.getParent().getHeight();
		
		Font font = new Font(null, Font.PLAIN, 10);  
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(270), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		
		
		int maxY = g2.getFontMetrics(this.getFont()).stringWidth(String.valueOf(barChart.getMaxY()));
		int pocY = 60 + maxY;
		int pocX = 55 + maxY;
	

		int[] lineYpointsY = new int[6];
		int[] lineYpointsX = new int[6];
		
		//y os
		lineYpointsX[0] = pocY;
		lineYpointsX[1] = pocY;
		lineYpointsX[2] = pocY+1;
		lineYpointsX[3] = pocY+1;
		
		int stringHeight = g2.getFontMetrics(this.getFont()).getHeight();
		int dnoOsiY = height-30 - 2*stringHeight - 8;
		
		lineYpointsY[0] = 70;
		lineYpointsY[1] = dnoOsiY;
		lineYpointsY[2] = dnoOsiY;
		lineYpointsY[3] = 70;

		g2.setColor(Color.GRAY);
		g2.drawPolygon(lineYpointsX, lineYpointsY , 4);	
		g2.fillPolygon(lineYpointsX, lineYpointsY , 4);
		g2.drawPolygon(new int[] {pocX, pocY, 65 + maxY + 1}, new int[] {70, 60, 70}, 3);
		
		//x os
		lineYpointsX[0] = pocX;
		lineYpointsX[1] = pocX;
		lineYpointsX[2] = width-pocX;
		lineYpointsX[3] = width-pocX;
		
		int dnoOsiX = dnoOsiY - 5;
		
		lineYpointsY[0] = dnoOsiX -1;
		lineYpointsY[1] = dnoOsiX;
		lineYpointsY[2] = dnoOsiX;
		lineYpointsY[3] = dnoOsiX -1;
		
		g2.drawPolygon(lineYpointsX, lineYpointsY , 4);
		g2.fillPolygon(lineYpointsX, lineYpointsY , 4);
		
		//strelica na y-osi
		g2.drawPolygon(new int[] {width - pocX, width - 45-maxY , 
				width - pocX}, new int[] {dnoOsiX-6,
						dnoOsiX, dnoOsiX+6}, 3);
		
		
		//izracun lokacija komponenata y-osi
		int lineLength = height - 140;    //duljina osi y
		
		
		double eachLength = barChart.getRazmakY()*(lineLength/(barChart.getMaxY()-barChart.getMinY()));
		int length = (int) (eachLength);
		if(length>eachLength) {
			length = length-1;
		}
		
		int keepLengthY = length/barChart.getRazmakY();	
		int startX = pocY - 10;
		int startY = dnoOsiX-1 + this.getFont().getSize()/2 -2;

		//crtanje komponenata y-osi
		for(int i=0;i<=barChart.getMaxY();i=i+barChart.getRazmakY()) {
				String current =  String.valueOf(i);
				int stringHeight1 = g2.getFontMetrics(this.getFont()).getHeight();
				g2.drawString(current, startX - g2.getFontMetrics(this.getFont()).stringWidth(current), startY);
				g2.drawLine(pocY -5, startY - stringHeight1/2+4, pocY, 
						startY - stringHeight1/2+4);
				startY = startY-length;

		}
		
		g2.setFont(rotatedFont);
		int size = g2.getFontMetrics(this.getFont()).stringWidth(barChart.getOpisUzY());
		
		//dodaj opis uz y
		g2.drawString(barChart.getOpisUzY(), 30, 
			70 + lineLength/2 + size/2 - 10);
		
		
		//izracun lokacija komponenata X-osi
		List<Integer> listaX = barChart.getListXY().stream().map(a -> a.getX()).collect(Collectors.toList());
		
		lineLength = width - 2*pocX - 5;
		eachLength = lineLength/(listaX.size());
		length = (int)eachLength;
		if((double)length>eachLength) {
			length = length-1;
		}
		
		g2.setFont(font);
		
		//crtanje komponenata X-osi
		for(int i=0;i<listaX.size();++i) {
			String current =  String.valueOf(listaX.get(i));
			g2.drawLine(pocY + i*length, dnoOsiY - 5, pocY + i*length, dnoOsiY-1);
			g2.drawString(current, pocY + i*length + length/2 - current.length(), height-30 - (height-30-dnoOsiX)/2);

		}
		
		g2.drawLine(pocY + listaX.size()*length, dnoOsiY-5, pocY + listaX.size()*length, dnoOsiY-1);
		size = g2.getFontMetrics(this.getFont()).stringWidth(barChart.getOpisUzX());
		g2.drawString(barChart.getOpisUzX(), pocY + lineLength/2 - size/2 + 10, 
				height-30);
		
		//crtanje stupaca grafa
		List<Integer> listaY = barChart.getListXY().stream().map(a -> a.getY()).collect(Collectors.toList());
		for(int i=0;i<listaY.size();++i) {
			int cur = listaY.get(i);
			Polygon p = new Polygon(new int[] {pocY + i*length+1, pocY + (i+1)*length
					,pocY + (i+1)*length, pocY + i*length+1}, new int[] {dnoOsiX-1, dnoOsiX-1,
							 dnoOsiX-1 - cur*keepLengthY, dnoOsiX-1 - cur*keepLengthY}, 4);
			
			g2.setColor(Color.ORANGE);
			g2.fillPolygon(p);
			g2.setColor(Color.GRAY);
			g2.drawPolygon(p);
			

		}
		
		g2.dispose();
	}
	
}
