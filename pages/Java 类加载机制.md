- ## 对象的创建\触发类加载的方式
	- new关键字
	- Class的newInstance方法
	- Constructor类的newInstance方法
	- clone方法
	- 反序列化
- ## 加载过程
	- ![image.png](../assets/image_1673522060461_0.png)
	- 加载
		- 根据查找路径找到相应的class文件然后导入，最终生成的Class类，存入 [[Java 方法区]]
		- 使用 ((63bfeccb-a23e-465f-beda-16fe4dbcaa19))
	- 验证
		- 检查加载的Class文件的正确性
	- 准备
		- 给类中的**静态变量**分配内存空间
	- 解析
		- 将 ((63bbf636-53a5-4b10-afe8-6cebec7eaa26)) 中的**符号引用**(标识)替换成直接引用(内存地址)
		- 延迟解析：在符号引用第一次被使用时完成
			- ```java
			  public class LinkTest {
			    public static void main(String[] args) {
			      ToBeLinked toBeLinked = null;
			      System.out.println("Test link.");
			    }
			  }
			  ```
			- 即使在编译后删除ToBeLinked字节码，再运行
	- 初始化
		- 对静态变量和静态代码块执行初始化工作
		- 当类第一次被主动使用(active use)，JVM 才会初始化类
			-
- 双亲委派模型
  id:: 63bfeccb-a23e-465f-beda-16fe4dbcaa19
	- 防止内存中出现多份同样的字节码。自己定义的`java.lang.String`的同名类与JDK内置的`java.lang.String` 保证他们的唯一性。