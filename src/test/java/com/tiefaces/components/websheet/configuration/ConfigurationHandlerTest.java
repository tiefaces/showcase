package com.tiefaces.components.websheet.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigurationHandlerTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}
	@Before
	public void setUp() throws Exception
	{
	}	
	
	@Test
	public void testIsDefinedFunction() {
		

		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		String pattern       = ((SimpleDateFormat)formatter).toPattern();
		String localPattern  = ((SimpleDateFormat)formatter).toLocalizedPattern();
		
System.out.println(" pattern = "+pattern);		
System.out.println(" localpattern = "+localPattern);		
		
		
		//String regex = "\\$.*\\{.*}";
		//  String regex = "\\$.*\\{+[^{}]+\\}";
		String regex = "\\$+[^{$]+\\{+[^{}$]+\\}";
		assertTrue("$save{departments.name}".matches(regex)); // true		
		assertTrue("$init{departments.name}".matches(regex)); // true	
		assertTrue("$widget.calendar{showOn=\"button\"}".matches(regex)); // true	
		
String newComment = "$init{departments.name}";		
String	key = newComment.substring(0, newComment.indexOf("{"));
System.out.println("key =" + key);		
		
		assertFalse("$${departments.name}".matches(regex)); // true		
		assertTrue("${departments.name}".startsWith("${")); // true		
		assertFalse("$abc}departments.name}".matches(regex)); // false		
		assertFalse("{$departments.name}".matches(regex)); // false		
		assertFalse("$init{departments.name}}".matches(regex)); // false
		
	
	}	

}
