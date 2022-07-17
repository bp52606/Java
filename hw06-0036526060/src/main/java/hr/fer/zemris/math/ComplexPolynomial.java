package hr.fer.zemris.math;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Class used as a representation of complex numbers in a form : zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0, 
 * where  z0-zn are the coefficients written next to appropriate powers of z
 * 
 * @author 38591
 *
 */
public class ComplexPolynomial {
	
	// zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0

	/**
	 * An array of complex numbers(coefficients) in this polynomial.
	 * 
	 */
	
	private Complex[] factors;
	
	/**
	 * Constructor that accepts an array of complex numbers(coefficients) in this polynomial.
	 * 
	 * @param factors
	 */
	
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3.
	 * 
	 * @return order of this polynom
	 */
	
	public short order() {
		Integer order = this.factors.length-1;
		return order.shortValue();
	}
	
	/**
	 * Computes a new polynomial equal to this*p.
	 * 
	 * @param p given ComplexPolynomial to multiply this one with
	 * @return this*p
	 */
	
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Map<Integer,Complex> map = new LinkedHashMap<Integer, Complex>();
		for(int i=0;i<this.factors.length;++i) {
			for(int k=0;k<p.getFactors().length;++k) {
				if(map.containsKey(i+k)) {
					Complex complex = map.get(i+k);
					map.put(i+k,complex.add(this.factors[i].multiply(p.getFactors()[k])));
				} else {
					map.put(i+k,this.factors[i].multiply(p.getFactors()[k]));
				}
			}
		}
		List<Complex> list = new LinkedList<Complex>();
		for(Map.Entry<Integer, Complex> entry : map.entrySet()) {
			list.add(entry.getKey(), entry.getValue());
		}
		return new ComplexPolynomial(list.toArray(new Complex[list.size()]));
		
	}
	/**
	 * Returns an array of complex numbers(coefficients) in this polynomial.
	 * 
	 * @return an array of complex numbers(coefficients) in this polynomial
	 */
	
	public Complex[] getFactors() {
		return factors;
	}

	/**
	 * Computes first derivative of this polynomial; (for example, for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5).
	 *
	 * @return ComplexPolynomial representation of first derivative of this polynomial
	 */

	public ComplexPolynomial derive() {
		Complex[] complex = new Complex[this.factors.length-1];
		for(int i=1;i<this.factors.length;++i) {
			complex[i-1] = this.factors[i].multiply(new Complex(i,0));
		}
			
		return new ComplexPolynomial(complex);
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z given point for counting a value of this polynomial
	 * @return complex number value of this polynomial at given point z
	 */
	public Complex apply(Complex z) {
		Complex complex = new Complex();
		for(int i=0;i<this.factors.length;++i) {
			complex = complex.add(z.power(i).multiply(this.factors[i]));
		}
		return complex;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=this.factors.length-1;i>=0;--i) {
			if(i!=this.factors.length-1) {
				sb.append("+");
			}
			if(i>0) {
				sb.append("("+this.factors[i].toString()+")*z^"+i);
			} else {
				sb.append("("+this.factors[i].toString()+")");
			}
		}
		return sb.toString();
	}

}
