package com.keng.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.keng.base.common.Constants;

public class CommonwealthBankUtil {
  public void generate(String htmlStr, OutputStream out) throws Exception {
    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(htmlStr);
    renderer.getSharedContext().setBaseURL(Constants.UPLOAD_PATH_PARENT + "/assets/biz-logic/images/");
    renderer.layout();
    try {
      renderer.createPDF(out);
    } catch (Exception e) {
      e.printStackTrace();
    }
    out.close();
  }

  @SuppressWarnings("unused")
public void generatePDF(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> listMap) {
    try {
      StringBuffer html = new StringBuffer();
      String orderno = "";
      for (int i = 0; i < listMap.size(); i++) {
        orderno += listMap.get(i).get("orderno");
      }
      String outputFile = "D:\\" + listMap.get(0).get("orderno") + ".pdf";
      OutputStream out = new FileOutputStream(outputFile);
      // DOCTYPE 必需写否则类似于 这样的字符解析会出现错误
      html.append(
          "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
      html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
      html.append("<head>");
      html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
      html.append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>");
      html.append("</head>");
      html.append("<body>");
      html.append("<p>");
      // 完成日期
      html.append("<span style='font-size:11.0pt; font-family:Arial,sans-serif;' lang='EN-US'>"
          + listMap.get(0).get("time") + "</span>");
      html.append(
          "<span style='font-size:13.0pt;margin-left: 200px;font-family:Arial,sans-serif;' lang='EN-US'>NetBank-Receipt</span>");
      html.append("</p>");
      html.append("<br></br>");
      html.append("<p>");
      html.append(
          "<span style='font-size:12.0pt;margin-left:20px;font-family:Arial,sans-serif;' lang='EN-US'>Commonwealth&nbsp;Bank<img src='bank-logo.png' width='30px' height='30px'/></span>");
      html.append("</p>");
      html.append("<br></br>");
      html.append("<p>");
      html.append(
          "<span style='font-size:12.0pt;margin-left:32px;font-family:Arial,sans-serif;' lang='EN-US'>Receipt</span>");
      html.append("</p>");
      // 总金额
      html.append("<p>");
      html.append(
          "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US' >Amount</span>");
      html.append("<br></br>");
      html.append("<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>" + "$"
          + listMap.get(0).get("amount") + "&nbsp;AUD</span>");
      html.append("</p>");
      // 订单交易明细
      html.append("<p>");
      html.append(
          "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;'>Transaction Details</span>");
      for (int i = 0; i < listMap.size(); i++) {
        html.append("<br></br>");
        html.append(
            "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;'>SupaytechOrderNumber  "
                + listMap.get(i).get("orderno") + "   $" + listMap.get(i).get("money") + "&nbsp;AUD</span>");
        html.append("<br></br>");
      }
      html.append("</p>");
      html.append("<p>");
      html.append(
          "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>To</span>");
      html.append("<br></br>");
      String bsbName = (listMap.get(0).get("tobsbname").toString()).replaceAll("&", "&amp;");
      html.append("<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>"
          + bsbName + "</span>");
      html.append("<br></br>");
      html.append("<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>"
          + listMap.get(0).get("tobsb") + "</span>");
      html.append("<br></br>");
      // toacc
      html.append("<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>"
          + listMap.get(0).get("toacc") + "</span>");
      html.append("<br></br>");
      html.append("<span style='font-size:11.0pt;margin-left:42px;'>From Supay Technology Pty Ltd</span>");
      // tobsb
      html.append(
          "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>Commonwealth Bank of Australia</span>");
      html.append("<br></br>");
      html.append("</p>");
      html.append("<p>");
      html.append(
          "<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>When</span>");
      html.append("<br></br>");
      // 交易完成时间
      html.append("<span style='font-size:11.0pt;margin-left:42px;font-family:Arial,sans-serif;' lang='EN-US'>"
          + listMap.get(0).get("donetime") + " Sydney/Melbourne time</span>");
      html.append("</p>");
      html.append("<br></br>");
      html.append("<br></br>");
      html.append("<br></br>");
      html.append("<br></br>");
      html.append("</body></html>");
      generate(html.toString(), out);
      // 要实现另存为下载,必须满足两个条件：导入commons-upload.jar包，表单提交
      OutputStream out2 = response.getOutputStream();
      byte by[] = new byte[1024];
      response.reset();
      response.setContentType("application/pdf;charset=utf-8");
      response.setHeader("Content-Disposition",
          "attachment; filename=ordernum_" + listMap.get(0).get("orderno") + ".pdf");
      long fileLength = outputFile.length();
      String length1 = String.valueOf(fileLength);
      response.setHeader("Content_Length", length1);
      FileInputStream in = new FileInputStream(outputFile);
      int n;
      while ((n = in.read(by)) != -1) {
        out2.write(by, 0, n);
      }
      in.close();
      out2.flush();
      File f = new File(outputFile);
      if (f.exists()) {
        f.delete();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
