package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和事务管理相关的工具类，它包含了开启事务、提交事务、回滚事务和释放链接
 */
@Component("txManager")
@Aspect
public class TransactionManager {

	@Autowired
	private ConnectionUitls connectionUitls;

	@Pointcut("execution(* com.itheima.service.impl.*.*(..))")
	private void pt1() {}


	/**
	 * 开启事务
	 * @return
	 */
//	@Before("pt1()")
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
//	@AfterReturning("pt1()")
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
//	@AfterThrowing("pt1()")
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
//	@After("pt1()")
	public void release() {
		try {
			connectionUitls.getThreadConnection().close();//还回连接池中
			connectionUitls.removeConnection();//线程和连接解绑
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Around("pt1()")
	public Object aroudAdvice(ProceedingJoinPoint pjp) {
		Object rtValue = null;

		try {
			// 1、获取参数
			Object [] args = pjp.getArgs();

			// 2、开启事务
			this.beginTransaction();

			// 3、执行方法
			rtValue = pjp.proceed(args);

			// 4、提交事务
			this.commit();

			// 返回结果
			return rtValue;
		} catch (Throwable e) {
			// 5、回滚事务
			this.rollback();
			throw new RuntimeException(e);
		} finally {
			// 6、释放资源
			this.release();
		}
	}
}
