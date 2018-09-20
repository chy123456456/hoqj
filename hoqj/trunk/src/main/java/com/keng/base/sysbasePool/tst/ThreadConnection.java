package com.keng.base.sysbasePool.tst;  
  
import java.sql.Connection;  

import com.keng.base.sysbasePool.ConnectionPoolManager;
import com.keng.base.sysbasePool.IConnectionPool;
/** 
 * 模拟线程启动，去获得连接 
 * 
 */  
public class ThreadConnection implements Runnable{  
    private IConnectionPool pool;  
    @Override  
    public void run() {  
        pool = ConnectionPoolManager.getInstance().getPool("sybasePool");  
    }  
      
    public Connection getConnection(){  
        Connection conn = null;  
        if(pool != null && pool.isActive()){  
            conn = pool.getConnection();  
        }  
        return conn;  
    }  
      
    public Connection getCurrentConnection(){  
        Connection conn = null;  
        if(pool != null && pool.isActive()){  
            conn = pool.getCurrentConnecton();  
        }  
        return conn;  
    }

	public IConnectionPool getPool() {
		return pool;
	}

	public void setPool(IConnectionPool pool) {
		this.pool = pool;
	}  
}  