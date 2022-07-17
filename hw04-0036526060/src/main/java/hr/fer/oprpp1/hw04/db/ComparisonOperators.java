package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of {@link IComparisonOperator}
 * Class that represents implementation of functions that operators do
 * 
 * @author 38591
 *
 */

public class ComparisonOperators {
	
		/**
		 * Defines how operator < compares objects
		 * 
		 */
	
		public static final IComparisonOperator LESS = (s1, s2) -> {return (s1.compareTo(s2) < 0);};
		
		/**
		 * Defines how operator <= compares objects
		 * 
		 */
		
		public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> {return (s1.compareTo(s2) <= 0);};  
		
		/**
		 * Defines how operator > compares objects
		 * 
		 */
		
		public static final IComparisonOperator GREATER = (s1, s2) -> {return (s1.compareTo(s2) > 0);};
		
		/**
		 * Defines how operator >= compares objects
		 * 
		 */
		
		public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> {return (s1.compareTo(s2) >= 0);};
		
		/**
		 * Defines how operator == compares objects
		 * 
		 */
		
		public static final IComparisonOperator EQUALS = (s1, s2) -> {return (s1.compareTo(s2) == 0);};
		
		/**
		 * Defines how operator != compares objects
		 * 
		 */
		
		public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> {return (s1.compareTo(s2) != 0);};
		
		/**
		 * Defines how operator LIKE compares objects
		 * 
		 */
		
		public static final IComparisonOperator LIKE = new IComparisonOperator() {
			
		@Override
		public boolean satisfied(String value1, String value2) {
				String[] split = value2.split("\\*");
				try {	
					if(split.length<=2) {
						if(split[0].equals(" ")) {
							return value1.endsWith(split[1]);
						}
						if(split.length == 1) {
							return value1.startsWith(split[0]);
						}
						
					} else {
					
						throw new IllegalArgumentException();
					}
					
				} catch(IllegalArgumentException ex) {
					System.out.println("Only one \\* is allowed");
				}
				
				return value1.startsWith(split[0]) && 
						value1.substring(value1.indexOf(split[0]) + split[0].length()).endsWith(split[1]);
			}
		};

}
