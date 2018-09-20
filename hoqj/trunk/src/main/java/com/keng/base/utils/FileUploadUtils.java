package com.keng.base.utils;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.keng.base.common.BaseAction;
import com.keng.base.common.BeansManager;
import com.keng.base.common.Constants;
import com.keng.base.common.FileUpload;
import com.keng.base.ehcache.ResCacheBean;
import com.keng.main.entity.SysFiles;

public class FileUploadUtils {
    private static Log          log         = LogFactory.getLog(BaseAction.class);
    private static ResCacheBean ehcacheBean = BeansManager.getBean(ResCacheBean.class);
    private static Random       random      = new Random();

    /**
     * 获取目录
     * 
     * @param path
     * @return
     */
    public static String getDir(String path) {
        if (StringUtils.isBlank(Constants.UPLOAD_PATH_PARENT)) {
            path = ehcacheBean.getRootPath() + path;
        } else {
			path = Constants.UPLOAD_PATH_PARENT + "/" + path;
        }
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
            log.debug("目录不存在，创建成功！[路径:" + path + "]");
        }
        return filePath.getAbsolutePath();
    }

    public static String renamePath(String path, String newName) {
        // 得到文件名
        String fname = path.substring(path.lastIndexOf("/") + 1);
        // 得到文件路径
        String fpath = path.substring(0, path.lastIndexOf("/"));
        // 得到磁盘目录
        String dirPath = getDir("assets/upload");
        String newPath = fpath + "/" + newName + ".plist";
        /*
         * File f = new File(dirPath,fname); String rootPath=f.getParent(); String newDirPath =
         * rootPath+newName+".plist"; File mm=new File(newDirPath); if(f.renameTo(mm)){ log.info("文件名修改成功！oldPath[" +
         * path + "], newPath[" + newPath + "]"); } else{ log.info("文件名修改失败！oldPath[" + path + "]"); }
         */
        File oldFile = new File(dirPath, fname);
        File newFile = new File(dirPath, newName + ".plist");
        boolean b = oldFile.renameTo(newFile);
        if (b) {
            log.info("文件名修改成功！oldPath[" + path + "], newPath[" + newPath + "]");
        } else {
            log.info("文件名修改失败！oldPath[" + path + "]");
        }
        return newPath;
    }

    /**
     * 文件上传
     * 
     * @param path 文件存储路径
     * @param newName 保存文件名
     * @return 文件对象
     */
    public static SysFiles uploadFile(MultipartFile patch, String path, String newName) {
        SysFiles upload = null;
        String fileName;
        if (patch != null && !patch.isEmpty()) {
            try {
                fileName = patch.getOriginalFilename();
                patch.transferTo(new File(getDir(path), newName));
                log.debug("文件上传成功！path[" + path + "], name[" + fileName + "], size[" + Math.rint(patch.getSize() / 1024.0) + "]");
                upload = new SysFiles();
                upload.setLocalName(newName);
                upload.setRemoteName(fileName);
                upload.setSavePath(path);
                upload.setUploadTime(new Date());
                upload.setFileType(patch.getContentType());
                upload.setFileSize(patch.getSize());
                upload.setFileStatus(1);
            } catch (IOException e) {
                log.error("文件上传失败！", e);
            }
        }
        return upload;
    }

    /**
     * 文件上传
     * @param patch 文件流
     * @param uploadPath 上传路径
     * @param ipaName 生成的文件名（当没有传入时，则按照日期生成）
     * @return
     */
    public static FileUpload uploadFile(MultipartFile patch, String uploadPath, String ipaName, String path) {
        path = StringUtils.isBlank(path) ? "assets/upload" : path;
        FileUpload upload = null;
        String newName = null, fileName = null, suffix = null;
        if (patch != null && !patch.isEmpty()) {
            try {
                fileName = patch.getOriginalFilename();
                int index = fileName.lastIndexOf('.');
                if (index > -1) {
                    suffix = fileName.substring(index);
                }
                if (null != ipaName && !"".equals(ipaName)) {
                    newName = ipaName;
                } else {
                    newName = String.valueOf(System.currentTimeMillis()) + (suffix == null ? "" : suffix);
                }
                patch.transferTo(new File(getDir(path), newName));
                log.debug("文件上传成功！path[" + path + "], name[" + fileName + "], size[" + Math.rint(patch.getSize() / 1024.0) + "]");
                upload = new FileUpload();
                upload.setLocalName(newName);
                upload.setDiskPath(getDir(path));
                log.info("文件上传路径_________" + uploadPath + "/" + path);
                upload.setLocalPath(uploadPath + "/" + path); // 设置上传图片的地址
                upload.setRemoteName(fileName);
            } catch (IOException e) {
                log.error("文件上传失败！", e);
            }
        }
        return upload;
    }

    /**
     * 文件上传
     * 
     * @param path 文件存储路径
     * @return 文件对象
     */
    public static SysFiles uploadFile(MultipartFile patch, String path) {
        String fileName = patch.getOriginalFilename(), suffix = null;
        int index = fileName.lastIndexOf('.');
        if (index > -1) {
            suffix = fileName.substring(index);
        }
        String newName = String.valueOf(System.currentTimeMillis());
        newName += "-" + String.format("%06d", random.nextInt(999999));
        newName += (suffix == null ? "" : suffix);
        return uploadFile(patch, path, newName);
    }
}
