package com.tiefaces.components.websheet;


import static org.junit.Assert.*;

import java.io.IOException;

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


public class CellHelperTest {
	
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
	public void testRowShiftWithFormulaChange() throws IOException {
		
		  Workbook wb = new HSSFWorkbook();
		    //Workbook wb = new XSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("new sheet");

		    // Create a row and put some cells in it. Rows are 0 based.
		    Row row = sheet.createRow((short)0);
		    // Create a cell and put a value in it.
		    row.createCell(0).setCellValue(1);
		    row.createCell(1).setCellValue(1.2);
		    row.createCell(2).setCellValue(
		         createHelper.createRichTextString("This is a string"));
		    row.createCell(3).setCellValue(true);
		    
		    row = sheet.createRow(1);
		    row.createCell(0).setCellValue(2);
		    row.createCell(1).setCellValue(2.2);
		    row.createCell(2).setCellValue(
		         createHelper.createRichTextString("This is a 2nd string"));
		    row.createCell(3).setCellValue(true);
		    
		    row = sheet.createRow(2);
		    row.createCell(0).setCellFormula("SUM(A1:INDIRECT(\"A\" & (ROW() -1) ) )");
		    row.createCell(1).setCellValue(3.2);
		    row.createCell(2).setCellValue(
		         createHelper.createRichTextString("This is a 3nd string"));
		    row.createCell(3).setCellValue(true);
		    
		    
		    //sheet.shiftRows(1, 2, 1);
		    
		    TieWebSheetCellHelper cellHelper = new TieWebSheetCellHelper();
		    cellHelper.copyRow(wb, sheet, 0, 1);
		    
		    String formula = sheet.getRow(3).getCell(0).getCellFormula();
		    
		    System.out.println("formula ="+formula);
		    
		    FormulaEvaluator evaluator = wb.getCreationHelper()
	                .createFormulaEvaluator();
		    
		    evaluator.evaluateAll();

		    System.out.println("value ="+sheet.getRow(3).getCell(0).getNumericCellValue());

		    assertEquals("SUM(A1:A3)", formula );
		    
		    
		
		
	}
	

	
	
}
