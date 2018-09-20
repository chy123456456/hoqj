package com.keng.base.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {
    private static final Log logger = LogFactory.getLog(UploadUtil.class);

    /*
     * multipartRequest:文件上传request对象 inputFileName:文件上传选择框name值 myPathName:自定义的文件所在父目录名 successMsg:成功信息 failMsg:失败信息
     */
    public static Map<String, Object> upload(MultipartHttpServletRequest multipartRequest, String inputFileName, String myPathName,
            String successMsg, String failMsg) {
        String originalFileName = null;
        // newFilePath供以后取出时使用
        String newFileIpPath = null;
        // basePath固定位web-inf下的upload文件夹
        String baseRealPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/";
        String targetRealPath = baseRealPath + myPathName + "/";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        MultipartFile multipartFile = null;
        try {
            multipartFile = multipartRequest.getFile(inputFileName);
            originalFileName = multipartFile.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(targetRealPath);
        if (!file.exists()) {
            file.mkdirs(); // 创建文件路径
        }
        if (originalFileName.equals("")) {
            resultMap.put("code", 1);
            resultMap.put("msg", failMsg);
            return resultMap;
        }
        String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");// 返回一个随机UUID。
        String suffix = originalFileName.indexOf(".") != -1 ? originalFileName
                .substring(originalFileName.lastIndexOf("."), originalFileName.length()) : null;
        String newFileName = uuid + (suffix != null ? suffix : "");// 构成新文件名。
        File uploadFile = new File(targetRealPath + newFileName);
        String ipAndPort = multipartRequest.getScheme() + "://" + "localhost:" + multipartRequest.getServerPort() + multipartRequest.getContextPath();
        newFileIpPath = ipAndPort + "/upload/" + myPathName + "/" + newFileName;
        System.out.println(newFileIpPath);
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultMap.put("code", 0);
        resultMap.put("msg", successMsg);
        resultMap.put("newFilePath", newFileIpPath);
        return resultMap;
    }

    public static String resDownloadRootUrl(HttpServletRequest request) {
        String url = null;
        if (url == null || url.length() == 0) {
            String scheme = request.getScheme().toLowerCase();
            int port = request.getServerPort();
            StringBuffer sb = new StringBuffer(scheme);
            sb.append("://").append(request.getServerName());
            if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
                sb.append(':').append(port);
            }
            sb.append(request.getContextPath());
            url = sb.toString();
            url = url + "/res";
        }
        return url;
    }

    /**
     * 转发到硬盘文件
     * 
     * @param request
     * @param response
     * @param downLoadPath
     * @param contentType
     * @param realName
     * @throws Exception
     */
    public static void dispatchFile(HttpServletRequest request, HttpServletResponse response, String downLoadPath, String contentType, String realName)
            throws Exception {
        // if (contentType == null) contentType = "application/octet-stream";
        if (realName == null) {
            int lastIndexOf = downLoadPath.lastIndexOf("/");
            if (lastIndexOf == -1) lastIndexOf = downLoadPath.lastIndexOf("\\");
            realName = downLoadPath.substring(lastIndexOf + 1);
        }
        // response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File downloadFile = new File(downLoadPath);
            if (downloadFile.exists()) {
                long fileLength = downloadFile.length();
                if (contentType != null) response.setContentType(contentType);
                // response.setHeader("Content-disposition", "attachment; filename="
                // + new String(realName.getBytes("utf-8"), "ISO8859-1"));
                response.setHeader("Content-Length", String.valueOf(fileLength));
                bis = new BufferedInputStream(new FileInputStream(downLoadPath));
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } else {
                // 返回404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("下载文件失败", e);
        } finally {
            if (bis != null) bis.close();
            if (bos != null) bos.close();
        }
    }
}
