# Spring学习笔记

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

* 1.创建bean的三种方式：

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

* bean的作用范围调整

  * ```
    bean标签的scope属性：
        作用：用于指定bean的作用范围
        取值：
            singleton:单例的（默认值）
            prototype
            request
            session
            global-session
    ```