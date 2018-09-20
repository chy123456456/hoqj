package com.keng.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SystemUtil {
	public static String randomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	/**
	 * 获取完整的请求根路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestRootPath(HttpServletRequest request) {
		String scheme = request.getScheme().toLowerCase();
		int port = request.getServerPort();
		StringBuffer sb = new StringBuffer(scheme);
		sb.append("://").append(request.getServerName());
		if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
			sb.append(':').append(port);
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

	/**
	 * 从request获取请求IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取操作系统名称
	 */
	public static String getOsName() {
		String os = "";
		os = System.getProperty("os.name");
		return os;
	}

	private String sRemoteAddr;
	private int iRemotePort = 137;
	private byte[] buffer = new byte[1024];
	private DatagramSocket ds = null;

	public SystemUtil(String strAddr) throws Exception {
		sRemoteAddr = strAddr;
		ds = new DatagramSocket();
	}

	public final DatagramPacket send(final byte[] bytes) throws IOException {
		DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(sRemoteAddr), iRemotePort);
		ds.send(dp);
		return dp;
	}

	public final DatagramPacket receive() throws Exception {
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);
		return dp;
	}

	public byte[] GetQueryCmd() throws Exception {
		byte[] t_ns = new byte[50];
		t_ns[0] = 0x00;
		t_ns[1] = 0x00;
		t_ns[2] = 0x00;
		t_ns[3] = 0x10;
		t_ns[4] = 0x00;
		t_ns[5] = 0x01;
		t_ns[6] = 0x00;
		t_ns[7] = 0x00;
		t_ns[8] = 0x00;
		t_ns[9] = 0x00;
		t_ns[10] = 0x00;
		t_ns[11] = 0x00;
		t_ns[12] = 0x20;
		t_ns[13] = 0x43;
		t_ns[14] = 0x4B;

		for (int i = 15; i < 45; i++) {
			t_ns[i] = 0x41;
		}
		t_ns[45] = 0x00;
		t_ns[46] = 0x00;
		t_ns[47] = 0x21;
		t_ns[48] = 0x00;
		t_ns[49] = 0x01;
		return t_ns;
	}

	public final String GetMacAddr(byte[] brevdata) throws Exception {
		// 获取计算机名
		int i = brevdata[56] * 18 + 56;
		String sAddr = "";
		StringBuffer sb = new StringBuffer(17);
		// 先从第56字节位置，读出Number Of Names（NetBIOS名字的个数，其中每个NetBIOS Names
		// Info部分占18个字节）
		// 然后可计算出“Unit ID”字段的位置＝56＋Number Of
		// Names×18，最后从该位置起连续读取6个字节，就是目的主机的MAC地址。
		for (int j = 1; j < 7; j++) {
			sAddr = Integer.toHexString(0xFF & brevdata[i + j]);
			if (sAddr.length() < 2) {
				sb.append(0);
			}
			sb.append(sAddr.toUpperCase());
			if (j < 6)
				sb.append('-');
		}
		return sb.toString();
	}

	public final void close() throws Exception {
		ds.close();
	}

	public final String GetRemoteMacAddr() throws Exception {
		byte[] bqcmd = GetQueryCmd();
		send(bqcmd);
		DatagramPacket dp = receive();
		String smac = GetMacAddr(dp.getData());
		close();

		return smac;
	}

	// 得到客户端计算机名
	public static String getComputerName(HttpServletRequest request) {
		return request.getServerName();
	}

	public static String getMACAddress(String ip) throws Exception {
		SystemUtil umac = new SystemUtil(ip);
		return umac.GetRemoteMacAddr();
	}
	
	public static void main(String [] args){
	}
}
