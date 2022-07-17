package hr.fer.oprpp1.hw04.db;
/**
 * Class representing all information about a student
 * 
 * @author 38591
 *
 */
public class StudentRecord {
	
	/**
	 * String value of student's identification number
	 * 
	 */
	private String JMBAG;
	
	/**
	 * String value of student's name
	 * 
	 */
	
	private String name;
	
	/**
	 * String surname of student's surname
	 * 
	 */
	
	private String surname;
	
	/**
	 * Integer value of student's grade
	 * 
	 */
	
	private int grade;
	
	
	/**
	 * Constructor receiving information about a student: JMBAG, name, surname and grade
	 * 
	 * @param JMBAG String value of identification number
	 * @param name String value of student's name
	 * @param surname String value of student's surname
	 * @param grade int value of student's grade
	 */
	
	public StudentRecord(String JMBAG, String name, String surname, int grade) {
		this.JMBAG = JMBAG;
		this.name = name;
		this.surname = surname;
		this.grade = grade;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StudentRecord) {
			return this.getJMBAG().equals(((StudentRecord)obj).getJMBAG());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(this.getJMBAG());
	}
	
	/**
	 * Getter for JMBAG
	 * 
	 * @return String value of JMBAG
	 */
	
	public String getJMBAG() {
		return this.JMBAG;
	}
	
	/**
	 * Getter for a grade
	 * 
	 * @return integer value of a grade
	 */
	
	public int getGrade() {
		return grade;
	}

	/**
	 * 
	 * Getter for a name
	 * 
	 * @return String value of a name
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * Setter for a name
	 * 
	 * @param name String value of a name
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Getter for a surname
	 * 
	 * @return String value of a surname
	 */
	
	public String getSurname() {
		return surname;
	}

	/**
	 * 
	 * Setter for a surname
	 * 
	 * @param surname String value of a surname
	 */
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	
	/**
	 * Setter for a JMBAG
	 * 
	 * @param JMBAG String value of a JMBAG
	 */
	
	public void setJMBAG(String JMBAG) {
		this.JMBAG = JMBAG;
	}

	/**
	 * Setter for a grade
	 * 
	 * @param grade integer value of a grade
	 */
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
