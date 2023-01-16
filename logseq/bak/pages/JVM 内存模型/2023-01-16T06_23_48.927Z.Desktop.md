- ![image.png](../assets/image_1673252384599_0.png)
- [[Java 虚拟机栈]]
- 本地方法栈
	- 执行的是native方法
	- 也可能抛出两种异常
- PC计数器
- [[Java 堆内存]]
- [[Java 方法区]] 1.8改为了 [[Java 元空间]]
- [[Java 直接内存]]
- ## 几种变量存放在哪里？
  id:: 63bd460a-e462-4cab-90c5-e495141648ad
  collapsed:: true
	- ```java
	  public class StaticObjTest {
	      static class Test{
	            // 静态变量&类变量
	            // 一个java.lang.Class类型的对象实例引用了此变量
	            static ObjectHolder staticObj = new ObjectHolder();
	            // 实例变量&成员变量
	            ObjectHolder instanceObj = new ObjectHolder();
	            void foo() {
	                // 局部变量
	                ObjectHolder localObj = new ObjectHolder()();
	                System.out.println("done");
	                }
	          }
	          private static class ObjectHolder{
	          }
	          public static void main(String[] args) {
	            Test test = new StaticObjTest.Test();
	            test.foo();
	      	}
	  }
	  
	  ```
	- 静态变量&类变量
		- `static`所修饰的，定义在方法外的变量
		- 1.8-随着Test类信息存放于 [[Java 方法区]] ，1.8+存放于[[Java 堆内存]]中
	- 实例变量&成员变量
		- 没有`static`修饰的，定义在类中
		- 在[[Java 对象创建过程]] 时，从 ((63bbf5ce-587f-4c0c-8d74-972bba355132)) 取出直接引用或值，随着Test对象实例存放于[[Java 堆内存]]中
	- 局部变量
		- 定义在类的方法中
		- 方法被调用时放入[[Java 虚拟机栈]]的栈帧中，方法结束后从栈中弹出
	- 变量的引用对应的对象实体
		- 存放在[[Java 堆内存]]
- ## 逃逸分析
	- 如果一个对象在一个方法内定义，如果被方法外部的引用所指向，那认为它逃逸了
	- 方法逃逸
		- 当一个对象在方法里面被定义后，它可能被外部方法所引用.| 对象的指针，传递到了 方法之
		  外。
		- ```java
		  //StringBuffer对象发生了方法逃逸
		  public static StringBuffer createStringBuffer(String s1, String s2) {
		      StringBuffer sb = new StringBuffer();
		      sb.append(s1);
		      sb.append(s2);
		      return sb;
		  }
		  
		  ```
		-
	- 线程逃逸
		- 一个对象可能被外部线程访问到
		- 如赋值给**类变量**或可以在其它线程中访问的**实例变量**
	- 由 不逃逸->方法逃逸->线程逃逸 衍生出3种不同程度的优化
		- 栈上分配(不逃逸)
			- 对象不分配在堆上，而是分配在栈内存上
			- 快速地在栈帧上创建和销毁对象，减少GC压力
		- 标量替换(不会被外部访问)
			- 将一个大的对象打散成若干变量
			- 可以选择在栈帧分配，也可以就近在寄存器上分配空间，避免了需要连续空间而导致内存不足的情况
		- 消除同步锁(对象只能从一个线程被访问到)
			- 如果被`synchronized`修饰，会消除synchronized内置锁，但不能消除[[Java 显式锁]] 、[[Java CAS]]乐观锁
	- 逃逸分析底层原理
	-