package com.keng.base.sysbasePool;  
  
import java.util.ArrayList;  
import java.util.List;  
/** 
 * 初始化，模拟加载所有的配置文件 
 * 
 */  
public class DBInitInfo {  
    public  static List<DBbean>  beans = null;  
    static{  
        beans = new ArrayList<DBbean>();  
        // 这里数据 可以从xml 等配置文件进行获取  
        // 为了测试，这里我直接写死  
        DBbean beanOracle = new DBbean();  
        beanOracle.setDriverName("com.sybase.jdbc3.jdbc.SybDriver");  
        beanOracle.setUrl("jdbc:sybase:Tds:192.168.2.88:5000/FL_HIS");  
        beanOracle.setUserName("sa");  
        beanOracle.setPassword("123456");  
          
        beanOracle.setMinConnections(5);  
        beanOracle.setMaxConnections(100);  
          
        beanOracle.setPoolName("sybasePool");  
        beans.add(beanOracle);  
    }  
}