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
	  collapsed:: true
		- ```java
		  import java.lang.reflect.Method;
		   
		  import net.sf.cglib.proxy.MethodInterceptor;
		  import net.sf.cglib.proxy.MethodProxy;
		  /**
		   * 目标对象拦截器，实现MethodInterceptor
		   *
		   */
		  public class TargetInterceptor implements MethodInterceptor{
		   
		      /**
		       * 重写方法拦截在方法前和方法后加入业务
		       * Object obj为目标对象
		       * Method method为目标方法
		       * Object[] params 为参数，
		       * MethodProxy proxy CGlib方法代理对象
		       */
		      @Override
		      public Object intercept(Object obj, Method method, Object[] params,
		              MethodProxy proxy) throws Throwable {
		          System.out.println("调用前");
		          Object result = proxy.invokeSuper(obj, params);
		          System.out.println(" 调用后"+result);
		          return result;
		      }
		  }
		  ```
	- 创建代理类并使用
	  collapsed:: true
		- ```java
		  import net.sf.cglib.proxy.Callback;
		  import net.sf.cglib.proxy.CallbackFilter;
		  import net.sf.cglib.proxy.Enhancer;
		  import net.sf.cglib.proxy.NoOp;
		   
		  public class TestCglib {
		      public static void main(String args[]) {
		        	// 字节码增强器,方便对类进行扩展
		          Enhancer enhancer =new Enhancer();
		        // 设置目标类
		          enhancer.setSuperclass(TargetObject.class);
		        // 设置代理方法的实现
		          enhancer.setCallback(new TargetInterceptor());
		        // 创建代理类
		          TargetObject targetObject2=(TargetObject)enhancer.create();
		        // 使用
		          System.out.println(targetObject2);
		          System.out.println(targetObject2.method1("mmm1"));
		          System.out.println(targetObject2.method2(100));
		          System.out.println(targetObject2.method3(200));
		      }
		  }
		  ```
	- 进阶1