# Tomcat的安装和配置

1. 解压：不要有中文不要有空格
2. 目录结构说明：
    - *bin			可执行文件目录*
    - *conf			配置文件目录*
    - *lib			存放lib的目录*
    - *logs			日志文件目录*
    - *webapps	项目部署的目录*
    - *work		工作目录*
    - *temp		临时目录*
3. 配置环境变量，让tomcat能够运行，因为tomcat也是用java和C来写的，因此需要JRE，所以需要配置JAVA_HOME
4. 启动tomcat，然后访问主页

# 新建Web项目，并在tomcat中部署