- SPI（Service Provider Interface），是JDK内置的一种服务提供发现机制，可以用来启用框架扩展和替换组件.java中SPI机制主要思想是将装配的控制权移到程序之外，在模块化设计中这个机制尤其重要，其核心思想就是**解耦**。
- 核心类 `java.util.ServiceLoader`
- 流程
	- 定义一个接口
	- 使用者自己完成接口的多个实现
	- 使用者在自己的项目中,`META-INF/services/${interface_name} ` 目录下新建文件,并写入自己实现的接口的全路径
		- ```
		  com.vivo.study.spidemo.spi.impl.ImageHello
		  com.vivo.study.spidemo.spi.impl.TextHello
		  ```
	- 使用 `ServiceLoader `来加载配置文件中指定的实现
		- ```java
		  		ServiceLoader<HelloSPI> serviceLoader = ServiceLoader.load(HelloSPI.class);
		          // 执行不同厂商的业务实现，具体根据业务需求配置
		          for (HelloSPI helloSPI : serviceLoader) {
		              helloSPI.sayHello();
		          }
		  ```
-