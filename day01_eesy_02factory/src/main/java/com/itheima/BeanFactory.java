package com.itheima;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * 创建Bean对象的工厂
 * Bean在计算机中，有可重用组件的含义
 * JavaBean:用Java语言编写的可重用组件
 * 它就是创建我们的service和dao对象的
 *
 * 第一个，需要一个配置文件来配置我们的service和dao
 *      配置的内容：唯一标识=全限定类名(key=value)
 * 第二个，通过读取配置文件中配置的内容，反射创建对象
 *
 * 配置文件可以是XML也可以是properties
 */

public class BeanFactory {
	// 定义一个Properties对象
	private static Properties props;

	// 定义一个Map，用于存放我们要创建的对象，我们称之为容器
	private static HashMap<String, Object> beans;

	// 使用静态代码块为Properties对象赋值
	static {
		try {
			// 实例化对象
			props = new Properties();
			// 获取properties文件的流对象
			InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
			props.load(in);

			beans = new HashMap<String, Object>();
			// 取出配置文件中所有的key
			Enumeration keys = props.keys();
			// 遍历枚举
			while (keys.hasMoreElements()) {
				// 取出每个key
				String key = keys.nextElement().toString();
				// 根据key获取value
				String beanPath = props.getProperty(key);
				// 反射创建对象
				Object value = Class.forName(beanPath).newInstance();
				// 把key value存入容器中
				beans.put(key, value);
			}
		} catch (IOException e) {
			throw new ExceptionInInitializerError("初始化properties失败");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 根据bean的名称获取bean对象
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return beans.get(beanName);
	}

	/**
	 * 根据bean的名称获取bean对象
	 * @param beanName
	 * @return
	 */
//	public static Object getBean(String beanName) {
//		Object bean = null;
//		try {
//			String beanPath = props.getProperty(beanName);
//			bean = Class.forName(beanPath).newInstance();//反射的模式建立对象，每次都会调用默认构造函数创建对象
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//
//		return bean;
//	}
}
