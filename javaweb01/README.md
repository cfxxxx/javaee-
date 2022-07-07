# Tomcat的安装和配置

1. 解压：不要有中文不要有空格
2. 目录结构说明：
    - *bin            可执行文件目录*
    - *conf            配置文件目录*
    - *lib            存放lib的目录*
    - *logs            日志文件目录*
    - *webapps    项目部署的目录*
    - *work        工作目录*
    - *temp        临时目录*
3. 配置环境变量，让tomcat能够运行，因为tomcat也是用java和C来写的，因此需要JRE，所以需要配置JAVA_HOME
4. 启动tomcat，然后访问主页

# 新建Web项目，并在tomcat中部署

1. 新建项目 - 新疆模块
2. 在模块中添加 web
3. 创建artifact - 部署包
4. lib - artifact

   先有artifact，后来才添加的mysql.jar。此时，这个jar包并没有添加到部署包中，那么在projectSettings中有个Problems中会有提示，我们点击fix选择add to...

   另外，我们也可以直接把lib文件夹直接新建在WEB-INF下
5. 再部署的时候，修改application Context。然后再回到server选项卡，检查URL的值。URL的值指的是tomcat启动完成后自动打开你指定的浏览器，然后默认访问的网址。

   启动后，报错404,404意味着找不到指定的资源。

   如果我们的网址是：http://localhost:8080/ ，那么表明我们访问的是index.html

   我们可以通过\<welcome-file-list>标签进行设置欢迎页（在tomcat得到web.xml中设置，或者在自己项目的web.xml设置）
6. 405问题：当前请求的方法不支持。比如，我们表单method=post，那么Servlet必须对应doPost，否则报405错误
7. 空指针或者是NumberFormatException，因为有价格和库存，如果价格取不到，结果你想对null进行Integer.parseInt()，就会报错，错误原因大部分是因为name="price"此处写错了，结果在Servlet端还是使用request.getParameter("price")去获取
8. <url-pattern>中以/开头

# 设置编码

**tomcat8之前，设置编码：**

1. get请求方式：====
   ```java
   //get方式目前不需要设置编码（基于tomcat8）
   String fname = request.getParameter("fname");
   //1.将字符串打散成字节数组
   byte[] bytes = fname.getBytes("ISO-8859-1");
   //2.将字节数组按照设定的编码重新组装成字符串
   fname = new String(bytes, "UTF-8");
   ```
2. post请求方式：
   ```java
   request.setCharacterEncoding("UTF-8");
   ```

**tomcat8开始，设置编码，只需要针对post方式**：

```java
request.setCharacterEncoding("UTF-8");
```

**注意：**

==需要注意的是，设置编码这一行代码必须在所有的获取参数动作之前==

# Servlet的继承关系 - 重点查看的是服务方法（service()）

1. 继承关系
   > javax.servlet.Servlet接口
   >
   > javax.servlet.GenericServlet抽象类
   >
   > javax.servlet.http.HttpServlet抽象子类
2. 相关方法

   **javax.servlet.Servlet接口：**

   	*void init(config)*		-初始化方法

   	*void service(request, response)*		-服务方法

   	*void destory()*			-销毁方法

   **javax.servlet.GenericServlet抽象类：**

   	*void service(request, response)*		-仍然是抽象的

   **javax.servlet.http.HttpServlet抽象子类：**

   	*void service(request, response)* 		-不是抽象的
   1. 获取请求的方式
      ```java
      String method = req.getMethod();
      ```
   2. 各种if判断，根据请求方式不同，决定去调用不同的do方法
      ```java
      if (method.equals("GET")) {
          this.doGet(req, resp);
        } else if (method.equals("HEAD")) {
          this.doHead(req, resp);
        } else if (method.equals("POST")) {
          this.doPost(req, resp);
        } else if (method.equals("PUT"))
      ```
   3. 在HttpServlet这个抽象类中，do方法都差不多
      ```java
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              String msg = lStrings.getString("http.method_get_not_supported");
              this.sendMethodNotAllowed(req, resp, msg);
          }
          
       private void sendMethodNotAllowed(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
              String protocol = req.getProtocol();
              if (protocol.length() != 0 && !protocol.endsWith("0.9") && !protocol.endsWith("1.0")) {
                  resp.sendError(405, msg);
              } else {
                  resp.sendError(400, msg);
              }
          }
      ```
3. 小结：
   1. 继承关系：HttpServlet -> GenericServlet -> Servlet
   2. Servlet中的核心方法：init(), service(), destory()
   3. 服务方法：当有请求过来时，service方法会自动相应（其实是tomcat容器调用）。

      在HttpServlet中我们会去分析请求的方式：到底是get，post，head还是delete等

      然后再决定调用的是哪个do开头的方法

      那么在HttpServlet中这些do方法默认都是405的实现风格-要我们子类去实现对应的方法，否则默认会报405错误
   4. 因此，我们在新建Servlet时，我们才会去考虑请求方法，从而决定重写哪个do方法

# Servlet的生命周期

1. 生命周期：从出生到死亡的过程就是生命周期，对应Servlet中的三个方法：init(),service(),destory()
2. 默认情况下：

   第一次接受请求时，这个Servlet会进行实例化（调用构造方法），初始化（调用init()），然后服务（调用service()）

   从第二次请求开始，每一次都是服务

   当容器关闭时，其中所有的servlet实例会被销毁，调用销毁方法
3. 通过案例我们发现：
   - Servlet实例tomcat只会创建一个，所有的请求都是这个实例去响应
   - 默认情况下，第一次请求时，tomcat才会去实例化，初始化，然后再服务。这样的好处是什么？提高系统的启动速度。这样的缺点是什么？第一次请求时，耗时较长
   - 因此得出结论：如果需要提高系统的启动速度，可以使用当前默认情况。如果要提高响应速度，我们应该设置Servlet的初始化时机
4. Servlet的初始化时机：
   - 默认是第一次接受请求时，实例化，初始化
   - 我们可以通过\<load-on-startup>来设置servlet启动的先后顺序，数字越小，启动越靠前，最小值0
5. Servlet在容器中是：单例的，线程不安全的
   - 单例：所有的请求都是同一个实例去响应
   - 线程不安全：一个线程需要根据这个实例中的某个成员变量值去做逻辑判断。但是在中间某个时机，另一个线程改变了这个成员变量的值，从而导致第一个线程的执行路径发生了变化
   - 我们已经知道了servlet是线程不安全的，给我们的启发是：尽量不要在servlet中定义成员变量，如果不得不定义成员变量，那么不要去：1.修改成员变量的值  2.根据成员变量的值做一些逻辑判断

# Http协议

## 1.介绍

Http：超文本传输协议，Http是无状态的。Http最大的作用就是确定了请求和响应数据的格式。浏览器发送给服务器的数据：请求报文；服务器返回给浏览器的数据：响应报文

## 2.请求报文

请求报文的三个部分：请求行，请求消息头，请求主体

### 请求行

作用：展示当前请求的最基本信息

> POST /dynamic/target.jsp HTTP/1.1

- 请求方式
- 访问地址
- Http协议的版本（一般都是HTTP1.1）

### 请求消息头

作用：通过具体的参数对本次请求进行详细的说明

格式：键值对，键和值之间用冒号隔开

相对比较重要的请求消息头：

|名称|功能|
|--|--|
|Host|服务器的主机地址|
|Accept|声明当前请求能够接受的【媒体类型】|
|Referer|当前请求来源页面的地址|
|Content-Length|请求体内容的长度|
|Content-Type|请求体的内容类型，这一项的具体值是媒体类型中的某一种|
|Cookie|浏览器访问服务器时携带的Cookie数据|

### 请求主体

三种情况：

1. get方式：没有请求体，但是有一个queryString（跟在url后面）
2. post方式：有请求体，form data
3. json格式：有请求体，request payload

## 3.响应报文

响应也包含三个部分：响应行，响应头，响应体

### 响应状态行

协议，响应状态码（200），响应状态（ok）

//200：正常响应

//404：找不到资源

//405：请求方式不支持

//500：服务器内部错误

### 响应消息头

包含了服务器的信息；服务器发送给浏览器的信息（内容的媒体类型，编码，内容长度等）

### 响应体

响应的实际内容
<br/>

# 会话

1. HTTP是无状态的

   -- HTTP无状态：服务器无法判断这两次请求是同一客户端发过来的，还是不同的客户端发过来的

   -- 无状态带来的现实问题：第一次请求是添加商品到购物车，第二次请求是结账；如果这两次请求服务器无法区分是同一个用户的，那么就会导致混乱

   -- 通过会话跟踪技术来解决无状态的问题
2. 会话跟踪技术

   -- 客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后相应给客户端

   -- 下次客户端给服务器发请求时，会把sessionID带给服务器，那么服务器就能获取到了，那么服务器就判断这一次请求和上次某次请求是同一个客户端，从而能够区分开客户端

   -- 常用的API：

   request.getSession()	->	获取当前的会话，没有则创建一个新的会话

   request.getSession(true)		->	效果和不带参数相同

   request.getSession(false)	->	获取当前会话，没有则返回null，不会创建新的

   session.getId()		->	获取sessionID

   session.isNew()	->	判断当前session是否是新的

   session.getMaxInactiveInterval()	->	session的最大非激活间隔时长，默认1			       800秒

   session.getMaxInactiveInterval()

   session.incalidate()	->	强制性让会话立即失效

   session.getCreationTime()	->	获取会话创建时间

   session.getLastAccessdTime	->	获取最近一次会话访问时间
3. session保存作用域

   -- session保存作用域是和具体的某一个session对应的

   -- 常用API：

   void session.setAttribute(k, v)

   Object session.getAttribute(k)

   void removeAttribute(k)

# 服务器内部转发以及客户端重定向

1. 服务器内部转发：request.getRequestDispatcher("...").forward(request, response)

   -- 一次请求响应的过程，对于客户端而言，内部经过了多少次转发，客户端是不知道的

   -- 地址栏没有变化
2. 客户端重定向：response.sendRedirect("...")

   -- 两次请求响应的过程，客户端知道请求url有变化

   -- 地址栏有变化

# Thymeleaf - 视图模板技术

1. 添加thymeleaf的jar包
2. 新建一个Servlet类ViewBaseServlet
3. 在web.xml文件中添加配置

   -- 配置前缀 prefix

   -- 配置后缀 suffix
4. 使得我们的Servlet继承ViewBaseServlet
5. 根据逻辑视图名称得到物理视图名称

   super.processTemplate("index", request, response);
6. 使用thymeleaf的标签

   th:if		th:unless		th:each		th:text