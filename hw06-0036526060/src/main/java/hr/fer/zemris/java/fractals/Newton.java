package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The program must ask user to enter roots and then it must start fractal viewer and display the fractal. This class contains methods whose function is to
 * show images of fractals derived from Newton-Raphson iteration.
 * 
 * @author 38591
 *
 */

public class Newton {
	
	/**
	 * Scanner used to read user's input of roots.
	 * 
	 */
	
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * List of Complex numbers read from user's input.
	 * 
	 */
	
	private static List<Complex> list = new LinkedList<Complex>();
	
	/**
	 * Polynomial created using rooted polynomial representation of user's input.
	 * 
	 */
	
	public static ComplexPolynomial polynomial;
	
	/**
	 * Polynomial created using roots from user's input.
	 * 
	 */
	
	public static ComplexRootedPolynomial crp;
	
	/**
	 * Main method that starts the program
	 * 
	 * @param args arguments from run configurations.
	 */
	
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int counter = 0;
		checkCorrectInput(counter);
		sc.close();
		FractalViewer.show(new Producer());
	
	}

	/**
	 * Checks if user's input is correct. Check if correct number of roots are given.
	 * 
	 * @param counter int value of number of given roots
	 */
	
	public static void checkCorrectInput(int counter) {
		System.out.format("Root %d> ",counter+1);
		String s = sc.nextLine();
		while(!s.equals("done")) {
			++counter;
			list.add(findComplexNumber(s));
			System.out.format("Root %d> ",counter+1);
			s = sc.nextLine();
		}
		int difference = 2-counter; 
		if(difference>0) {
			throw new IllegalArgumentException("At least two roots must be provided!");
		} 
		crp = new ComplexRootedPolynomial(new Complex(1,0),list.toArray(new Complex[list.size()]));
		System.out.println(crp.toString());
		polynomial = crp.toComplexPolynom();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
	}

	/**
	 * Method used for parsing user's input. Creates complex number representation of user's input.
	 * 
	 * @param s given String representation of one line of user's input
	 * @return complex number representation of user's input
	 */
	
	private static Complex findComplexNumber(String s) {
		boolean minus = false;
		boolean minNext = false;
		String[] str = new String[] {};
		if(s.contains("+") && !s.contains(" ")) {
			str = s.split("\\+");
		} else if(s.contains("-") && !s.startsWith("-") && !s.contains(" ")) {
			str = s.split("-");
			minus = true;
		} else {
			str = s.split(" ");
		}
		double re = 0;
		double im = 0;
		for(String element : str) {
			if((element.endsWith("+") || element.endsWith("-")) && element.length()>1) {
				if(element.endsWith("+")) {
					element = element.replaceAll("\\+","");
				}else if(element.endsWith("-") && element.length()>1) {
					element = element.replaceAll("-","");
					minNext = true;
				}
			} else if(element.startsWith("+") && element.length()>1) {
				element = element.substring(1);
			}
			if(!element.contains("i") && !element.equals("+") && !element.equals("-")) {
				re = Double.valueOf(element);	
				if(minus) {
					re = -re;
				}
			} else if(element.contains("i")) {
				if(element.startsWith("-") || minus) {
					if(element.length()>2) {
						im = -Double.valueOf(element.substring(2));
					} else {
						im = -1;
					}
					if(minus) minus = false;
				} else {
					if(element.length()>1) {
						im = Double.valueOf(element.substring(1));
					} else {
						im = 1;
					}
				}
			} else if(element.equals("-")) {
				minus = true;
			}
			if(minNext) {
				minus = true;
				minNext = false;
			}
		}
		return new Complex(re,im);
	}
	
	/**
	 * Class used for production of fractal images
	 * 
	 * @author 38591
	 *
	 */
	
	private static class Producer implements IFractalProducer {
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");

			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					int iters = 0;
					double module = 0;
					Complex zn = map_to_complex_plain(x,y,0,width,0,height,reMin,reMax,imMin,imMax); 
					do {
						Complex numerator = polynomial.apply(zn);
						ComplexPolynomial derived = polynomial.derive();
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						//System.out.println(fraction.toString());
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(module > 0.001 && iters < 60);
					//System.out.println(zn.toString());
					int index = crp.indexOfClosestRootFor(zn, 0.002);
					if(index>=0) {
						data[offset] = (short)(index+1);
						//System.out.println(index);
					} else {
						data[offset] = 1;
					}
					offset++;
				}
			}
			
			//Mandelbrot.calculate(arg0, arg1, arg2, arg3, arg4, arg5, polynomial.order()+1, 0, arg5-1, data, arg8);
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
			
		}
		
		/**
		 * Method used to calculate values of a complex number
		 * 
		 * @param x x-coordinate value
		 * @param y y-coordinate value
		 * @param i minimum x-value
		 * @param width maximum x-value
		 * @param j minimum y-value
		 * @param height maximum y-value
		 * @param reMin minimum real value
		 * @param reMax maximum real value
		 * @param imMin minimum imaginary value
		 * @param imMax maximum imaginary value
		 * @return calculated Complex number using given parameters
		 */

		private Complex map_to_complex_plain(int x, int y, int i, int width, int j, int height, double reMin,
				double reMax, double imMin, double imMax) {
			double re = x/(width-1.0) * (reMax-reMin) + reMin;
			double im = (height-1.0-y)/(height-1)*(imMax-imMin)+imMin;
			return new Complex(re,im);
		}

	
	}
		
}
