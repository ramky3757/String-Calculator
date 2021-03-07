package com.java.string.calculator;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class StringCalculator {	
	
	static Logger logger = Logger.getLogger(StringCalculator.class.getName());
	private static Pattern isNumPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private static int DEFAULT_VAL = 0;
	private static int MAX_VAL = 101;
	public static String DELIMIT = ",";

	public static void main(String[] args) {
		
			double output = addNumbers("3,3,2");
			System.out.println(output);
	}
	
			/*Return sum of numbers provided in the input
			 * return 0 if the input is empty or null
			 */
		public static double addNumbers(String inputString) {
			double total = 0;
			
			try {
				if (inputString != null && !inputString.isEmpty()) {
					
					// split with delimiter, validateInput and return sum of Numbers
					logger.log(Level.INFO, "Input received");
					total = splitWithDelimiterAndReturnSum(inputString);
					
				} else {					
					logger.log(Level.SEVERE, "Input is either Empty or null.");
					return DEFAULT_VAL;
				}
				
			} catch(PatternSyntaxException e) {
				logger.log(Level.SEVERE, "Verify the Input and Delimiter");
				throw new PatternSyntaxException("Verify the Input and Delimiter", DELIMIT, 0);
			}
		
			return total;
		}

		/* Check if Empty and Dot are provided as delimiters, if found throw NumberFormatException
		 * Verify If the Input String is Number, if not throw NumberFormatException
		 * Verify If the Input number is negative value, if yes - throw RunTimeException
		 * If any number is above 100 or Empty, if found - ignore in calculation		 * 
		 */
		private static double splitWithDelimiterAndReturnSum(String inputString) throws PatternSyntaxException{
			
			if(DELIMIT.equalsIgnoreCase(".") || DELIMIT.equalsIgnoreCase("")) {	
				logger.log(Level.SEVERE, "Not a valid delimiter");
				throw new NumberFormatException("Exception!! Please check the provided delimiter");
			} 
							
			List<String> strInput = Arrays.asList(inputString.split(DELIMIT));
			
			logger.log(Level.INFO, "Proceeding with validatingInput and sum the input");
			double total = strInput.stream().filter(string -> validateInput(string.trim())).map(s -> Double.parseDouble(s)).reduce((double)0, Double::sum);
			
			return total;

		}
		
	// To ignore maxValue, Empty string and validate
	public static boolean validateInput(String str) {

		if (!str.isEmpty()) {

			if (!isNumPattern.matcher(str).matches()) {
				// Input is not Number
				logger.log(Level.SEVERE, "Input is not a Number", str);
				throw new NumberFormatException("Exception!! Verify the input and delimiter provided");

			} else if (Double.parseDouble(str) < 0) {
				// Negative value found, throw Exception
				logger.log(Level.SEVERE, "Negative values are not allowed", str);
				throw new RuntimeException("Exception!! Negative values are not allowed");

			} else if (Double.parseDouble(str) < MAX_VAL) {
				return true;
			}

		}

		return false;

	}
}
