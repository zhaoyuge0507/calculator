# 使用一个轻量的基础 JDK 镜像
FROM eclipse-temurin:17-jre-alpine

# 设置工作目录
WORKDIR /app

# 直接将你本地已经编译好的 Fat Jar 复制进镜像
COPY target/calculator-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# 设置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]