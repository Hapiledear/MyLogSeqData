- ![image.png](../assets/image_1673398301920_0.png)
- [[Java 类加载机制]]
	- 执行`<clinit>`方法，即被static修饰的属性和代码块
- 分配内存
	- 指针碰撞
		- 如果[[Java 堆内存]]是规整的。分配时将位于中间的指针指示器向空闲的内存移动一段与大小相等的距离
	- 空闲列表
		- 如果[[Java 堆内存]]是不规整的，则需要JVM维护一个列表用来记录哪些内存可用。在分配时从列表中查询出足够大的内存分配给对象，并在分配后更新列表记录。
	- 内存是否规整的解释
		- 规整的内存即经过GC整理的，所有用过的内存放在一边，而空闲的放在另一边。这与 [[Java GC]]算法和GC时间相关.
- 分配内存时的并发处理
	- CAS + 失败重试
	- 本地线程分配缓冲 Thread Local Allocation Buffer
		- 每个线程在Java堆中预先分配一小块内存，先用自己的，用完再申请，此时才需要同步锁。
- 初始化
- `<init>`方法，包括构造函数、一般代码块和一般属性
- ## 对象如何访问
	- 句柄访问
		- 局部变量存放在[[Java 虚拟机栈]]中的局部变量表，指向[[Java 堆内存]]中 ((63be0f36-eaf1-4f67-84b9-d643b432a521))
		- 好处是有稳定的地址，在 [[Java GC]]时会移动对象，改变频繁
	- 直接指针
		- 局部变量存放在[[Java 虚拟机栈]]中的局部变量表，指向[[Java 堆内存]]中对象池的对象
		- 对象池的对象再有一个指针指向对应类的实例
		- 好处是数据更快，节省了一次指针定位的开销
- ## 对象的引用类型
  id:: 63be09ad-e4d7-4923-825b-d87a62faa166
	- 强引用
		- 使用new关键字创建的对象
		- 即使在内存不足的情况下，JVM宁愿抛出 [[OOM 内存溢出]] 也不会回收这种对象
	- 软引用
		- `SoftReference<String> softRef=new SoftReference<>(str);`
		- 内存空间足够则不回收，不足时(内存将要溢出 or GC空间不足)才回收
		- 在内存足够的情况下进行缓存，提升速度，内存不足时JVM自动回收
			- ```java
			    A a = new A();
			    SoftReference<A> sr = new SoftReference<A>(a);
			    a = null;
			    if(sr!=null){
			    	a = sr.get();
			    }else{
			      a = new A();
			      sr = new SoftReference<A>(a);
			    }
			  ```
	- 弱引用
		- `WeakReference<Car> weakCar = new WeakReference,<Car>(Car);`
		- 每次GC时都会回收
		- a. [[Java ThreadLocalMap]]防止内存泄漏 
		  b. 监控对象是否将要被回收
	- 虚引用
		- 必须和`ReferenceQueue` 引用队列联合使用
			- GC收集器准备回收该对象->放入相应的引用队列
			- 程序监测到引用队列中有元素了，在释放之前采取一些行动
		- 任何时候都可能被垃圾回收
		- [[Java NIO]] 中的`ByteBuffer`对象，靠此机制来释放堆外内存
	-