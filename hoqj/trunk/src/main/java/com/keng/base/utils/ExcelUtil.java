package com.keng.base.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void parseExcelToList(File excel){
        try {
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);

                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                System.out.println(cell.toString());
                            }
                        }
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
//package com.keng.base.utils;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import jxl.Cell;
//import jxl.CellType;
//import jxl.DateCell;
//import jxl.LabelCell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.write.DateTime;
//import jxl.write.Label;
//import jxl.write.Number;
//import jxl.write.WritableCell;
//
//import org.springframework.web.multipart.MultipartFile;
//
//public class ExcelUtil {
//    public static Sheet initExcel(MultipartFile upload) {
//        Workbook rwb = null;
//        Sheet sheet = null;
//        try {
//            InputStream is = upload.getInputStream();
//            rwb = Workbook.getWorkbook(is);
//            sheet = rwb.getSheet(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sheet;
//    }
//
//    /**
//     * 读取excle数据进入excelValue数组中
//     */
//    public static String[][] readExcel(Sheet sheet) {
//        String[][] excelValue = new String[sheet.getRows()][sheet.getColumns()];
//        for (int i = 0; i < sheet.getRows(); i++) {
//            for (int j = 0; j < sheet.getColumns(); j++) {
//                Cell cell = sheet.getCell(j, i);
//                if ("".equals(cell.getContents().toString().trim())) {
//                    excelValue[i][j] = "";
//                }
//                if (cell.getType() == CellType.LABEL) {
//                    LabelCell lablecell = (LabelCell) cell;
//                    excelValue[i][j] = lablecell.getString().trim();
//                } else if (cell.getType() == CellType.NUMBER) {
//                    excelValue[i][j] = cell.getContents();
//                } else if (cell.getType() == CellType.DATE) {
//                    DateCell datcell = (DateCell) cell;
//                    Date excelDate = null;
//                    excelDate = datcell.getDate();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    excelValue[i][j] = format.format(excelDate);
//                } else {
//                    excelValue[i][j] = cell.getContents().toString().trim();
//                }
//            }
//        }
//        return excelValue;
//    }
//
//    /**
//     * 根据提供的【列标】、【行标】、【对象值】构建一个Excel列对象。
//     * 
//     * @param beginRow 【行标】
//     * @param beginColumn 【列标】
//     * @param obj 【对象值】
//     * @return
//     */
//    public static WritableCell getWritableCellByObject(int beginRow, int beginColumn, Object obj) {
//        WritableCell cell = null;
//        if (obj == null) return new Label(beginColumn, beginRow, "");
//        if (obj.getClass().getName().equals(String.class.getName())) {
//            cell = new Label(beginColumn, beginRow, StringUtils.filterHTML(obj.toString()));
//        } else if (obj.getClass().getName().equals(int.class.getName()) || obj.getClass().getName().equals(Integer.class.getName())) {
//            // jxl.write.Number
//            cell = new Number(beginColumn, beginRow, Integer.parseInt(obj.toString()));
//        } else if (obj.getClass().getName().equals(float.class.getName()) || obj.getClass().getName().equals(Float.class.getName())) {
//            cell = new Number(beginColumn, beginRow, Float.parseFloat(obj.toString()));
//        } else if (obj.getClass().getName().equals(double.class.getName()) || obj.getClass().getName().equals(Double.class.getName())) {
//            cell = new Number(beginColumn, beginRow, Double.parseDouble(obj.toString()));
//        } else if (obj.getClass().getName().equals(long.class.getName()) || obj.getClass().getName().equals(Long.class.getName())) {
//            cell = new Number(beginColumn, beginRow, Long.parseLong(obj.toString()));
//        } else if (obj.getClass().getName().equals(Date.class.getName())) {
//            cell = new DateTime(beginColumn, beginRow, (Date) obj);
//        } else {
//            cell = new Label(beginColumn, beginRow, StringUtils.filterHTML(obj.toString()));
//        }
//        return cell;
//    }
//}
