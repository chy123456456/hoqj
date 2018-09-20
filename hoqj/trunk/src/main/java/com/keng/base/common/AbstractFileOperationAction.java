package com.keng.base.common;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

import com.keng.base.utils.IOUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Title：AbstractFileOperationAction Desc：
 * @date ${date} ${time}
 */
public class AbstractFileOperationAction extends BaseAction {
    // private static final Logger logger = LoggerFactory.getLogger(AbstractFileOperationAction.class);
    protected void doExportExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        List<Map<String, Object>> list = doQuery(request, response, model);
        doExportExcelHeader(request, response);
        doExportExcelBody(list, request, response);
    }

    protected List<Map<String, Object>> doQuery(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        return list;
    }

    /**
     * 设置导出Excel文件的Http头，只要是设置下载文件的文件名和MITA信息
     */
    @SuppressWarnings("static-access")
    protected void doExportExcelHeader(HttpServletRequest request, HttpServletResponse response) {
        String fileName = getExportFileName(request);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment");
        response.setHeader("Content-Disposition", "filename=\"" + this.urlEncode(fileName) + ".xls\"");
    }

    /**
     * 导出文件的文件名
     * @return
     */
    protected String getExportFileName(HttpServletRequest request) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmsss");
        String fileName = df.format(new Date());
        String exportFileName = request.getParameter("exportFileName");
        if (StringUtils.isNotBlank(exportFileName)) {
            try {
                exportFileName = new String(exportFileName.getBytes("ISO-8859-1"), "UTF-8");
                fileName = exportFileName + "-" + fileName;
                System.out.println("文件名:" + fileName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出Excel实现。 <br>
     * <li>可选使用jxl,poi方式直接输出流到reponse,不需要导出页面。该方法返回fase;
     * <li>可选使用页面方式实现，需要返回到导出页面。该方法返回true
     * @param list
     * @param request
     * @param response
     * @return
     */
    protected boolean doExportExcelBody(List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response) {
        WritableWorkbook workbook = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            String[] headerNames = getExportTitles();
            int[] columnViews = getExportColumnViews();
            workbook = Workbook.createWorkbook(out);
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            int row = 0;
            row = doTitles(sheet, request);
            WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            WritableCellFormat cellFormat = new WritableCellFormat(font);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setVerticalAlignment(VerticalAlignment.BOTTOM);
            // 写入header
            if (headerNames != null) {
                for (int i = 0; i < headerNames.length; i++) {
                    sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
                }
                row++;
            }
            // 列宽度
            if (columnViews != null) {
                for (int i = 0; i < columnViews.length; i++) {
                    sheet.setColumnView(i, columnViews[i]);
                }
            }
            for (int index = 0; index < list.size(); index++) {
                List<String> entityData = doMarshalEntityToXls(list.get(index), index);
                for (int i = 0; i < entityData.size(); i++) {
                    sheet.addCell(new Label(i, row, entityData.get(i)));
                }
                row++;
            }
            workbook.write();
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("执行导出过程失败[" + e.getMessage() + "]");
        } finally {
            try {
                workbook.close();
            } catch (Exception e2) {
                logger.error(e2);
            }
            IOUtils.freeQuietly(out);
        }
        return false;
    }

    /**
     * 大标题
     * @param sheet
     * @return
     */
    protected int doTitles(WritableSheet sheet, HttpServletRequest request) {
        return 0;
    }

    /**
     * 返回导出文件内容的标题行，默认为空，不导出标题行
     * @return
     */
    protected String[] getExportTitles() {
        return null;
    }

    /**
     * 列的宽度
     * @return
     */
    protected int[] getExportColumnViews() {
        return null;
    }

    /**
     * 编列实体对象为List<String>待输出格式 这里为简化操作，要求改方法内完成属性的类型转换为输出String类型。如果需要对字段类型进行精确控制，可以考虑复写doExportXls方法实现
     * @param index
     * @param entity
     */
    protected List<String> doMarshalEntityToXls(Map<String, Object> map, int index) {
        // Set<String> simplePropertyNames = Reflections.getSimpleFieldNames(entity.getClass());
        // String[] propertyNames = simplePropertyNames.toArray(new String[] {});
        // return Reflections.invokeGetterToString(entity, propertyNames);
        List<String> value = new ArrayList<String>();
        for (Entry<String, Object> entry : map.entrySet()) {
            value.add(entry.getValue() + "");
        }
        return value;
    }
}
