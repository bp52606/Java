package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;


import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	JPanel p = new JPanel();
	
	@BeforeEach
	void setUp() throws Exception {
		p = new JPanel(new CalcLayout(3));

	}

	@Test
	void testAddComponentInBounds() {
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,8"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "-3,4"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "6,3"));
	}
	
	@Test
	void testForbidAddingComponent() {
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,2"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,3"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,4"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,5"));
	}
	
	@Test
	void testForbidMultipleEqualComponents() {
		p.add(new JLabel("x"), "2,5");
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "2,5"));
	}
	
	@Test
	void testParsing() {
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "2"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "2,"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "2,5,6"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "a,6"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "4fdh"));
	}
}
