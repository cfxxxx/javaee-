# IOC（概念和原理）

1. 什么是IOC
   1. 控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理
   2. 使用IOC目的：使耦合度降低
2. IOC底层原理
   1. xml解析，工厂模式，反射

# IOC（接口）

1. IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
2. Spring提供IOC容器实现两种方式（两个接口）
   1. BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供开发人员进行使用
      
      加载配置文件时不会创建对象，在获取对象时才去创建对象
   2. ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用
      
      加载配置文件的时候就会把在配置文件的对象进行创建
   3. ApplicationContext接口有实现类

# IOC操作 Bean管理

1. Bean管理
   
   Bean管理指的是两个操作：
   1. Spring创建对象
   2. Spring注入属性
2. Bean管理操作有两种方式
   1. 基于xml配置文件方式实现
   2. 基于注解方式实现

# IOC操作 Bean管理（基于xml方式）

1. 基于xml方式创建对象
   ```xml
   <!-- 配置User对象创建 -->
   <bean id="user" class="com.spring5.User"></bean>
   ```
   1. 在spring配置文件中，使用bean标签，标签里面添加对应属性，就可以实现对象创建
   2. 在bean标签有很多属性，介绍常用属性
      - id属性：唯一标识
      - class属性：类全路径
   3. 创建对象的时候，默认执行无参构造方法
2. 基于xml方式注入属性
   1. DI：依赖注入，就是注入属性
      
      ### 第一种注入方式：使用set方法进行注入
      1. 创建类，定义属性和对应的set方法
         ```java
         public class Book {
             private String bname;
         
             public void setBname(String bname) {
                 this.bname = bname;
             }
         }
         ```
      2. 在spring配置文件配置对象创建，配置属性注入
         ```xml
         <!-- 2 set方法注入属性 -->
             <bean id="book" class="com.spring5.Book">
                 <!-- 使用property完成属性注入
                      name：类里面属性名称
                      value：向属性注入的值 -->
                 <property name="bname" value="石敖玉"/>
             </bean>
         ```
      
      ### 第二种注入方式：使用有参构造进行注入
      1. 创建类，定义属性，创建属性对应有参构造方法
         ```java
         public class Orders {
             private String oname;
             private String address;
         
             public Orders(String oname, String address) {
                 this.oname = oname;
                 this.address = address;
             }
         }
         ```
      2. 在spring配置文件中进行配置
         ```xml
         <!-- 3 有参构造注入属性 -->
             <bean id="orders" class="com.spring5.Orders">
                 <constructor-arg name="oname" value="苹果"/>
                 <constructor-arg name="address" value="中国"/>
             </bean>
         ```

# P名称空间注入（了解）

使用p名称空间注入，可以简化基于xml配置方式

### 第一步 添加p名称空间在配置文件中

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

```

### 第二步 进行属性注入，在bean标签里面进行操作

```xml
<bean id="book" class="com.spring5.Book" p:bname="九阳神功"/>
```

# IOC操作 Bean管理（xml注入其他属性类型）

1. 字面量
   1. null值
      ```xml
      <!--null值-->
              <property name="author">
                  <null/>
              </property>
      ```
   2. 属性包含特殊符号
2. 注入属性-外部bean
   1. 创建两个类service类和dao类
   2. 在service调用dao里面的方法
   3. 在spring配置文件中进行配置
3. 注入属性-内部bean
   1. 一对多关系：部门和员工
   2. 在实体类之间表示一对多的关系
      ```java
      //部门类
      public class Dept {
          private String dname;
      
          public void setDname(String dname) {
              this.dname = dname;
          }
      }
      
      //员工类
      public class Employee {
          private String ename;
          private String gender;
      
          //员工属于某一个部门，适用对象形式表示
          private Dept dept;
      
          public void setEname(String ename) {
              this.ename = ename;
          }
      
          public void setGender(String gender) {
              this.gender = gender;
          }
      }
      ```
   3. 在spring配置文件中进行配置
      ```xml
      <bean id="employee" class="com.spring5.bean.Employee">
              <property name="ename" value="lucy"/>
              <property name="gender" value="女"/>
              <property name="dept">
                  <bean class="com.spring5.bean.Dept">
                      <property name="dname" value="财务"/>
                  </bean>
              </property>
          </bean>
      ```
4. 注入属性-级联赋值
   1. 第一种写法
      ```xml
      <!--级联赋值-->
          <bean id="employee" class="com.spring5.bean.Employee">
              <!--设置两个普通属性-->
              <property name="ename" value="lucy"/>
              <property name="gender" value="女"/>
              <!--级联赋值-->
              <property name="dept" ref="dept"/>
          </bean>
          <bean id="dept" class="com.spring5.bean.Dept">
              <property name="dname" value="安保"/>
          </bean>
      ```
   2. 第二种写法
      ```xml
      <!--级联赋值-->
          <bean id="employee" class="com.spring5.bean.Employee">
              <!--设置两个普通属性-->
              <property name="ename" value="lucy"/>
              <property name="gender" value="女"/>
              <!--级联赋值-->
              <property name="dept" ref="dept"/>
              <property name="dept.dname" value="技术部"/>
          </bean>
          <bean id="dept" class="com.spring5.bean.Dept">
              <!--<property name="dname" value="安保"/>-->
          </bean>
      ```

# IOC操作 Bean管理（xml注入集合属性）

1. 注入数组类型属性
2. 注入List集合类型属性
3. 注入Map集合类型属性
   1. 创建类，定义数组，list，map，set类型属性，生成对应set方法
      ```java
      public class Student {
          //1 数组类型属性
          private String[] subjects;
      
          //2 list集合类型属性
          private List<String> list;
      
          //3 map集合类型属性
          private Map<String, String> maps;
      
          //4 set集合类型属性
          private Set<String> sets;
      
          public void setSets(Set<String> sets) {
              this.sets = sets;
          }
      
          public void setList(List<String> list) {
              this.list = list;
          }
      
          public void setMaps(Map<String, String> maps) {
              this.maps = maps;
          }
      
          public void setSubjects(String[] subjects) {
              this.subjects = subjects;
          }
      }
      ```
   2. 在spring配置文件进行配置
      ```xml
      <bean id="student" class="com.spring5.collectiontype.Student">
              <!-- 1 数组类型属性注入 -->
              <property name="subjects">
                  <array>
                      <value>java课程</value>
                      <value>数据库课程</value>
                  </array>
              </property>
      
              <!-- 2 list类型属性注入 -->
              <property name="list">
                  <list>
                      <value>张三</value>
                      <value>李四</value>
                  </list>
              </property>
      
              <!-- 3 map类型属性注入 -->
              <property name="maps">
                  <map>
                      <entry key="JAVA" value="java"/>
                      <entry key="PHP" value="php"/>
                  </map>
              </property>
      
              <!-- 4 set类型属性注入 -->
              <property name="sets">
                  <set>
                      <value>Mysql</value>
                      <value>Redis</value>
                  </set>
              </property>
          </bean>
      ```
   3. 在集合里面设置对象类型值
      ```xml
      <!-- 创建多个Subject对象 -->
          <bean id="subject1" class="com.spring5.collectiontype.Subject">
              <property name="name" value="Spring5框架"/>
          </bean>
          <bean id="subject2" class="com.spring5.collectiontype.Subject">
              <property name="name" value="MyBatis框架"/>
          </bean>
          
      <!-- 注入list集合类型，值是对象 -->
              <property name="subjects">
                  <list>
                      <ref bean="subject1"/>
                      <ref bean="subject2"/>
                  </list>
              </property>
      ```
   4. 把集合注入部分提取出来
      1. 在spring配置文件中引入名称空间util
         ```xml
         <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:util="http://www.springframework.org/schema/util"
                xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
         
         ```
      2. 使用util标签完成list集合注入提取
         ```xml
         <!-- 1 提取list集合类型属性注入 -->
             <bean id="book" class="com.spring5.collectiontype.Book">
                 <property name="list" ref="books"/>
             </bean>
         
             <util:list id="books">
                 <value>老人与海</value>
                 <value>傲慢与偏见</value>
             </util:list>
         ```

# IOC操作 Bean管理（FactoryBean）

1. Spring有两种类型bean，一种是普通bean，另一种是工厂bean（FactoryBean）
2. 普通bean：在配置文件中定义bean类型就是返回类型
3. 工厂bean：在配置文件定义bean类型可以和返回类型不一样

第一步	创建类，让这个类作为工厂bean，实现接口FactoryBean

第二步	实现接口里面的方法，在实现的方法中定义返回的bean类型

```java
public class MyBean implements FactoryBean<Subject> {
    @Override
    public Subject getObject() throws Exception {
        return new Subject();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
```

# IOC操作 Bean管理（bean作用域）

1. 在Spring里面，设置创建bean实例是单实例还是多实例
2. 在Spring中，默认情况下，创建的bean是单实例对象
3. 设置单实例/多实例
   1. 在spring配置文件的bean标签中有属性（scope）用于设置单实例还是多实例
   2. scope属性值
      1. 默认值，singleton，表示单实例对象
      2. prototype，表示是多实例对象
         ```xml
         <bean id="book" class="com.spring5.collectiontype.Book" scope="prototype">
                 <property name="list" ref="books"/>
             </bean>
         ```
      3. singleton和prototype的区别
         
         singleton表示单实例。prototype表示多实例
         
         设置scope值是singleton的时候，加载spring配置文件时就会创建单实例对象
         
         设置scope值是prototype的时候，不是在加载spring配置文件的时候创建对象，而是在调用getBean方法的时候创建多实例对象
      4. request，session

# IOC操作 Bean管理（bean生命周期）

1. 生命周期
   
   从对象创建到对象销毁的过程
2. bean生命周期
   1. 通过构造器创建bean实例（执行无参构造）
   2. 为bean的属性设置值和对其他bean的引用（调用set方法）
   3. 调用bean的初始化的方法（需要进行配置初始化的方法）
   4. bean可以使用（对象获取到了）
   5. 当容器关闭时，调用bean的销毁方法（需要进行配置销毁的方法）
3. 演示bean生命周期
   ```java
   public class Orders {
       private String oname;
   
       public Orders() {
           System.out.println("无参构造器被调用...");
       }
   
       public void setOname(String oname) {
           System.out.println("set方法被调用...");
           this.oname = oname;
       }
   
       //创建执行的初始化方法
       public void initMethod() {
           System.out.println("调用初始化方法...");
       }
   
       //创建执行的销毁方法
       public void destroyMethod() {
           System.out.println("调用销毁方法...");
       }
   }
   ```
   ```xml
   <bean id="orders" class="com.spring5.bean.Orders" init-method="initMethod" destroy-method="destroyMethod">
           <property name="oname" value="手机"/>
       </bean>
   ```
   ```java
   public void test4() {
           ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean04.xml");
           Orders orders = applicationContext.getBean("orders", Orders.class);
   
           //手动让bean实例销毁
           ((ClassPathXmlApplicationContext) applicationContext).close();
       }
   ```
4. 加上bean的后置处理器，bean生命周期有七步
   
   在第三步前，把bean实例传递给bean后置处理器的方法
   
   **postProcessBeforeInitialization(Object bean, String beanName)**
   
   在第三步后，把bean实例传递给bean后置处理器的方法
   
   **postProcessAfterInitialization(Object bean, String beanName)**
5. 演示添加后置处理器效果
   1. 创建类，实现接口BeanPostProcessor，创建后置处理器
      ```java
      public class MyBeanPost implements BeanPostProcessor {
          @Override
          public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
              System.out.println("在初始化之前执行的方法...");
              return bean;
          }
      
          @Override
          public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
              System.out.println("在初始化之后执行的方法...");
              return bean;
          }
      }
      ```
   2. 在xml文件中配置后置处理器
      ```xml
      <!-- 配置后置处理器 -->
          <bean id="myBeanPost" class="com.spring5.bean.MyBeanPost"/>
      ```

# IOC操作 Bean管理（xml自动装配）

1. 什么是自动装配
   
   根据指定装配规则（属性名称或者属性类型），Spring自动减匹配的属性值进行注入
2. 演示自动装配过程
   1. 根据属性名称自动注入
      ```xml
      <!--实现自动装配
          bean标签属性autowire，配置自动装配
          autowire属性常用两个值：byName根据属性名注入，注入bean的id值要跟类的属性名称一致
                                byType根据属性类型注入-->
          <bean id="employee" class="com.spring5.autowire.Employee" autowire="byName">
              <!--<property name="dept" ref="dept"/>-->
              <property name="dept" ref="dept"/>
          </bean>
          <bean id="dept" class="com.spring5.autowire.Dept"/>
      ```
   2. 根据属性类型自动注入
      ```xml
      <!--实现自动装配
          bean标签属性autowire，配置自动装配
          autowire属性常用两个值：byName根据属性名注入，注入bean的id值要跟类的属性名称一致
                                byType根据属性类型注入-->
          <bean id="employee" class="com.spring5.autowire.Employee" autowire="byType">
              <!--<property name="dept" ref="dept"/>-->
              <property name="dept" ref="dept"/>
          </bean>
          <bean id="dept" class="com.spring5.autowire.Dept"/>
      ```

# IOC操作 Bean管理（外部属性文件）

1. 直接配置数据库信息
   1. 配置德鲁伊连接池
      ```xml
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
              <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
              <property name="url" value="jdbc:mysql://localhost:3306/fruit"/>
              <property name="username" value="root"/>
              <property name="password" value="ckj"/>
          </bean>
      ```
   2. 引入德鲁伊连接池依赖
2. 引入外部属性文件配置数据库连接池
   1. 创建外部属性文件，properties格式文件，写入数据库信息
      ```properties
      driverClassName=com.mysql.jdbc.Driver
      url=jdbc:mysql://localhost:3306/java_web
      username=root
      password=ckj
      
      initialSize=10
      
      maxActive=50
      
      maxWait=5000
      ```
   2. 把外部properties属性文件引入到spring配置文件中
      1. 引入context名称空间
         ```xml
         xmlns:context="http://www.springframework.org/schema/context"
                xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
         ```
      2. 在spring配置文件使用标签引入外部属性文件
         ```xml
         <context:property-placeholder location="classpath:jdbc.properties"/>
         
             <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
                 <property name="driverClassName" value="${driverClassName}"/>
                 <property name="url" value="${url}"/>
                 <property name="username" value="${username}"/>
                 <property name="password" value="${password}"/>
             </bean>
         ```

# IOC操作 Bean管理（基于注解方式）

1. 什么是注解
   1. 注解是代码里的特殊标记，格式：@注解名称(属性名称=属性值,属性名称=属性值...)
   2. 使用注解，注解作用在类上面，方法上面，属性上面
   3. 使用注解的目的：简化xml配置
2. Spring针对Bean管理中创建对象提供注解
   1. @Component
   2. @Service
   3. @Controller
   4. @Repository
   
   上面四个注解功能是一样的，都可以用来创建bean实例
3. 基于注解方式实现对象创建
   1. 第一步 引入依赖
   2. 第二步 开启组件扫描
      ```xml
      <!--开启组件扫描
          1 如果扫描多个包，多个包用逗号隔开
          2 扫描包上层目录-->
          <!--<context:component-scan base-package="com.spring5.dao,com.spring5.service"/>-->
          <context:component-scan base-package="com.spring5"/>
      ```
   3. 第三部 在类上面添加创建对象注解
      ```java
      //在注解里面value属性值可以省略不写，
      //默认值是类名称，首字母小写
      //UserService --> userServicec
      @Component(value = "userService")
      public class UserService {
      
          public void add() {
              System.out.println("service add...");
          }
      }
      ```
4. 开启组件扫描细节配置
   ```xml
   <!--实例一-->
       <!--不使用默认过滤器，而是只扫描注解为Controller的类-->
       <context:component-scan base-package="com.spring5" use-default-filters="false">
           <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
       
       <!--实例二-->
       <!--扫描包所有内容, context:exclude-filter:设置哪些内容不进行扫描-->
       <context:component-scan base-package="com.spring5">
           <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
   ```
5. 基于注解方式实现属性注入
   ```java
   @Repository
   public class UserDAOImpl implements UserDAO {
       @Override
       public void add() {
           System.out.println("dao add...");
       }
   }
   
   ```
   1. @AutoWired：根据属性类型进行自动装配
      ```java
      @Service
      public class UserService {
          //不需要添加set方法
          //添加注入属性的注解
          @Autowired //根据类型进行注入
          private UserDAO userDAO;
      
          public void add() {
              System.out.println("service add...");
              userDAO.add();
          }
      }
      ```
   2. @Qualifier：根据属性名称进行注入
      
      @Qualifier注解需要和@AutoWired注解一起使用（解决当接口实现类有多个时，无法确定注入哪个实现类的问题）
      ```java
      @Autowired //根据类型进行注入
          @Qualifier(value = "userDAOImpl")
          private UserDAO userDAO;
      ```
   3. @Resource：可以根据类型注入，可以根据名称注入
      ```java
      //@Resource //默认根据名称进行注入
          @Resource(type = UserDAOImpl.class) //按照类型注入
          private UserDAO userDAO;
      
      ```
   4. @Value：注入普通类型属性
      ```
      @Value(value = "小明")
          private String uname;
      ```
6. 完全注解开发
   1. 创建配置类，替代xml配置文件
      ```java
      @Configuration //作为配置类，替代xml配置文件
      @ComponentScan(basePackages = {"com.spring5"})
      public class SpringConfig {
      }
      ```
   2. 编写测试类
      ```java
      @Test
          public void testService2() {
              //加载配置类
              ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
              UserService userService = applicationContext.getBean("userService", UserService.class);
              userService.add();
          }
      ```