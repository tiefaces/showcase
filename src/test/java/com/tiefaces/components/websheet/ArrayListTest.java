package com.tiefaces.components.websheet;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ArrayListTest {
	
	@BeforeClass
	public static void setUpLibrary() throws Exception {
	}
	
	@Before
	public void startUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testArrayList() throws IOException {
		
		ArrayList<Integer> str=new ArrayList<Integer>();
	    str.add(3,3); 
	    str.add(2,2);
	    str.add(1,1);
	    str.add(0,0);
System.out.println("str="+str);
//Result = [0, 1, 2, 3]

assertEquals(str.get(0),Integer.valueOf(0));
		
		
	}
	

	
	
}
