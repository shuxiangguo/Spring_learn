package com.itheima.utils;

/**
 * 和事务管理相关的工具类，它包含了开启事务、提交事务、回滚事务和释放链接
 */
public class TransactionManager {
	private ConnectionUitls connectionUitls;

	public void setConnectionUitls(ConnectionUitls connectionUitls) {
		this.connectionUitls = connectionUitls;
	}


	/**
	 * 开启事务
	 * @return
	 */
	public void beginTransaction() {
		try {
			connectionUitls.getThreadConnection().setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交事务
	 * @return
	 */
	public void commit() {
		try {
			connectionUitls.getThreadConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回滚事务
	 * @return
	 */
	public void rollback() {
		try {
			connectionUitls.getThreadConnection().rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放连接
	 * @return
	 */
	public void release() {
		try {
			connectionUitls.getThreadConnection().close();//还回连接池中
			connectionUitls.removeConnection();//线程和连接解绑
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
