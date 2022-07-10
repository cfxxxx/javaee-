# JDBCTemplate(概念和准备)

1. 什么是JDBCTemplate
   
   Spring框架对JDBC进行封装，使用JDBCTemplate方便实现对数据库的操作
2. 准备工作
   1. 引入相关jar包
   2. 在spring配置文件配置数据库连接池
      ```xml
      <!--引入外部属性文件-->
          <context:property-placeholder location="classpath:jdbc.properties"/>
      
          <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
              <property name="driverClassName" value="${driverClassName}"/>
              <property name="url" value="${url}"/>
              <property name="username" value="${username}"/>
              <property name="password" value="${password}"/>
          </bean>
      ```
   3. 配置JDBCTemplate对象，注入DataSource
      ```xml
      <!--JDBCTemplate对象-->
          <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
              <!--注入DataSource-->
              <property name="dataSource" ref="dataSource"/>
          </bean>
      ```
   4. 创建dao类，service类，在dao注入JDBCTemplate对象
      ```java
      @Repository
      public class BookDAOImpl implements BookDAO{
      
          @Autowired
          private JdbcTemplate jdbcTemplate;
      }
      ```
      ```java
      @Service
      public class BookService {
          @Autowired
          private BookDAO bookDAO;
      }
      ```

# JDBCTemplate操作数据库（添加）

1. 对应数据库表创建实体类
   ```java
   public class User {
       private Integer userId;
       private String username;
       private String userStatus;
   
       public User() {
       }
   
       public Integer getUserId() {
           return userId;
       }
   
       public void setUserId(Integer userId) {
           this.userId = userId;
       }
   
       public String getUsername() {
           return username;
       }
   
       public void setUsername(String username) {
           this.username = username;
       }
   
       public String getUserStatus() {
           return userStatus;
       }
   
       public void setUserStatus(String userStatus) {
           this.userStatus = userStatus;
       }
   }
   
   ```
2. 编写service和dao
   1. 在dao进行数据库添加操作
   2. 调用JDBCTemplate对象里面update进行添加操作
      ```java
      @Repository
      public class BookDAOImpl implements BookDAO{
      
          @Autowired
          private JdbcTemplate jdbcTemplate;
      
      
          @Override
          public void add(User user) {
              String sql = "insert into t_user values(?,?,?)";
              int update = jdbcTemplate.update(sql, user.getUserId(), user.getUsername(), user.getUserStatus());
              System.out.println(update);
          }
      }
      ```

# JDBCTemplate操作数据库（修改和删除）

1. 修改
   ```java
    @Override
       public void update(User user) {
           String sql = "update t_user set username=?,ustatus=? where user_id=?";
           int update = jdbcTemplate.update(sql, user.getUsername(), user.getUserStatus(), user.getUserId());
           System.out.println(update);
       }
   ```
2. 删除
   ```java
   @Override
       public void delete(Integer id) {
           String sql = "delete from t_user where user_id=?";
           int delete = jdbcTemplate.update(sql, id);
           System.out.println(delete);
       }
   ```

# JDBCTemplate操作数据库（查询返回某个值）

1. 查询表里面有多少条记录，返回是某个值
2. 使用JDBCTemplate实现查询返回某个值
   ```java
   @Override
       public int selectCount() {
           String sql = "select count(*) from t_user";
           return jdbcTemplate.queryForObject(sql, Integer.class);
       }
   ```

# JDBCTemplate操作数据库（查询返回对象）

1. 查询用户详情信息
2. JDBCTemplate实现查询返回对象
   ```java
    @Override
       public User findOne(Integer id) {
           String sql = "select * from t_user where User_id=?";
           return jdbcTemplate.queryForObject(sql, User.class, id);
       }
   ```

# JDBCTemplate操作数据库（查询返回集合）

1. 查询用户列表分页
2. 调用JDBCTemplate方法实现查询返回集合
   ```java
   @Override
       public List<User> findAll() {
           String sql = "select * from t_user";
           return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
       }
   ```

# JDBCTemplate操作数据库（批量操作）

1. 批量操作：操作表里面多条记录
   ```java
   @Override
       public void batchAddUser(List<Object[]> batchArgs) {
           String sql = "insert into t_user values(?,?,?)";
           int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
           System.out.println(Arrays.toString(ints));
       }
   ```

# 事务概念

1. 什么是事务
   1. 事务是数据库操作的最基本的单元，逻辑上的一组操作，要么都成功，如果有一个失败所有操作都失败
   2. 典型场景：银行转账
2. 事务四个特性（ACID）
   1. 原子性
   2. 一致性
   3. 隔离性
   4. 持久性

# 事务操作（搭建事务操作环境）

1. 创建数据库表，添加记录
2. 创建service，创建dao，完成对象创建和注入关系
   1. service注入dao，在dao注入JDBCTemplate，在JDBCTemplate注入DataSource
3. 在dao创建两个方法，多钱和少钱的方法，在service层实现转账

# 事务操作（Spring事务管理介绍）

1. 事务添加到JavaEE三层结构里面Service层（业务逻辑层）
2. 在Spring进行事务管理操作
   1. 有两种方式：编程式事务管理和声明式事务管理（使用）
3. 声明式事务管理
   1. 基于注解方式
   2. 基于xml配置文件方式
4. 在Spring进行声明式事务管理，底层使用AOP原理
5. Spring事务管理API
   1. 提供一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类

# 事务操作（注解声明式事务管理）

1. 在spring：配置文件配置事务管理器
   ```xml
   <!--创建事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <!--注入数据源-->
           <property name="dataSource" ref="dataSource"/>
       </bean>
   ```
2. 在spring配置文件，开启事务注解
   1. 在spring配置文件引入名称空间
      ```xml
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xmlns:tx="http://www.springframework.org/schema/tx"
             xmlns:context="http://www.springframework.org/schema/context"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                 http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
                                 http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
                                 http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      
      ```
   2. 开启事务注解
      ```xml
      <!--开启事务注解-->
          <tx:annotation-driven transaction-manager="transactionManager"/>
      ```
   3. 在service类上面（获取service类里面方法上面）添加事务注解
      ```java
      @Service
      @Transactional
      public class UserService {
          @Autowired
          private UserDAO userDAO;
      
          public void money() {
      //        try {
                  //第一步 开启事务
      
      
                  //第二步 进行业务操作
                  userDAO.reduceMoney();
                  userDAO.addMoney();
      
                  //第三步 没有发生异常，提交事务
      //        } catch (Exception e) {
      //            //第四步 出现异常，事务回滚
      //            throw new RuntimeException(e);
      //        }
          }
      }
      ```
      1. @Transactional，这个注解添加到类上面，也可以添加到方法上面
      2. 如果把注解添加到类上面，这个类里面所有的方法都添加事务
      3. 如果把这个注解添加到方法上面，为这个方法添加事务

# 事务操作（声明式事务管理参数配置）

1. propagation：事务传播行为
   
   当一个事务方法被另外一个事务方法调用的时候，这个事务方法如何进行处理
   
   事务的传播行为可以由传播属性指定，spring定义了7种类传播行为
   |传播属性|描述|
   |--|--|
   |REQUIRED|如果有事务在运行，当前的方法就在这个事务内运行，否则买酒启动一个新的事物，并在自己的事务内运行|
   |REQUIRED_NEW|当前的方法必须启动新事务，并在它自己的事务内运行，如果有事务正在运行，应该将它挂起|
   |SUPPORTS|如果有事务在运行，当前的方法就在这个事务内运行，否则他可以不运行在事务中|
   |NOT_SUPPORTED|当前的方法不应该运行在事务中，如果有运行的事务，将它挂起|
   |MANDATORY|当前的方法必须运行在事务内部，如果没有正在运行的事务，就抛出异常|
   |NEVER|当前的方法不应该运行在事务中，如果有运行的事务，就抛出异常|
   |NESTED|如果有事务在运行，当前的方法就应该在这个事务的嵌套事务内运行，否则，就启动一个新的事物，并在它自己的事务内运行|
2. ioslation：事务隔离级别
   1. 事务有特性成为隔离性，多事务操作之间不会产生影响，不考虑隔离性产生很多问题
   2. 有三个读问题：脏读，不可重复读，虚（幻）读
   3. 脏读：一个未提交事务读取到另一个未提交事务的数据
   4. 不可重复读：一个未提交事务读取到另一个提交事务中修改的数据
   5. 虚读：一个未提交事务读取到另一提交事务的添加数据
   6. 通过设置事务隔离性，解决读问题
      ```java
      @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
      ```
      |MySQL隔离级别|脏读|不可重复读|幻读|加锁读|
      |--|--|--|--|--|
      |读未提交（read uncommitted）|✓|✓|✓|不加锁|
      |读已提交（read committed）|×|✓|✓|不加锁|
      |可重复读（repeatable read）|×|×|×|不加锁|
      |可串行化（serializable）|×|×|×|加锁|
3. timeout：超时时间
   1. 事务需要在一定的时间内进行提交，如果不提交，进行回滚
   2. 默认值是-1，设置时间以秒为单位进行计算
4. readOnly：是否只读
   1. 读：查询操作，写：添加修改删除操作
   2. readOnly默认值false，表示可以查询，可以添加修改删除
   3. 设置readOnly值是true，只能查询
5. rollbackFor：回滚
   1. 设置出现哪些异常进行事务回滚
6. noRollbackFor：不回滚
   1. 设置出现哪些异常不进行事务回滚

# 事务操作（XML声明式）

1. 在spring配置文件中进行配置
   1. 第一步 配置事务管理器
      ```xml
      <!--创建事务管理器-->
          <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
              <!--注入数据源-->
              <property name="dataSource" ref="dataSource"/>
          </bean>
      ```
   2. 第二部 配置通知
      ```xml
      <!--配置通知-->
          <tx:advice id="txadvice">
              <!--配置事务参数-->
              <tx:attributes>
                  <!--指定哪种规则的方法上添加事务-->
                  <tx:method name="money" propagation="REQUIRED"/>
              </tx:attributes>
          </tx:advice>
      ```
   3. 第三步 配置切入点和切面
      ```xml
      <!--配置切入点和切面-->
          <aop:config>
              <!--配置切入点-->
              <aop:pointcut id="pt" expression="execution(* com.spring5.service.UserService.*(..))"/>
              <!--配置切面-->
              <aop:advisor advice-ref="txadvice" pointcut-ref="pt"/>
          </aop:config>
      ```

# 事务操作（完全注解声明式事务管理）

1. 创建配置类，使用配置类替代xml配置文件
   ```java
   @Configuration
   @ComponentScan(basePackages = "com.spring5")
   @EnableTransactionManagement
   public class TxConfig {
       //创建数据库连接池
       @Bean
       public DruidDataSource getDruidDataSource() {
           DruidDataSource druidDataSource = new DruidDataSource();
           druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
           druidDataSource.setUrl("jdbc:mysql://localhost:3306/java_web");
           druidDataSource.setUsername("root");
           druidDataSource.setPassword("ckj");
           return druidDataSource;
       }
   
       //创建JDBCTemplate对象
       @Bean
       public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
           JdbcTemplate jdbcTemplate = new JdbcTemplate();
           jdbcTemplate.setDataSource(dataSource);
           return jdbcTemplate;
       }
   
       //创建事务管理器对象
       @Bean
       public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
           DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
           dataSourceTransactionManager.setDataSource(dataSource);
           return dataSourceTransactionManager;
       }
   }
   
   ```
