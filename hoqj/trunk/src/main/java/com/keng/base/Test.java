package com.keng.base;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import com.keng.base.utils.mandalaSMS.org.tempuri.MsgTaskServiceSoapProxy;

public class Test {
	// 得到客户端IP地址
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// 得到客户端计算机名public
	public static String getComputerName(String ip) {
		String computerName = "";
		String str = "";
		try {
			Process p = Runtime.getRuntime().exec("cmd /c C:\\Windows\\sysnative\\nbtstat.exe -a " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				try {
					str = input.readLine();
				} catch (IOException e) {
				}
				if (str.indexOf("唯一") > 1) {
					computerName = str.substring(0, str.indexOf("<")).trim();
					break;
				}
			}
		} catch (IOException e) {// TODO Auto-generated catch
			e.printStackTrace();
		}
		return computerName;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String[] arrs = new String[] { "内容" };
		String[] arrs1 = new String[] { "test" };
		MsgTaskServiceSoapProxy proxy = new MsgTaskServiceSoapProxy();
		try {
			com.keng.base.utils.mandalaSMS.org.tempuri.WebApiResult result = proxy.submitMsgSimple("目标号码", "055", "发送时间（YYYY-MM-DD hh:mi:ss）", "博思特机房管理",
					arrs, arrs1);
			// org.tempuri.WebApiResult resul1 =
			// proxy.submitMsgSimple(cellphone, bizid, senddt, sysid, keys,
			// values)
			System.out.println(result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}