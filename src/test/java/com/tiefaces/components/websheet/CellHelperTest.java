package com.tiefaces.components.websheet;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaRenderer;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.SharedFormula;
import org.apache.poi.ss.formula.ptg.Area3DPtg;
import org.apache.poi.ss.formula.ptg.Area3DPxg;
import org.apache.poi.ss.formula.ptg.AreaPtgBase;
import org.apache.poi.ss.formula.ptg.OperandPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.Ref3DPtg;
import org.apache.poi.ss.formula.ptg.Ref3DPxg;
import org.apache.poi.ss.formula.ptg.RefPtgBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tiefaces.components.websheet.service.CellHelper;

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

		//HSSFWorkbook wb = new HSSFWorkbook();
		Workbook wb = new XSSFWorkbook();
		XSSFEvaluationWorkbook wbWrapper = XSSFEvaluationWorkbook
				.create((XSSFWorkbook) wb);	
		CreationHelper createHelper = wb.getCreationHelper();
		XSSFSheet sheet = (XSSFSheet) wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		XSSFRow row1 = sheet.createRow((short) 0);
		// Create a cell and put a value in it.
		row1.createCell(0).setCellValue(1);
		row1.createCell(1).setCellValue("A2");
		row1.createCell(2).setCellValue(
				createHelper.createRichTextString("This is a string"));
		row1.createCell(3).setCellValue(true);

		XSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue(2);
		row2.createCell(1).setCellValue(2.2);
		row2.createCell(2).setCellValue(
				createHelper.createRichTextString("This is a 2nd string"));
		row2.createCell(3).setCellValue(true);

		XSSFRow row3 = sheet.createRow(2);
//		row.createCell(0).setCellFormula(
//				"SUM(A1:INDIRECT(\"A\" & (ROW() -1) ) )");
//		row.createCell(0).setCellFormula(
//				"SUM(A1:A2)");
		row3.createCell(0).setCellFormula(
				//"A1-(A1+A2)+A2");
				//"A1-(A1)-(A1)+A2");
				"SUM((A1:A2),(A1:A2))");
				//"SUM(A1:INDIRECT(B1))");
		row3.createCell(1).setCellValue(3.2);
		row3.createCell(2).setCellValue(
				createHelper.createRichTextString("This is a 3nd string"));
		row3.createCell(3).setCellValue(true);

		// sheet.shiftRows(1, 2, 1);

		CellHelper cellHelper = new CellHelper();
System.out.println("row 3 index = "+row3.getRowNum());		
System.out.println("row 3  object = "+sheet.getRow(2));		
		cellHelper.copyRow(wb, wbWrapper, sheet,sheet, 0, 1);
		
		System.out.println("after row 3 index = "+row3.getRowNum());		
		System.out.println("after row 3  object = "+sheet.getRow(2));		

		String formula = sheet.getRow(3).getCell(0).getCellFormula();

		System.out.println("formula =" + formula);

		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();

		evaluator.evaluateAll();

		System.out.println("value ="
				+ sheet.getRow(3).getCell(0).getNumericCellValue());

	    SharedFormula sharedFormula = new SharedFormula(SpreadsheetVersion.EXCEL2007);
		
	    Cell sourceCell = sheet.getRow(3).getCell(0);
	    Ptg[] sharedFormulaPtg = FormulaParser.parse(sourceCell.getCellFormula(), wbWrapper, FormulaType.CELL, 0);
System.out.println("before shift formula = "+listptg(sharedFormulaPtg));	    
	    Ptg[] convertedFormulaPtg = sharedFormula.convertSharedFormulas(sharedFormulaPtg, 1, 0);
System.out.println("after shift formula = "+listptg(convertedFormulaPtg));	    
	    String newformula = FormulaRenderer.toFormulaString(wbWrapper, convertedFormulaPtg);
System.out.println("newformula = "+newformula);	    
		
		assertEquals("SUM(A1:A3)", formula);

	}
	

	private String listptg(Ptg[] ptglist) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Ptg ptg: ptglist) {
			sb.append(ptg);
			sb.append(" ptgclass: "+ptg.getPtgClass()+" ");
			if (ptg instanceof RefPtgBase) {
				sb.append("RefPtgBase");
			} else if (ptg instanceof AreaPtgBase) {
				sb.append("AreaPtgBase");
			} else if (ptg instanceof OperandPtg) {
				sb.append("OperandPtg");
			}
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
}
