package com.keng.main.action.common;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.keng.base.common.BaseAction;
import com.keng.base.utils.BeanUtils;
import com.keng.base.utils.FileUploadUtils;
import com.keng.main.entity.SysConfig;
import com.keng.main.entity.SysFiles;
import com.keng.main.service.ConfigService;
import com.keng.main.service.FileService;

/**
 * 文件管理统一接口 包含文件上传,下载,读取
 * @date 2014-5-8 上午11:38:11
 */
@Controller
@RequestMapping(value = "/document")
public class FileAction extends BaseAction {
    @Autowired
    private ConfigService       configService;
    @Autowired
    private FileService         fileService;
    private static final String FILE_UPLOAD_PATH    = "FILE_UPLOAD_PATH";
    private static final String DEFAULT_UPLOAD_PATH = "/upload";

    /**
     * 文件上传 传入参数 保存路径（sys_config表中FILE_UPLOAD_PATH对应的key）
     * @param request 文件上传Request对象
     * @return 返回文件信息
     * @date 2014-5-8 上午11:41:21
     */
    @ResponseBody
    @RequestMapping(value = "/upload-{key}.do", method = RequestMethod.POST)
    public Object upload(@PathVariable String key, MultipartHttpServletRequest request) {
        Map<String, Object> result = getResultMap();
        try {
            List<SysFiles> files = new ArrayList<SysFiles>();
            List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
            Integer gId = getRequestParams(Integer.class, request, "gId");
            SysConfig conf = configService.getByCfgidAndCfgkey(FILE_UPLOAD_PATH, key);
            String path = conf == null ? DEFAULT_UPLOAD_PATH : conf.getCfgVal();
            Iterator<String> names = request.getFileNames();
            while (names.hasNext()) {
                String name = names.next();
                List<MultipartFile> multipartFiles = request.getFiles(name);
                for (MultipartFile multipartFile : multipartFiles) {
                    SysFiles upload = FileUploadUtils.uploadFile(multipartFile, path);
                    upload.setFileMode(key);
                    upload.setgId(gId);
                    files.add(upload);
                    Map<String, Object> attr = new HashMap<String, Object>();
                    attr.put("name", name);
                    attr.put("path", path + "/" + upload.getLocalName());
                    attrs.add(attr);
                }
            }
            SysFiles[] arr = files.toArray(new SysFiles[files.size()]);
            fileService.addFile(arr);
            Map<String, Object> data = new HashMap<String, Object>();
            for (int i = 0; i < arr.length; i++) {
                Map<String, Object> attr = attrs.get(i);
                String name = String.valueOf(attr.get("name"));
                attr.remove("name");
                attr.put("fileId", arr[i].getfId());
                attr.put("sort", arr[i].getSort());
                data.put(name, attr);
            }
            result.put(DATA, data);
            result.put(RESULT, true);
            result.put(MESSAGE, "上传成功！");
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，上传失败！");
            getLog(this).error("系统异常，图片上传失败！", e);
        }
        return result;
    }

    /**
     * 文件下载 传入参数 文件编号
     * @date 2014-5-8 上午11:41:25
     */
    @ResponseBody
    @RequestMapping(value = "/download-{fId}.do", method = RequestMethod.GET)
    public void download(@PathVariable String fId, HttpServletResponse response) {
        Integer id = BeanUtils.typeCast(Integer.class, fId);
        try {
            boolean isOk = false; // 是否正常下载
            if (id != null) {
                SysFiles sysFiles = fileService.getFile(id);
                if (sysFiles != null && sysFiles.getFileStatus() == 1) {
                    String path = FileUploadUtils.getDir(sysFiles.getSavePath());
                    File file = new File(path + File.separator + sysFiles.getLocalName());
                    if (file.exists()) {
                        response.setContentLength((int) file.length());
                        response.setContentType("application/octet-stream; charset=UTF-8");
                        String fileName = new String(sysFiles.getRemoteName().getBytes("GBK"), "ISO-8859-1");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                        isOk = true;
                    }
                }
            }
            if (!isOk) {
                response.setContentType("text/html");
                FileCopyUtils.copy(new ByteArrayInputStream("The download fails, the file does not exist!".getBytes()), response.getOutputStream());
                getLog(this).error("文件读取失败，指定文件不存在！");
            }
        } catch (FileNotFoundException e) {
            getLog(this).error("文件下载失败，指定文件不存在！", e);
        } catch (IOException e) {
            getLog(this).error("文件下载失败，系统异常！", e);
        }
    }

    /**
     * 读取文件（如果为图片，返回图片类型，否则下载文件）
     * @param fId 文件编号
     * @param response Response对象
     * @date 2014-5-30 下午5:18:08
     */
    @ResponseBody
    @RequestMapping(value = "/file-{fId}.do", method = RequestMethod.GET)
    public void image(@PathVariable String fId, HttpServletResponse response) {
        Integer id = BeanUtils.typeCast(Integer.class, fId);
        try {
            boolean isOk = false; // 是否正常下载
            if (id != null) {
                SysFiles sysFiles = fileService.getFile(id);
                if (sysFiles != null && sysFiles.getFileStatus() == 1) {
                    String path = FileUploadUtils.getDir(sysFiles.getSavePath());
                    File file = new File(path + File.separator + sysFiles.getLocalName());
                    if (file.exists()) {
                        if (sysFiles.getFileType().startsWith("image")) {
                            response.setContentType(sysFiles.getFileType());
                            response.setHeader("Pragma", "No-cache");
                            response.setHeader("Cache-Control", "no-cache");
                            response.setDateHeader("Expires", 240);
                        } else {
                            response.setContentLength((int) file.length());
                            response.setContentType(sysFiles.getFileType());
                            String fileName = new String(sysFiles.getRemoteName().getBytes("GBK"), "ISO-8859-1");
                            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                        }
                        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                        isOk = true;
                    }
                }
            }
            if (!isOk) {
                response.setContentType("text/html");
                FileCopyUtils.copy(new ByteArrayInputStream("The download fails, the file does not exist!".getBytes()), response.getOutputStream());
                getLog(this).error("文件读取失败，指定文件不存在！");
            }
        } catch (FileNotFoundException e) {
            getLog(this).error("文件读取失败，指定文件不存在！", e);
        } catch (IOException e) {
            getLog(this).error("文件读取失败，系统异常！", e);
        }
    }
}
