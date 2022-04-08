- 特点
	- 完全不受代理类必须实现接口的限制
	- 底层采用ASM字节码生成框架,使用字节码技术生成代理类，比使用Java反射效率要高
	- 不能对声明为final的方法进行代理，因为CGLib原理是动态生成被代理类的子类。
- 使用案例
	- 目标类
	  collapsed:: true
		- ```java
		  /**
		   * 没有实现接口，需要CGlib动态代理的目标类
		   * 
		   *
		   */
		  public class TargetObject {
		      public String method1(String paramName) {
		          return paramName;
		      }
		   
		      public int method2(int count) {
		          return count;
		      }
		   
		      public int method3(int count) {
		          return count;
		      }
		   
		      @Override
		      public String toString() {
		          return "TargetObject []"+ getClass();
		      }
		  }
		  ```
	- 实现代理方法
		- ```java
		  ```