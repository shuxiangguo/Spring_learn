package com.itheima.test;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import config.JdbcConfig;
import config.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit单元测试，测试我们的配置
 * Spring整合Junit的配置
 *  1、导入srping整合Junit的jar（坐标）
 *  2、使用Junit提供的一个注解把原有的main方法替换掉，替换成spring提供的
 *      @Runwith
 *  3、告知spring的运行器，spring的IOC创建时基于XML还是注解的，并且说明位置
 *      @ContextConfiguration
 *          locations:指定XML文件的位置，加上classpath关键字，表示在类路径下
 *          classes：指定注解类所在位置
 *  当我们使用spring 5.x版本的时候，要求Junit的jar必须是4.12及以上
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {
	@Autowired
	private IAccountService as = null;



	@Test
	public void testFindAll() {

		//3.执行方法
		List<Account> accounts = as.findAllAccount();
		for (Account account : accounts) {
			System.out.println(account);
		}
	}

	@Test
	public void testFindOne() {
		//1. 获取容器
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		// 2.得到业务层对象
		IAccountService as = ac.getBean("accountService", IAccountService.class);
		//3.执行方法
		Account account = as.findAccountById(1);
		System.out.println(account);
	}

	@Test
	public void testSave() {
		Account account = new Account();
		account.setName("test anno");
		account.setMoney(12345f);
		//1. 获取容器
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		// 2.得到业务层对象
		IAccountService as = ac.getBean("accountService", IAccountService.class);
		//3.执行方法
		as.saveAccount(account);
	}

	@Test
	public void testUpdate() {
		//1. 获取容器
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		// 2.得到业务层对象
		IAccountService as = ac.getBean("accountService", IAccountService.class);
		//3.执行方法
		Account account = as.findAccountById(4);
		account.setMoney(2324f);
		as.updateAccount(account);
	}

	@Test
	public void testDelete() {
		//1. 获取容器
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		// 2.得到业务层对象
		IAccountService as = ac.getBean("accountService", IAccountService.class);
		//3.执行方法
		as.deleteAccount(4);
	}
}
