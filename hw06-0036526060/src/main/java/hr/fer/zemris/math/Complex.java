package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a representation of a complex number
 * 
 * @author 38591
 *
 */

public class Complex {

	/**
	 * Real value of a complex number
	 * 
	 */
	private double real;
	/**
	 * Imaginary value of a complex number
	 * 
	 */
	private double imaginary;
	/**
	 * Complex number with real and imaginary value set to 0
	 * 
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Complex number with real value set to 1 and imaginary value set to 0
	 * 
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Complex number with real value set to -1 and imaginary value set to 0
	 * 
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Complex number with real value set to 0 and imaginary value set to 1
	 * 
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Complex number with real value set to 0 and imaginary value set to -1
	 * 
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Constructor that initializes complex number with both real and imaginary value set to 0
	 * 
	 */
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
	}
	/**
	 * Constructor that initializes complex number and sets it's values using given arguments
	 * 
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}
		
	/**
	 * Returns module of a complex number
	 * 
	 * @return module of a complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2)+Math.pow(imaginary, 2));
	}
	
	/**
	 * Returns complex number as a result of multiplication of this complex number and the one given as an argument
	 * 
	 * @param c given complex number
	 * @return complex number representation of multiplication of this complex number and c
	 */
	public Complex multiply(Complex c) {
		double real = this.real * c.getReal() - this.imaginary*c.getImaginary();
		double imaginary = this.real*c.getImaginary() + this.imaginary*c.getReal();
		return new Complex(real,imaginary);
	}
	
	/**
	 * Returns complex number as a result of a division of this complex number and the one given as an argument
	 * 
	 * @param c given complex number
	 * @return complex number representation of division of this complex number and c
	 */
	public Complex divide(Complex c) {
		Complex x2 = new Complex(c.getReal(), -c.getImaginary());
		Complex numerator = this.multiply(x2);
		double denominator = Math.pow(c.module(),2);
		double real = numerator.getReal()/denominator;
		double imaginary = numerator.getImaginary()/denominator;
		return new Complex(real,imaginary);
	}
	
	/**
	 * Returns complex number as a result of a sum of this complex number and the one given as an argument
	 * 
	 * @param c given complex number
	 * @return complex number representation of a sum of this complex number and c
	 */
	public Complex add(Complex c) {
		return new Complex(c.getReal()+this.real, this.imaginary+c.getImaginary());
	}
	
	/**
	 * Returns complex number as a result of a subtraction of this a complex number given as an argument from this complex number
	 * 
	 * @param c given complex number
	 * @return complex number representation of a subtraction of this a complex number given as an argument from this complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real-c.getReal(), this.imaginary-c.getImaginary());
	}
	
	/**
	 * Returns negative value of this complex number
	 * 
	 * @return negative value of this complex number
	 */
	public Complex negate() {
		return new Complex(-this.real,-this.imaginary);
	}
	
	/**
	 * Returns this^n, n being a non-negative integer
	 * 
	 * @param n a non-negative integer given as a power value for this complex number
	 * @return this^n
	 */
	public Complex power(int n) {
		double r = this.module();
		double angle = Math.atan2(this.imaginary,this.real);
		double real = Math.pow(r, n) * Math.cos(n*angle);
		double imaginary = Math.pow(r,n) * Math.sin(n*angle);
		return new Complex(real,imaginary);
		
	}
	
	/**
	 * Returns n-th root of this, n being a positive integer
	 * 
	 * @param n a positive integer given as a root value for this complex number
	 * @return n-th root of this complex number
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new LinkedList<Complex>();
		
		double angle = Math.atan2(this.imaginary,this.real);
		double r = Math.pow(this.module(), 1/n);
		
		for(int k=0;k<n;++k) {
			list.add(new Complex(r*(Math.cos((angle + 2*Math.PI*k)/n)),r* Math.sin((angle + 2*Math.PI*k))/n));
		}
		return list;
	}
	
	@Override
	public String toString() {
		if(this.imaginary>=0) {
			return String.format("%s+i%s",this.real,this.imaginary);
		} else {
			return String.format("%s-i%s",this.real,Math.abs(this.imaginary));
		}
	}

	/**
	 * Returns real value of this number
	 * 
	 * @return real value of this number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary value of this number
	 * 
	 * @return imaginary value of this number
	 */
	public double getImaginary() {
		return imaginary;
	}

}
