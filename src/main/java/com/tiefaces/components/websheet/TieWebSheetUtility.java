package com.tiefaces.components.websheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFColor;

public final class TieWebSheetUtility {
	
	private static boolean debug = false;

	private static void debug(String msg) {
		if (debug) {
			System.out.println("TieWebSheetUtility: " + msg);
		}
	}
	
	
	public static String GetExcelColumnName(int number) {
		String converted = "";
		// Repeatedly divide the number by 26 and convert the
		// remainder into the appropriate letter.
		while (number >= 0) {
			int remainder = number % 26;
			converted = (char) (remainder + 'A') + converted;
			number = (number / 26) - 1;
		}

		return converted;
	}

	public static Cell getCellByReference(String cellRef, Sheet sheet) {

		// Sheet sheet =
		// wb.getSheet(sheetConfigMap.get(currentTabName).getSheetName());
		Cell c = null;
		try {
			CellReference ref = new CellReference(cellRef);
			Row r = sheet.getRow(ref.getRow());
			if (r != null) {
				c = r.getCell(ref.getCol(), Row.CREATE_NULL_AS_BLANK);
			}
		} catch (Exception ex) {
			// use log.debug because mostly it's expected
			debug("WebForm WebFormHelper getCellByReference cellRef = "
					+ cellRef + "; error = " + ex.getLocalizedMessage());
		}
		return c;
	}	
	

    // Each cell conatins a fixed number of co-ordinate points; this number
    // does not vary with row height or column width or with font. These two
    // constants are defined below.
    public static final int TOTAL_COLUMN_COORDINATE_POSITIONS = 1023; // MB
    public static final int TOTAL_ROW_COORDINATE_POSITIONS = 255;     // MB
    // The resoultion of an image can be expressed as a specific number
    // of pixels per inch. Displays and printers differ but 96 pixels per
    // inch is an acceptable standard to beging with.
    public static final int PIXELS_PER_INCH = 96;                     // MB
    // Cnstants that defines how many pixels and points there are in a
    // millimetre. These values are required for the conversion algorithm.
    public static final double PIXELS_PER_MILLIMETRES = 3.78;         // MB
    public static final double POINTS_PER_MILLIMETRE = 2.83;          // MB
    // The column width returned by HSSF and the width of a picture when
    // positioned to exactly cover one cell are different by almost exactly
    // 2mm - give or take rounding errors. This constant allows that
    // additional amount to be accounted for when calculating how many
    // celles the image ought to overlie.
    public static final double CELL_BORDER_WIDTH_MILLIMETRES = 2.0D;  // MB
    public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;
    public static final int UNIT_OFFSET_LENGTH = 7;
    public static final int[] UNIT_OFFSET_MAP = new int[]
        { 0, 36, 73, 109, 146, 182, 219 };
	public static final short EXCEL_ROW_HEIGHT_FACTOR = 20;
	public static final int EMU_PER_MM = 36000;
	public static final int EMU_PER_POINTS = 12700;
    /**
    * pixel units to excel width units(units of 1/256th of a character width)
    * @param pxs
    * @return
    */
    public static short pixel2WidthUnits(int pxs) {
        short widthUnits = (short) (EXCEL_COLUMN_WIDTH_FACTOR *
                (pxs / UNIT_OFFSET_LENGTH));
        widthUnits += UNIT_OFFSET_MAP[(pxs % UNIT_OFFSET_LENGTH)];
        return widthUnits;
    }

    /**
     * excel width units(units of 1/256th of a character width) to pixel
     * units.
     *
     * @param widthUnits
     * @return
     */
    public static int widthUnits2Pixel(int widthUnits) {
        int pixels = (widthUnits / EXCEL_COLUMN_WIDTH_FACTOR)
                * UNIT_OFFSET_LENGTH;
        int offsetWidthUnits = widthUnits % EXCEL_COLUMN_WIDTH_FACTOR;
        pixels += Math.round(offsetWidthUnits /
                ((float) EXCEL_COLUMN_WIDTH_FACTOR / UNIT_OFFSET_LENGTH));
        return pixels;
    }
    
    
	public static int heightUnits2Pixel(short heightUnits) {
		int pixels = (heightUnits / EXCEL_ROW_HEIGHT_FACTOR);
		int offsetHeightUnits = heightUnits % EXCEL_ROW_HEIGHT_FACTOR;
		System.out.println(" heightunit2pixel heightunit = "+heightUnits+" pixels1 = "+pixels+" offsetHeightUnits="+offsetHeightUnits);		
		pixels += Math.round((float) offsetHeightUnits
				/ ((float) EXCEL_COLUMN_WIDTH_FACTOR / UNIT_OFFSET_LENGTH / 2));
		System.out.println(" pixels2 = "+pixels);		
		pixels += (Math.floor(pixels / 14 )+1) * 4;
		System.out.println(" Math.round (pixels / 14 ) = "+Math.round (pixels / 14 )+" final pixels = "+pixels);		
		
		return pixels;
	}	

    /**
     * Convert Excels width units into millimetres.
     *
     * @param widthUnits The width of the column or the height of the
     *                   row in Excels units.
     * @return A primitive double that contains the columns width or rows
     *         height in millimetres.
     */
    public static double widthUnits2Millimetres(short widthUnits) {
        return(widthUnits2Pixel(widthUnits) /
               PIXELS_PER_MILLIMETRES);
    }
    
    

    /**
     * Convert into millimetres Excels width units..
     *
     * @param millimetres A primitive double that contains the columns
     *                    width or rows height in millimetres.
     * @return A primitive int that contains the columns width or rows
     *         height in Excels units.
     */
    public static int millimetres2WidthUnits(double millimetres) {
        return(pixel2WidthUnits((int)(millimetres *
                PIXELS_PER_MILLIMETRES)));
    }
    
    public static int pointsToPixels(double points) {
		return (int) Math.round(points / 72D * PIXELS_PER_INCH);
	}
    
    public static double pointsToMillimeters(double points) {
    	return points / 72D * 25.4;
    }
    
	   public static short[] getTripletFromXSSFColor(XSSFColor xssfColor) {
		   
		   short[] rgbfix = {256,256,256};
	    	if (xssfColor != null) {
			   byte[] rgb = xssfColor.getRgb();
			   // Bytes are signed, so values of 128+ are negative!
			   // 0: red, 1: green, 2: blue
			   rgbfix[0] = (short) ((rgb[0] < 0) ? (rgb[0] + 256) : rgb[0]);
			   rgbfix[1] = (short) ((rgb[1] < 0) ? (rgb[1] + 256) : rgb[1]);
			   rgbfix[2] = (short) ((rgb[2] < 0) ? (rgb[2] + 256) : rgb[2]);
			 }		    		
		   return rgbfix;
	   }
	   
	   

// Below are utility functions for colors searched from internet. Maybe could used for future.   
//	   public static String getColorHexString(Color color)
//	   {
//	      if (color instanceof HSSFColor)
//	      {
//	         return getHSSFColorHexString((HSSFColor) color);
//	      }
//	      else if (color instanceof XSSFColor)
//	      {
//	         return getXSSFColorHexString((XSSFColor) color);
//	      }
//	      else if (color == null)
//	      {
//	         return "null";
//	      }
//	      else
//	      {
//	         throw new IllegalArgumentException("Unexpected type of Color: " + color.getClass().getName());
//	      }
//	   }
//
//	   /**
//	    * Get the hex string for a <code>HSSFColor</code>.  Moved from test code.
//	    * @param hssfColor A <code>HSSFColor</code>.
//	    * @return The hex string.
//	    */
//	   private static String getHSSFColorHexString(HSSFColor hssfColor)
//	   {
//		   
//		    if (hssfColor == null) return "000000";
//	      short[] shorts = hssfColor.getTriplet();
//	      StringBuilder hexString = new StringBuilder();
//	      for (short s : shorts)
//	      {
//	         String twoHex = Integer.toHexString(0x000000FF & s);
//	         if (twoHex.length() == 1)
//	            hexString.append('0');
//	         hexString.append(twoHex);
//	      }
//	      return hexString.toString();
//	   }
//
//
//	   /**
//	    * Get the hex string for a <code>XSSFColor</code>.
//	    * @param xssfColor A <code>XSSFColor</code>.
//	    * @return The hex string.
//	    */ 
//	   private static String getXSSFColorHexString(XSSFColor xssfColor)
//	   {
//	      if (xssfColor == null)
//	         return "000000";
//	      
//	      byte[] bytes;
//	      // As of Apache POI 3.8, there are Bugs 51236 and 52079 about font
//	      // color where somehow black and white get switched.  It appears to
//	      // have to do with the fact that black and white "theme" colors get
//	      // flipped.  Be careful, because XSSFColor(byte[]) does NOT call
//	      // "correctRGB", but XSSFColor.setRgb(byte[]) DOES call it, and so
//	      // does XSSFColor.getRgb(byte[]).
//	      // The private method "correctRGB" flips black and white, but no
//	      // other colors.  However, correctRGB is its own inverse operation,
//	      // i.e. correctRGB(correctRGB(rgb)) yields the same bytes as rgb.
//	      // XSSFFont.setColor(XSSFColor) calls "getRGB", but
//	      // XSSFCellStyle.set[Xx]BorderColor and
//	      // XSSFCellStyle.setFill[Xx]Color do NOT.
//	      // Solution: Correct the font color on the way out for themed colors
//	      // only.  For unthemed colors, bypass the "correction".
//	      if (xssfColor.getCTColor().isSetTheme())
//	         bytes = xssfColor.getRgb();
//	      else
//	         bytes = xssfColor.getCTColor().getRgb();
//	      // End of workaround for Bugs 51236 and 52079.
//	      if (bytes == null)
//	      {
//	         // Indexed Color - like HSSF
//	         HSSFColor hColor = ExcelColor.getHssfColorByIndex(xssfColor.getIndexed());
//	         if (hColor != null)
//	            return getHSSFColorHexString(ExcelColor.getHssfColorByIndex(xssfColor.getIndexed()));
//	         else
//	            return "000000";
//	      }
//	      if (bytes.length == 4)
//	      {
//	         // Lose the alpha.
//	         bytes = new byte[] {bytes[1], bytes[2], bytes[3]};
//	      }
//	      StringBuilder hexString = new StringBuilder();
//	      for (byte b : bytes)
//	      {
//	         String twoHex = Integer.toHexString(0x000000FF & b);
//	         if (twoHex.length() == 1)
//	            hexString.append('0');
//	         hexString.append(twoHex);
//	      }
//	      return hexString.toString();
//	   }
//	
//	   /**
//	    * <p>Returns a <code>String</code> formatted in the following way:</p>
//	    *
//	    * <code>" at " + cellReference</code>
//	    *
//	    * <p>e.g. <code>" at Sheet2!C3"</code>.</p>
//	    * @param cell The <code>Cell</code>
//	    * @return The formatted location string.
//	    * @since 0.7.0
//	    */
//	   public static String getCellLocation(Cell cell)
//	   {
//	      if (cell == null)
//	         return "";
//	      return " at " + new CellReference(
//	         cell.getSheet().getSheetName(), cell.getRowIndex(), cell.getColumnIndex(), false, false).toString();
//	   }
//	   
//	   /**
//	    * Determines the proper POI <code>Color</code>, given a string value that
//	    * could be a color name, e.g. "aqua", or a hex string, e.g. "#FFCCCC".
//	    *
//	    * @param workbook A <code>Workbook</code>, used only to determine whether
//	    *    to create an <code>HSSFColor</code> or an <code>XSSFColor</code>.
//	    * @param value The color value, which could be one of the 48 pre-defined
//	    *    color names, or a hex value of the format "#RRGGBB".
//	    * @return A <code>Color</code>, or <code>null</code> if an invalid color
//	    *    name was given.
//	    */
//	   public static Color getColor(Workbook workbook, String value)
//	   {
//	      debug("getColor: " + value);
//	      Color color = null;
//	      if (workbook instanceof HSSFWorkbook)
//	      {
//	         // Create an HSSFColor.
//	         if (value.startsWith("#"))
//	         {
//	            ExcelColor best = ExcelColor.AUTOMATIC;
//	            int minDist = 255 * 3;
//	            String strRed = value.substring(1, 3);
//	            String strGreen = value.substring(3, 5);
//	            String strBlue = value.substring(5, 7);
//	            int red   = Integer.parseInt(strRed, 16);
//	            int green = Integer.parseInt(strGreen, 16);
//	            int blue  = Integer.parseInt(strBlue, 16);
//	            // Hex value.  Find the closest defined color.
//	            for (ExcelColor excelColor : ExcelColor.values())
//	            {
//	               int dist = excelColor.distance(red, green, blue);
//	               if (dist < minDist)
//	               {
//	                  best = excelColor;
//	                  minDist = dist;
//	               }
//	            }
//	            color = best.getHssfColor();
//	            debug("  Best HSSFColor found: " + color);
//	         }
//	         else
//	         {
//	            // Treat it as a color name.
//	            try
//	            {
//	               ExcelColor excelColor = ExcelColor.valueOf(value);
//	               if (excelColor != null)
//	                  color = excelColor.getHssfColor();
//	               debug("  HSSFColor name matched: " + value);
//	            }
//	            catch (IllegalArgumentException e)
//	            {
//	               debug("  HSSFColor name not matched: " + e.toString());
//	            }
//	         }
//	      }
//	      else // XSSFWorkbook
//	      {
//	         // Create an XSSFColor.
//	         if (value.startsWith("#") && value.length() == 7)
//	         {
//	            // Create the corresponding XSSFColor.
//	            color = new XSSFColor(new byte[] {
//	               Integer.valueOf(value.substring(1, 3), 16).byteValue(),
//	               Integer.valueOf(value.substring(3, 5), 16).byteValue(),
//	               Integer.valueOf(value.substring(5, 7), 16).byteValue()
//	            });
//	            debug("  XSSFColor created: " + color);
//	         }
//	         else
//	         {
//	            // Create an XSSFColor from the RGB values of the desired color.
//	            try
//	            {
//	               ExcelColor excelColor = ExcelColor.valueOf(value);
//	               if (excelColor != null)
//	               {
//	                  color = new XSSFColor(new byte[]
//	                     {(byte) excelColor.getRed(), (byte) excelColor.getGreen(), (byte) excelColor.getBlue()}
//	                  );
//	               }
//	              debug("  XSSFColor name matched: " + value);
//	            }
//	            catch (IllegalArgumentException e)
//	            {
//	              debug("  XSSFColor name not matched: " + e.toString());
//	            }
//	         }
//	      }
//	      return color;
//	   }	   

}
