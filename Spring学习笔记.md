# Spring学习笔记

## Spring第一天的学习



**ApplicationContext的三个常用实现类：**单例对象适用

- ClassPathXmlApplicatioinContext：它可以加载类路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了
- FileSystemXmlApplicationContext:它可以加载磁盘任意路径下的配置文件（必须有访问权限）
- AnnotationConfigApplicationContext:它是用于读取注解创建容器的

**核心容器的两个接口引发出的问题**：多例对象适用

* ApplicationContext：它在构建核心容器时，创建对象采取的策略是立即加载的方式，也就是说，只要一读取完配置文件马上就创建配置文件中配置的对象
* BeanFactory：它在构建核心容器时，创建对象采取的策略是采用延迟加载的方式。也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。

更多情况下采用ApplicationContext！！！



**Spring对bean的管理细节**

```
1、创建bean的三种方式
2. bean对象的作用范围
3. bean对象的声明周期
```

* 1.**创建bean的三种方式：**

  * ```
    第一种方式：使用默认构造函数创建：
                在spring的配置文件中使用bean标签，配以id和class 属性之后，且没有其他属性和标签时。
                采用的就是默认构造函数创建bean对象，此时如果勒种没有默认构造函数，则对象无法创建
    ```

  * ```
    第二种方式：使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入Spring容器）
    <bean id="instanceFacotry" class="com.itheima.factory.InstanceFactory"></bean>
    <bean id="accountService" facotry-bean="instanceFactory" factory-method="getAccountService"></bean>
    ```

  * ```
    第三种方式：使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入Spring容器)
    ```

* **bean的作用范围调整**

  * ```
    bean标签的scope属性：
        作用：用于指定bean的作用范围
        取值：
            singleton:单例的（默认值）
            prototype:多例的
            request：作用于web应用的请求范围
            session：作用于web应用的会话范围
            global-session：作用于集群环境的会话范围（全局会话范围），当不是集群环境时，它就是session
    ```

* **bean对象的生命周期**
  * **单例对象**
    * 出生：当容器创建时对象出生
    * 活着：只要容器还在，对象一直活着
    * 死亡：容器销毁，对象销往
    * 总结：单例对象的生命周期和容器相同
  * **多例对象**
    * 出生：当我们使用对象时，Spring框架为我们创建
    * 活着：对象只要在使用过程中就一直活着
    * 死亡：当对象长时间不用，且没有别的对象引用时，由Java的垃圾回收器回收

* **Spring的依赖注入**

  * ```
    依赖注入：
        Dependency Injection
    IOC的作用：
        降低程序间的耦合（依赖关系）
    依赖关系的管理：
        以后都交给Spring来维护
    在当前类需要用到其他类的对象，由Spring为我们提供，我们只需要在配置文件中说明
    依赖股演习的维护：
        就称之为依赖注入
    依赖朱瑞：
        能注入的数据有三类：
            基本类型和String
            其他bean类型（在配置文件中或者注解配置过得bean）
            复杂类型/集合类型
        注入的方式：有三种
            第一种：使用构造函数提供
            第二种：使用set方法提供
            第三种：使用注解提供
    ```
    * **构造函数注入**

      ```
      使用的标签：constructor-arg
      标签出现的位置：bean标签的内部
      标签中的属性：
          type:用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
          index:用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
          name：用于指定给构造函数中指定名称的参数赋值  常用的
          ===========以上三个用于指定给构造函数中哪个参数赋值==========
          value:用于提供基本类型和String类型的数据
          ref：用于指定其他的bean类型数据。它指的就是在Spring的IOC核心容器中出现过的bean对象
      优势：
          在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功
      弊端：
          改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
      ```

    * **set方法注入**

      ```
      涉及的标签：property
      出现的位置：bean标签的内部
      标签的属性：
          name：用于指定注入时所调用的set方法名称
          value:用于提供基本类型和String类型的数据
          ref：用于指定其他的bean类型数据。它指的就是在Spring的IOC核心容器中出现过的bean对象
      优势：
          创建对象时没有明确的限制，可以直接使用默认构造函数
      弊端：
          如果有某个成员必须有值，则获取对象是有可能set方法没有执行。
      ```

    * **复杂类型的注入/集合类型的注入**

      ```
      用于给List结构集合注入的标签：
          List、array、set
      用于给Map结构集合注入的标签：
          map props
      结构相同，可以互换
      ```

## Spring第二天：spring基于注解的IOC以及IOC的案例

1、 Spring中的IOC的常用注解

2、案例使用xml方式和注解方式实现单表的CRUD操作

​	持久层技术选择：dbutils

3、改造给予注解的IOC案例，使用纯注解的方式实现

​	spring的一些新注解使用

4、spring和Junit的整合

**用于创建对象的注解**

```
他们的作用就和在XML配置中编写一个bean标签实现的功能是一样的
*     @Component:
*        作用：用于把当前类对象存入Spring容器中
*        属性：value：用于指定bean的id。当我们不写时，它的默认值是当前类名，且首字母小写
*     @Controller:一般用在表现层
*     @Service：一般用在业务层
*     @Repository：一般用在持久层
*     以上三个注解它们呢的作用和属性与@Component是一模一样的
```

**用于注入数据的*注解**

```
他们的作用就和在XML配置文件中的bean标签中写一个property标签的作用是一样的
*     @Autowired:
*        作用：自动按照类型注入。只要容器中有唯一的bean对象类型和要注入的变量类型匹配，就可以注入成功
*        如果IOC容器中没有任何bean的类型和要注入的变量类型匹配，则报错
*        如果IOC容器中有多个类型匹配时，
*        出现位置：可以是成员变量上，也可以是方法上
*        细节：在使用注解注入时，set方法就不是必须的了。
	  @Qualifier:
 *        作用：在按照累中注入的基础之上再按照名称注入。它在给类成员注入时，不能单独使用。但是再给方法参数注入时可以
 *        属性：value：用于指定注入bean的id
 *    @Resource
 *        作用：直接按照bean的id注入。它可以独立使用
 *        属性：
 *          name:用于指定bean的id
 		以上三个注入都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现
	    另外，集合类型的注入只能通过XML来实现。
	  @Value
 *        作用：用于注入基本类型和String类型的数据
 *        属性：
 *          value：用于指定数据的值。它可以使用spring中SpELl（也就是spring中的el表达式）
 *              SpEL的写法：${表达式}
```

 **和生命周期相关 (了解)**
     *      他们的作用范围就和在bean标签中使用init-method和destroy-method的功能是一样的
          * @PreDestroy
               *      作用：用于指定销毁方法
          * @PostConstruct
               *      作用：用于指定初始化方法

-------------------------

**消除xml配置文件的注解方式**

```
Spring中的新注解
* @Configuration
*  作用：指定当前类是一个配置类
   细节：当配置类作为AnnotationConfigApplicatonContext对象创建的参数时，该注解可以不写
* @ComponentScan
*  作用：用于通过注解指定Spring在创建容器时要扫描的包
*  属性：
*      value：它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包。
*      使用此注解就等同于在xml中配置了：
*          <!--    告知Spring在创建容器时要扫描的包-->
* <context:component-scan base-package="com.itheima"></context:component-scan>
* @Bean:
*  作用：用于把当前方法的返回值作为bean对象存入Spring的IOC容器中
*  属性：
*      name：用于指定bean的id，不写时，默认值是当前方法的名称
*
*  细节：当我们使用注解配置方法时，如果方法有参数，Spring框架会去容器中查找有没有可用的bean对象，
*  查找方式和Autowired注解的作用是一样的
 @Import
 *   作用：用于导入其他的配置类
 	 属性：
 *      value：用于指定其他配置类的字节码，
 *      当我们使用@Import注解之后，有Import注解的类就是父配置类，而被导入的都是子配置类
 @PropertySource
 *   作用：用于指定properties文件的位置
 *   属性：
 *      value：指定文件的名称和路径
 *          关键字：classpath，表示类路径下
```

**使用Junit单元测试，测试我们的配置**

```
Spring整合Junit的配置
*  1、导入srping整合Junit的jar（坐标）
*  2、使用Junit提供的一个注解把原有的main方法替换掉，替换成spring提供的
*      @Runwith
*  3、告知spring的运行器，spring的IOC创建时基于XML还是注解的，并且说明位置
*      @ContextConfiguration
*          locations:指定XML文件的位置，加上classpath关键字，表示在类路径下
*          classes：指定注解类所在位置
*  当我们使用spring 5.x版本的时候，要求Junit的jar必须是4.12及以上
```

**事务控制应该都是在业务层**

**动态代理**

```
特点：字节码随用随创建
*  作用：不修改源码的基础上对方法增强
*  分类：
*      1、基于接口的动态代理：
*          涉及的类：Proxy
*          提供者：JDK官方
*          如何创建代理对象：
*              使用Proxy类中的newProxyInstance方法
*          创建代理对象的要求：
*              被代理类最少实现一个接口，如果没有则不能使用
*          newProxyInstance方法的参数:
*              ClassLoader:类加载器，用于加载代理对象字节码 固定写法
*              Class[]:字节码数组，用于让代理对象和被代理对象有相同方法 固定写法
*              InvocationHandler：用于提供增强的代码；它是让我们写如何代理，我们一般都是写一个该接口的实现类，
*              通常情况下都是匿名内部类，但不是必须的。此接口的实现类是谁用谁写
		2、基于子类的动态代理
		 *          涉及的类：Enhancer
		 * 		 *          提供者：第三方cglib库
		 * 		 *          如何创建代理对象：
		 * 		 *              使用Enhancer类中的create方法
		 * 		 *          创建代理对象的要求：
		 * 		 *              被代理类不能是final类
		 * 		 *          create方法的参数:
		 * 		 *              Class:字节码；它是用于指定被代理对象的字节码
		 * 		            Callback:用于提供增强的代码
		 * 		                它是让我们写如何代理。一般都是写一个该接口的实现类，通常情况下都是匿名
		 * 		                内部类，但不是必须
		 * 		                此接口的实现类都是谁用谁写。
		 * 		                我们一般写的都是该接口的子接口实现类：MethodInterceptor
```

------------

## Spring第三天：Spring中的aop

### Spring中基于XML的aop配置步骤

```
1、把通知的Bean也交给Spring来管理
            2、使用aop:config标签表明开始AOP的配置
            3、使用aop:aspect标签表明配置切面
                id属性：是给切面提供一个唯一标识
                ref属性：是指定通知类bean的id
            4、在aop:aspect标签的内部使用对应标签来配置通知的类型
                我们现在的示例是让printLog方法在切入点方法执行之前执行，所以是前置通知。
                aop:before：表示配置前置通知
                    method属性：用于指定Logger类中哪个方法是前置通知
                    pointcut属性：用于指定切入点表达式，该表达式的含义指的是对业务层中哪些方法增强
               切入点表达式的写法：
                 关键字：execution(表达式)
                 表达式：
                    访问修饰符 返回值 包名.包名...类名.方法名(参数列表)
                    标准的表达式写法：
                        public void com.itheima.service.impl.AccountServiceImpl.saveAccount()
                    访问修饰符可以省略
                        void  com.itheima.service.impl.AccountServiceImpl.saveAccount()
                    返回值可以使用通配符，表示任意返回值
                        * com.itheima.service.impl.AccountServiceImpl.saveAccount()
                    包名可以使用通配符，表示任意包。但是有几级包，就需要些几个*
                        * *.*.*.*.AccountServiceImpl.saveAccount()
                    包名可以使用..表示当前包及其子包
                        * *..AccountServiceImpl.saveAccount()
                    名和方法名都可以使用*来实现通配
                        * *..*.*()
                    参数列表：
                        可以直接写数据类型：
                            基本类型写名称 int
                            引用类型写包名.类名的方式 java.lang.String
                        可以使用通配符表示任意类型，但是必须有参数
                        可以使用..表示有无参数均可，有参数可以是任意类型
                    全通配写法：
                        * *..*.*(..)
                    实际开发中切入点表达式的通常写法：
                        切到业务层实现类下的所有方法
                            * com.itheima.service.impl.*.*(..)
                            
                            
bean.xml配置例子：
<!--    配置Spring的IOC，把Service对象配置进来-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>
<!--    配置Logger类-->
    <bean id="logger" class="com.itheima.utils.Logger"></bean>

<!--    配置aop-->
    <aop:config>
    <!--配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
    <!--配置通知的类型且建立通知方法和切入点方法的关联-->
            <aop:before method="printLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:before>
        </aop:aspect>

    </aop:config>
```



### 四种常用的通知类型

* 前置通知：aop:before
* 后置通知：aop:after-returning
* 异常通知：aop:after-throwing
* 最终通知：aop:after

```
<!--配置前置通知,在切入点方法之前执行-->
<aop:before method="beforePrintLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:before>

<!--配置后置通知，在切入点方法正常执行之后执行。它和异常通知永远只能执行一个-->
<aop:after-returning method="afterReturningPrintLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:after-returning>

<!--配置异常通知，在切入点方法执行产生异常之后执行。它和后置通知永远只能执行一个-->
<aop:after-throwing method="afterThrowingPrintLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:after-throwing>

<!--配置最终通知，无论切入点方法是否正常执行，它都会在其后面执行-->
<aop:after method="afterPrintLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:after>
```

### 通用化切入点表达式

aop:pointcut

​	属性：id:指定表达式的唯一标识

​			  expressions:指定表达式内容

例：

```
<!--    配置aop-->
    <aop:config>
        <!--
            配置切入点表达式：id属性用于指定表达式的唯一标识。expressions属性指定表达式内容
            此标签写在aop:aspect标签的内部，只能当前切面使用
            它还可以写在aop:aspect外面，此时就变成了所有切面可用
            -->
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"/>
    <!--配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
    <!--配置通知的类型且建立通知方法和切入点方法的关联-->
            <!--配置前置通知,在切入点方法之前执行-->
            <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>

            <!--配置后置通知，在切入点方法正常执行之后执行。它和异常通知永远只能执行一个-->
            <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>

            <!--配置异常通知，在切入点方法执行产生异常之后执行。它和后置通知永远只能执行一个-->
            <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>

            <!--配置最终通知，无论切入点方法是否正常执行，它都会在其后面执行-->
            <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>


        </aop:aspect>

    </aop:config>
```



### Spring的环绕通知



```
环绕通知
* 问题：
*  当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
* 分析：
*  通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有。
* 解决：
*  Spring框架为我们提供了一个接口：ProceedingJoinPoint,该接口有一个方法proceed()，
*  此方法就相当于明确调用切入点方法。该接口可以作为环绕通知的方法参数，在程序执行时，Spring框架会为
*  我们提供该接口的实现类供我们使用。
* Spring中的环绕通知：
*  它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
```

配置案例：

```
配置文件中：
<!--            配置环绕通知, 详细的注释请看Logger类中-->
            <aop:around method="aroundPrintLog" pointcut-ref="pt1"></aop:around>
```

通知方法：

```
public Object aroundPrintLog(ProceedingJoinPoint pjp) {
   Object rtValue = null;
   try {
      Object[] args = pjp.getArgs();//得到方法执行所需的参数
      System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...前置");

      rtValue = pjp.proceed();//明确调用业务层方法（切入点方法）

      System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...后置");
      return rtValue;
   } catch (Throwable throwable) {
      System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...异常");
      throw new RuntimeException(throwable);
   } finally {
      System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...最终");
   }
}
```

### Spring基于注解的AOP配置

* bean.xml配置

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          https://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop
          https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context
          https://www.springframework.org/schema/context/spring-context.xsd">
  
      <!--    配置Spring创建容器时要扫描的包-->
      <context:component-scan base-package="com.itheima"></context:component-scan>
  
  <!--    配置Spring开启注解aop的支持-->
      <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
  
  
  
  
  </beans>
  ```

切面类配置：

```
package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */

@Component("logger")
@Aspect//表示当前类是一个切面类
public class Logger {
   @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
   private void pt1() {

   }

   /**
    * 前置通知
    */
// @Before("pt1()")
   public void beforePrintLog() {
      System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了...");
   }

   /**
    * 后置通知
    */
// @AfterReturning("pt1()")
   public void afterReturningPrintLog() {
      System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了...");
   }

   /**
    * 异常通知
    */
// @AfterThrowing("pt1()")
   public void afterThrowingPrintLog() {
      System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了...");
   }

   /**
    * 最终通知
    */
// @After("pt1()")
   public void afterPrintLog() {
      System.out.println("最终通知Logger类中的afterPrintLog方法开始记录日志了...");
   }


   /**
    * 环绕通知
    * 问题：
    *  当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
    * 分析：
    *  通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有。
    * 解决：
    *  Spring框架为我们提供了一个接口：ProceedingJoinPoint,该接口有一个方法proceed()，
    *  此方法就相当于明确调用切入点方法。该接口可以作为环绕通知的方法参数，在程序执行时，Spring框架会为
    *  我们提供该接口的实现类供我们使用。
    * Spring中的环绕通知：
    *  它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
    */
   @Around("pt1()")
   public Object aroundPrintLog(ProceedingJoinPoint pjp) {
      Object rtValue = null;
      try {
         Object[] args = pjp.getArgs();//得到方法执行所需的参数
         System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...前置");

         rtValue = pjp.proceed();//明确调用业务层方法（切入点方法）

         System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...后置");
         return rtValue;
      } catch (Throwable throwable) {
         System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...异常");
         throw new RuntimeException(throwable);
      } finally {
         System.out.println("Logger类中的aroundPrintLog方法开始记录日志了...最终");
      }
   }
}
```