package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.service.IAccountService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 账户的业务层实现类
 * 曾经XML的配置：
 *  <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
 *  scope="" init-method="" destroy-method="">
 *      <property name="" value="" ref=""></property>
 *  </bean>
 *  用于创建对象的注解
 *     他们的作用就和在XML配置中编写一个bean标签实现的功能是一样的
 *     @Component:
 *        作用：用于把当前类对象存入Spring容器中
 *        属性：value：用于指定bean的id。当我们不写时，它的默认值是当前类名，且首字母小写
 *     @Controller:一般用在表现层
 *     @Service：一般用在业务层
 *     @Repository：一般用在持久层
 *     以上三个注解它们呢的作用和属性与@Component是一模一样的
 *  用于注入数据的
 *     他们的作用就和在XML配置文件中的bean标签中写一个property标签的作用是一样的
 *     @Autowired:
 *        作用：自动按照类型注入。只要容器中有唯一的bean对象类型和要注入的变量类型匹配，就可以注入成功
 *        如果IOC容器中没有任何bean的类型和要注入的变量类型匹配，则报错
 *        如果IOC容器中有多个类型匹配时，
 *        出现位置：可以是成员变量上，也可以是方法上
 *        细节：在使用注解注入时，set方法就不是必须的了。
 *     @Qualifier:
 *        作用：在按照累中注入的基础之上再按照名称注入。它在给类成员注入时，不能单独使用。但是再给方法参数注入时可以
 *        属性：value：用于指定注入bean的id
 *     @Resource
 *        作用：直接按照bean的id注入。它可以独立使用
 *        属性：
 *          name:用于指定bean的id
 *      以上三个注入都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现
 *      另外，集合类型的注入只能通过XML来实现。
 *
 *     @Value
 *        作用：用于注入基本类型和String类型的数据
 *        属性：
 *          value：用于指定数据的值。它可以使用spring中SpELl（也就是spring中的el表达式）
 *              SpEL的写法：${表达式}
 *  用于改变作用范围的
 *      他们的作用范围就和在bean标签中使用scope属性实现的功能是一样的
 *      Scope:
 *          作用：用于指定bean的作用范围
 *          属性：
 *              value：指定范围的取值。常用取值：singleton prototype
 *  和生命周期相关 (了解)
 *      他们的作用范围就和在bean标签中使用init-method和destroy-method的功能是一样的
 * @PreDestroy
 *      作用：用于指定销毁方法
 * @PostConstruct
 *      作用：用于指定初始化方法
 */
@Component(value="accountService")
@Scope("singleton")
public class AccountServiceImpl implements IAccountService {

//	@Autowired
//	@Qualifier("accountDao")
	@Resource(name="accountDao1")
	private IAccountDao accountDao;

//	public AccountServiceImpl() {
//		System.out.println("对象创建了");
//	}
	public void saveAccount() {
		accountDao.saveAccount();
	}

	@PostConstruct
	public void init() {
		System.out.println("init method worked");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("destroy method worked");
	}
}
