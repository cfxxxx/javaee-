# AOP（概念）

1. 什么是AOP
   1. 面向切面编程（方面），利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发效率
   2. 通俗描述：不通过修改源代码方式，在主干功能里面添加新功能

# AOP（底层原理）

1. AOP底层使用动态代理
   
   有两种情况的动态代理
   1. 第一种 有接口情况，使用JDK动态代理
      
      **创建接口实现类的代理对象，实现增强类的方法**
   2. 第二种 没有接口情况，使用CGLIB动态代理
      
      **创建当前类之类的代理对象，实现增强类的方法**

# AOP（JDK动态代理）

1. 使用JDK动态代理，使用Proxy类里面的方法创建代理对象
   1. 调用newProxyInstance方法
      
      方法有三个参数
      
      第一个参数，类加载器
      
      第二个参数，增强方法所在的类，这个类实现的接口，支持多个接口
      
      第三个参数，实现这个接口InvocationHandler，创建代理对象，写增强的方法
   2. 编写JDK动态代理代码
      1. 创建接口，定义方法
         ```java
         public interface UserDAO {
             public int add(int a, int b);
             public String update(String id);
         }
         ```
      2. 创建接口实现类，实现方法
         ```java
         public class UserDAOImpl implements UserDAO {
             @Override
             public int add(int a, int b) {
                 return a + b;
             }
         
             @Override
             public String update(String id) {
                 return id;
             }
         }
         ```
      3. 使用Proxy类创建接口代理对象
         ```java
         public class UserDAOProxy implements InvocationHandler {
             private Object object;
         
             public UserDAOProxy(Object object) {
                 this.object = object;
             }
         
             @Override
             public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                 System.out.println("方法之前被执行...");
         
                 Object res = method.invoke(object, args);
         
                 System.out.println("方法之后被执行...");
                 return res;
             }
         }
         ```
         ```java
         Class[] interfaces = {UserDAO.class};
                 //创建接口实现类代理对象
                  UserDAO userDAO = (UserDAO)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDAOProxy(new UserDAOImpl()));
                 int add = userDAO.add(1, 2);
         ```

# AOP（术语）

1. 连接点
   
   类中可以被增强的方法叫做连接点
2. 切入点
   
   实际被增强的方法叫做切入点
3. 通知（增强）
   1. 实际增强的逻辑部分称为通知
   2. 通知有多种类型
      1. 前置通知
      2. 后置通知
      3. 环绕通知
      4. 异常通知
      5. 最终通知
4. 切面
   
   把通知应用到切入点的过程叫做切面

# AOP操作（准备）

1. Spring框架一般都是基于AspectJ实现AOP操作
   
   AspectJ不是Spring组成部分，是独立的AOP框架，一般吧AspectJ和Spring框架一起使用，进行AOP操作
2. 基于AspectJ实现AOP操作
   1. 基于xml配置文件实现
   2. 基于注解方式实现（使用）
3. 在项目工程中引入AOP相关依赖
4. 切入点表达式
   1. 切入点表达式作用：知道对哪个类里面的哪个方法进行增强
   2. 语法结构：
      
      execution([权限修饰符] [返回类型] [类全路径] [方法名称] [参数列表])
      
      例：对add方法进行增强 execution(*com.spring5.dao.UserDAO.add(..))
      
      	   对所有方法进行增强 execution(*com.spring5.dao.UserDAO.\*(..))
      
             对包中的所有类和类中的所有方法进行增强 execution(*com.spring5.dao.\*.\*(..))

# AOP操作（AspectJ注解）

1. 创建类，在类里面定义方法
   ```java
   //目标类
   public class User {
       public void add() {
           System.out.println("add...");
       }
   }
   ```
2. 创建增强类
   
   在增强类里面，创建方法，让不同的方法代表不同的通知类型
   ```java
   //增强类
   public class UserProxy {
       //前置通知
       public void before() {
           System.out.println("before...");
       }
   }
   ```
3. 进行通知的配置
   1. 在spring配置文件中，开启注解扫描（或者使用配置类）
      ```java
      @Configuration
      @ComponentScan(basePackages = {"com.spring5"})
      @EnableAspectJAutoProxy(proxyTargetClass = true)
      public class SpringConfig {
      }
      ```
      ```xml
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                 http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
      
          <!--开启AspectJ生成代理对象-->
          <aop:aspectj-autoproxy/>
      </beans>
      ```
   2. 使用注解创建User和UseProxy对象
      ```java
      @Component(value = "user")
      public class User {
          public void add() {
              System.out.println("add...");
          }
      }
      ```
   3. 在增强类上面添加注解@Aspect
      ```java
      @Component
      @Aspect
      public class UserProxy {
          //前置通知
          @Before(value = "execution(* com.spring5.aop_annotation.User.add(..))")
          public void before() {
              System.out.println("before...");
          }
      }
      ```
   4. 配置不同类型的通知
      1. 在增强类的里面，在作为通知方法上面添加通知类型注解，使用切入点表达式配置
         ```java
         @Component
         @Aspect
         public class UserProxy {
             //前置通知
             @Before(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void before() {
                 System.out.println("before...");
             }
         
             //后置通知
             @After(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void after() {
                 System.out.println("after...");
             }
         
             @AfterReturning(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void afterReturning() {
                 System.out.println("afterReturning...");
             }
         
             //异常通知
             @AfterThrowing(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void afterThrowing() {
                 System.out.println("afterThrowing...");
             }
         
             //环绕通知
             @Around(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
                 System.out.println("aroundBefore...");
                 //被增强的方法执行
                 proceedingJoinPoint.proceed();
                 System.out.println("aroundAfter...");
             }
         }
         ```
   5. 公共切入点抽取
      ```java
      //相同切入点抽取
          @Pointcut(value = "execution(* com.spring5.aop_annotation.User.add(..))")
          public void pointDemo() {
          }
      
          //前置通知
          @Before(value = "pointDemo()")
          public void before() {
              System.out.println("before...");
          }
      ```
   6. 有多个增强类对同一个方法进行增强，设置增强类优先级
      1. 在增强类上面添加注解@Order(数字类型值)，数字类型值越小优先级越高
         ```java
         @Component
         @Aspect
         @Order(1)
         public class PersonProxy {
             @Before(value = "execution(* com.spring5.aop_annotation.User.add(..))")
             public void before() {
                 System.out.println("Person before...");
             }
         }
         ```

# AOP操作（AspectJ配置文件）

1. 创建两个类，增强类和被增强类，创建方法
2. 在spring配置文件中创建两个类对象
   ```xml
   <!--创建对象-->
       <bean id="book" class="com.spring5.aopxml.Book"/>
       <bean id="bookProxy" class="com.spring5.aopxml.BookProxy"/>
   ```
3. 在spring配置文件中配置切入点
   ```xml
   <!--配置aop增强-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="p" expression="execution(* com.spring5.aopxml.Book.buy(..))"/>
           <!--配置切面-->
           <aop:aspect ref="bookProxy">
               <!--增强作用在具体的方法上-->
               <aop:before method="before" pointcut-ref="p"/>
           </aop:aspect>
       </aop:config>
   ```