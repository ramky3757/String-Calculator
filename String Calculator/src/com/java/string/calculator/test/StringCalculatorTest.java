package com.java.string.calculator.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.java.string.calculator.StringCalculator;

class StringCalculatorTest {
	
	@ParameterizedTest
	@ValueSource(strings = { "6,6", "10,2", "12", "3,3,6"} )
	public void testParameterizedWithValidInput(String input) {
		StringCalculator.DELIMIT = ",";
		assertEquals(12,StringCalculator.addNumbers(input));		
	}
	

	@Test	
	public void shouldReturnZeroIfInputNull() {
		assertEquals(0, StringCalculator.addNumbers(null));
	}
	
	@Test	
	public void shouldReturnZeroIfInputEmpty() {
		assertEquals(0, StringCalculator.addNumbers(""));
	}
	

	@ParameterizedTest
	@ValueSource(strings = { "20:20", "15:25"} )
	public void testDifferentDemiliter_PositiveCase1(String input) {
		StringCalculator.DELIMIT = ":";
		assertEquals(40,StringCalculator.addNumbers(input));		
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "20 20", "15 25"} )
	public void testDifferentDemiliter_PositiveCase2(String input) {
		StringCalculator.DELIMIT = " ";
		assertEquals(40,StringCalculator.addNumbers(input));		
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "20.20", "15.25"} )
	public void testDifferentDemiliter_NegativeCaseWithDot(String input) {
		StringCalculator.DELIMIT = ".";
		try {
			StringCalculator.addNumbers(input);
		} catch(NumberFormatException ne) {
			//
		}		
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "2020", "1525"} )
	public void testDifferentDemiliter_NegativeCaseWithEmpty(String input) {
		StringCalculator.DELIMIT = "";
		try {
			StringCalculator.addNumbers(input);
		} catch(NumberFormatException ne) {
			//
		}		
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "101:25:25", ":25:25"} )
	public void shouldIgnoreMaxvalOrEmpty(String input) {	
		StringCalculator.DELIMIT = ":";
		assertEquals(50,StringCalculator.addNumbers(input));
		
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = { "1a,2", "10,2b", "/1,3", "1.2.3"} )
	public void raiseExceptionifNotNumber(String input) {
		try {
			StringCalculator.addNumbers(input);
		} catch(NumberFormatException ne) {
			//
		}
	}
		
	@Test
	public void raiseExceptionOnNegativeValue() {
		try {
			StringCalculator.addNumbers("3,-3,2");
			fail("Negative Value not allowed -- Exception");
		} catch (RuntimeException re) {
			//
		}
	}
	
	@Test
	public void raiseExceptionOnPatternFail() {
		try {
			StringCalculator.DELIMIT = "?";
			StringCalculator.addNumbers("3?3?2");
			fail("PatternSyntax failure -- Exception");
		} catch (PatternSyntaxException pe) {
			//
		}
	}
	
	@Test
	public void raiseExceptionOnWrongDelimiterAndCorrectInput() {
		try {
			StringCalculator.DELIMIT = ",";
			StringCalculator.addNumbers("3_3_2");
			fail("Negative Value not allowed -- Exception");
		} catch (RuntimeException re) {
			//
		}
	}
}
