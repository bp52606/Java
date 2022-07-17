package hr.fer.zemris.math;
/**
 * Class used as a representation of complex numbers in a form :  z0*(z-z1)*(z-z2)*...*(z-zn), where z1-zn are it's roots.
 * 
 * @author 38591
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Constant value of this polynomial.
	 * 
	 */
	private Complex constant;
	/**
	 * Root values of this polynomial.
	 * 
	 */
	private Complex[] roots;
	
	/**
	 * Constructor that accepts a complex number that represents a constant and an array of roots.
	 * 
	 * @param constant constant value of this complex number, z0
	 * @param roots root values of this complex number
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z given Complex number being used to count a value of this polynomial at point z
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for(int i=0;i<roots.length;++i) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * 
	 * @return ComplexPolynomial representation of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		ComplexPolynomial complexPol = new ComplexPolynomial(constant);
		
		for (Complex element : roots) {
			complexPol = complexPol.multiply(new ComplexPolynomial(element.negate(), Complex.ONE));
		}
		
		return complexPol;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + constant.toString() +")");
		for(int i=0;i<this.roots.length;++i) {
			sb.append("*(z-("+this.roots[i].toString()+"))");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * Finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc.
	 * 
	 * @param z given complex number
	 * @param treshold maximum distance between two complex numbers
	 * @return index of closest root to given complex number
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		
		double minRoot = Math.sqrt(Math.pow(this.roots[0].getReal()-z.getReal(), 2)+
				Math.pow(this.roots[0].getImaginary()-z.getImaginary(), 2));

		for(int i=1;i<this.roots.length;++i) {

			double module = Math.sqrt(Math.pow(this.roots[i].getReal()-z.getReal(), 2)+
					Math.pow(this.roots[i].getImaginary()-z.getImaginary(), 2));
			if((module < minRoot)) {
				index = i;
				minRoot = module;
			}
		}
		if(minRoot > treshold) {
			index = -1;
		}
		return index;
	}

}
