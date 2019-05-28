package com.itheima.factory;

import com.itheima.service.IAccountService;
import com.itheima.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建Service的代理对象的工厂
 */

public class BeanFactory {
	private IAccountService accountService;
	private TransactionManager txManager;

	public void setTxManager(TransactionManager txManager) {
		this.txManager = txManager;
	}

	public final void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public IAccountService getAccountService() {
		Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
				accountService.getClass().getInterfaces(),
				new InvocationHandler() {
					/**
					 * 添加事务的支持
					 * @param proxy
					 * @param method
					 * @param args
					 * @return
					 * @throws Throwable
					 */
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object rtValue = null;
						try {
							//1、开启事务
							txManager.beginTransaction();
							//2、执行操作
							rtValue = method.invoke(accountService, args);
							//3、提交事务
							txManager.commit();
							//4.返回结果
							return rtValue;
						} catch (Exception e) {
							//4、回滚
							txManager.rollback();
							throw new RuntimeException(e);
						} finally {
							// 5、释放连接
							txManager.release();
						}
					}
				});
	}
}
