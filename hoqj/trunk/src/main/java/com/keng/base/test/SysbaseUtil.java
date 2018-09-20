package com.keng.base.test;

import java.sql.*; 
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class SysbaseUtil {
	/** 文件上传路径 */
	  public static String              sysbaseDriver;
	  public static String              sysbaseConnectUrl;
	  public static String              sysbaseUser;
	  public static String              sysbasePassword;
	
    public static void main(String[] args) {
        try {
            Class.forName(sysbaseDriver).newInstance();
            String url = sysbaseConnectUrl;// 数据库名
            Properties sysProps = System.getProperties();
            sysProps.put("user", sysbaseUser); // 设置数据库访问用户名
            sysProps.put("password", sysbasePassword); // 密码
            Connection conn = DriverManager.getConnection(url, sysProps);
            Statement stmt = conn
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from p_deptemp"; // 表
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                System.out.println("oject_id:"+rs.getString(1)+",oject_name:"+rs.getString(2)); // 取得第二列的值
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static List<Object> getList(String sql){
    	
		return null;
    	
    }

	public static String getSysbaseDriver() {
		return sysbaseDriver;
	}
	 
	@Value("${sysbaseDriver}")  
	public static void setSysbaseDriver(String sysbaseDriver) {
		SysbaseUtil.sysbaseDriver = sysbaseDriver;
	}

	public static String getSysbaseConnectUrl() {
		return sysbaseConnectUrl;
	}

	@Value("${sysbaseConnectUrl}")
	public static void setSysbaseConnectUrl(String sysbaseConnectUrl) {
		SysbaseUtil.sysbaseConnectUrl = sysbaseConnectUrl;
	}

	public static String getSysbaseUser() {
		return sysbaseUser;
	}

	@Value("${sysbaseUser}")
	public static void setSysbaseUser(String sysbaseUser) {
		SysbaseUtil.sysbaseUser = sysbaseUser;
	}

	public static String getSysbasePassword() {
		return sysbasePassword;
	}

	@Value("${sysbasePassword}")
	public static void setSysbasePassword(String sysbasePassword) {
		SysbaseUtil.sysbasePassword = sysbasePassword;
	}
}
