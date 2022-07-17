package hr.fer.zemris.java.fractals;

import java.util.LinkedList;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**
 * Class whose function is to show images of fractals derived from Newton-Raphson iteration. This class uses paralellization controlled with 
 * number of workers(threads) and tracks(jobs)
 * 
 * @author 38591
 *
 */

public class NewtonParallel {
	
		/**
		 * Number of workers(threads)
		 * 
		 */
		static int workers = 0;
		
		/**
		 * Number of tracks(jobs)
		 * 
		 */
		
		static int tracks = 0;
		
		/**
		 * Tells if parameter for number of threads is given
		 * 
		 */
		
		static boolean worker = false;
		
		/**
		 * Tells if parameter for number of jobs is given
		 * 
		 */
		
		static boolean track = false;
		
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
			
			if(args.length>4) {
				throw new IllegalArgumentException("Invalid number of arguments");
			}
			
			if(args.length>0) {
				checkArg(args[0],false);
				if(args.length==2) {
					checkArg(args[1],false);
				} else if(args.length==3) {
					if(args[0].equals("-w") || args[0].equals("-t")) {
						checkArg(args[1],true);
						checkArg(args[2],false);
					} else if(args[1].equals("-w") || args[1].equals("-t")) {
						checkArg(args[1],false);
						checkArg(args[2],true);
					} else {
						throw new IllegalArgumentException("Arguments are invalid");
					}
				} else if(args.length==4) {
					if(!((args[0].equals("-w") && args[2].equals("-t")) || (args[0].equals("-t") && args[2].equals("-w")))) {
						throw new IllegalArgumentException("Arguments are invalid");
					} else {
						checkArg(args[1],true);
						checkArg(args[2],false);
						checkArg(args[3],true);
					}
				}
			}
			
			int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
			
			if(!worker) {
				workers = numberOfAvailableProcessors;
			}
			
			if(!track) {
				tracks = 4*numberOfAvailableProcessors;
			}
			
			System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
			System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
			int counter = 0;
			checkCorrectInput(counter);
			sc.close();
			
			FractalViewer.show(new MojProducer());
		}
		
		/**
		 * Checks if user's input is correct. Checkc if correct number of roots are given.
		 * 
		 * @param counter int value of number of given roots
		 */

		private static void checkCorrectInput(int counter) {
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
		 * Method used for parsing given arguments
		 * 
		 * @param string String value of a given argument
		 * @param isShort true if number was supposed to be given as an argument,false otherwise
		 */
		
		private static void checkArg(String string,boolean isShort) {
			String[] str = new String[] {};
			if((string.contains("--workers") || string.contains("--tracks")) && !isShort) {
				str = string.split("=");
				
				if(string.contains("w") && !worker) {
					workers = Integer.valueOf(str[1]);
					checkValue(workers);
					worker = true;
				} else if(string.contains("t") && !track) {
					tracks = Integer.valueOf(str[1]);
					checkValue(tracks);
					track = true;
				} else {
					throw new IllegalArgumentException("Arguments are invalid");
				}
				
			} else if((string.equals("-w") || string.equals("-t")) && !isShort) {
				if(string.contains("-w ")) {
					worker = true;
				} else {
					track = true;
				}
			} else if(isShort) {
				if(worker) {
					workers = Integer.valueOf(string);
				} else if(track) {
					tracks = Integer.valueOf(string);
				}
			} else {
				throw new IllegalArgumentException("Arguments are invalid");
			}
			
		}

		/**
		 * Checks if valid value was given
		 * 
		 * @param value int value of number of workers or tracks
		 */
		
		private static void checkValue(int value) {
			if(value<1) 
				throw new IllegalArgumentException("Minimal acceptable number of tracks/workers is 1");
		}
		
		/**
		 * Class that counts values of data on which we base coloring
		 * 
		 * @author 38591
		 *
		 */
		
		public static class PosaoIzracuna implements Runnable {
			
			double reMin;
			double reMax;
			double imMin;
			double imMax;
			int width;
			int height;
			int yMin;
			int yMax;
			int m;
			short[] data;
			AtomicBoolean cancel;
			public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
			
			private PosaoIzracuna() {
				
			}
			
			public PosaoIzracuna(double reMin, double reMax, double imMin,
					double imMax, int width, int height, int yMin, int yMax, 
					int m, short[] data, AtomicBoolean cancel) {
				super();
				this.reMin = reMin;
				this.reMax = reMax;
				this.imMin = imMin;
				this.imMax = imMax;
				this.width = width;
				this.height = height;
				this.yMin = yMin;
				this.yMax = yMax;
				this.m = m;
				this.data = data;
				this.cancel = cancel;
			}
			
			@Override
			public void run() {

				int offset = yMin*width;
				
				for(int y = yMin; y <= yMax; y++) {
					if(cancel.get()) break;
					for(int x = 0; x < width; x++) {
						int iters = 0;
						double module = 0;
						Complex zn = map_to_complex_plain(x,y,0,width,yMin,yMax,reMin,reMax,imMin,imMax); 
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
						} else {
							data[offset] = 1;
						}
						offset++;
					}
				}			
				
			
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
			
			private Complex map_to_complex_plain(int x, int y, int i, int width2, int j, int height2, double reMin2,
					double reMax2, double imMin2, double imMax2) {
				double re = x/(width-1.0) * (reMax-reMin) + reMin;
				double im = (height-1.0-y)/(height-1)*(imMax-imMin)+imMin;
				return new Complex(re,im);
			}
		}
		
		/**
		 * Class that represents an implementation of producer of a fractal image
		 * 
		 * @author 38591
		 *
		 */
		
		private static class MojProducer implements IFractalProducer{

			@Override
			public void produce(double reMin, double reMax, double imMin, double imMax,
					int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Zapocinjem izracun...");
				
				short[] data = new short[width * height];
				
				if(tracks>height) {
					tracks = height;
				}
				
				int brojYPoTraci = height / tracks;
				
				System.out.println(Thread.activeCount());
				System.out.println(brojYPoTraci);
				
				final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

				Thread[] radnici = new Thread[workers];
				for(int i = 0; i < radnici.length; i++) {
					radnici[i] = new Thread(new Runnable() {
						@Override
						public void run() {
							while(true) {
								PosaoIzracuna p = null;
								try {
									p = queue.take();
									if(p==PosaoIzracuna.NO_JOB) break;
								} catch (InterruptedException e) {
									continue;
								}
								p.run();
							}
						}
					});
				}
				
				for(int i = 0; i < radnici.length; i++) {
					radnici[i].start();
				}
				
				for(int i = 0; i < tracks; i++) {
					int yMin = i*brojYPoTraci;
					int yMax = (i+1)*brojYPoTraci-1;
					if(i==tracks-1) {
						yMax = height-1;
					}
					PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynomial.order()+1, data, cancel);
					while(true) {
						try {
							queue.put(posao);
							break;
						} catch (InterruptedException e) {
						}
					}
				}
				for(int i = 0; i < radnici.length; i++) {
					while(true) {
						try {
							queue.put(PosaoIzracuna.NO_JOB);
							break;
						} catch (InterruptedException e) {
						}
					}
				}
				
				for(int i = 0; i < radnici.length; i++) {
					while(true) {
						try {
							radnici[i].join();
							break;
						} catch (InterruptedException e) {
						}
					}
				}
				
				System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
				observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
			}
		}
			
}
