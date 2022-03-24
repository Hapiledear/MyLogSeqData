- 关键类
	- `java.lang.reflect.Proxy` 静态工厂
		- 主要作用是使用 `public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)` 方法,来动态的创建代理类
		- ClassLoader  类加载器,用于加载并初始化涉及到的类
		- interfaces 需要代理的实现了同一接口的类集合
		- InvocationHandler  生成的动态代理的代理function
	- `java.lang.reflect.InvocationHandler` 代理钩子
		- 将代理类与代理方法解耦,使用者只需关心实现代理方法就行.
	- 使用案例
		- 目标类
		  collapsed:: true
			- ```java
			  interface Shopping {
			      void buy();
			  }
			  
			  class Client implements Shopping {
			      public void buy() {
			          System.out.println("我想买这件商品");
			      }
			  }
			  ```
		- 实现代理方法
		  collapsed:: true
			- ```java
			  public class DynamicProxy implements InvocationHandler {
			  
			      private Object target = null;
			  
			      DynamicProxy(Object target) {
			          this.target = target;
			      }
			  
			      /**
			       * 代理方法逻辑
			       *
			       * @param proxy  代理对象
			       * @param method 调度方法
			       * @param args   调度方法参数
			       */
			      @Override
			      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			          System.out.println("代理前");
			          method.invoke(target, args);
			          System.out.println("代理后");
			          return null;
			      }
			  }
			  
			  ```
		- 创建代理类,并使用
		  collapsed:: true
			- ```java
			  public class DyProxyTest {
			      public static void main(String[] args) {
			          Shopping client = new Client();
			          DynamicProxy dyProxy = new DynamicProxy(client);
			          Shopping shop = (Shopping) Proxy.newProxyInstance(Shopping.class.getClassLoader(), new Class[]{Shopping.class}, dyProxy);
			          shop.buy();
			      }
			  }
			  
			  ```
-
- 主要是针对接口做代理
- 针对类做代理,可以用[[CgLib]]
-