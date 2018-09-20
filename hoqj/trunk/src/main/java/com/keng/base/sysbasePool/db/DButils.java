package com.keng.base.sysbasePool.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.keng.base.sysbasePool.tst.Client;

public class DButils {
	/**
	 * 执行单句sql
	 * 
	 * @return
	 */
	public static Object exeSql(String sql) {
		try {
			Connection c = Client.getSqlConnection();
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// String sql = "select * from p_deptemp"; // 表
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		// while (rs.next()) {
		// System.out.println("oject_id:" + rs.getString(1) + ",oject_name:" +
		// rs.getString(2)); // 取得第二列的值
		// }
	}

	/**
	 * 执行查询sql
	 * 
	 * @return list
	 */
	public static Object exeQuerySql(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Connection c = Client.getSqlConnection();
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData(); // 获得结果集结构信息,元数据
			int columnCount = md.getColumnCount(); // 获得列数
			while (rs.next()) {
				Map<String, Object> rowData = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
