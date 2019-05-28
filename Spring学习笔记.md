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