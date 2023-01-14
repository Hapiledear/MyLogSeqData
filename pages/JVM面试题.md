- [[Java 元空间]] 会产生内存溢出吗？#card
  card-last-interval:: -1
  card-repeats:: 0
  card-ease-factor:: 2.5
  card-next-schedule:: nil
  card-last-reviewed:: nil
  card-last-score:: nil
	- 元空间的由来。 {{cloze 从java8+开始，用 元空间 替代了 永久代/方法区}}
	- 元空间的内存 {{cloze 并不使用虚拟机的内存，而是直接使用机器内存}}
	- 出现OOM的原因 {{cloze 加载到内存中的class文件数量太多or体积太大}}
	- 解决办法 {{cloze 增加元空间的大小 `-XX:MaxMetaspaceSize=512m`}}
- 说一下 JVM 的主要组成部分及其作用 #card
	- 类加载系统 {{cloze 根据给定的全限定类名，使用双亲委派模型，由类加载器加载class文件到 方法区}}
	- 运行时数据区 JVM内存模型 {{cloze 分为线程公有的堆、方法区，和线程私有的 虚拟机栈、本地方法栈、程序计数器}}
	- 执行引擎 {{cloze 执行字节码指令。分为 即时编译执行和解释执行。此外，**垃圾收集器** 也在其中}}
	- 本地接口 {{cloze 调用本地方法}}
- 说一下JVM内存模型 #card
	- 画一下JVM内存模型图，并描述每个模块的定义，作用，以及可能会存在的问题
	- 虚拟机栈
		- {{cloze 是线程私有的。每个方法被执行时，都在栈中同步创建一个栈帧}} 私有公有?何时创建
		- {{cloze 用于存储 局部变量表(基本数据类型及对象的引用)、操作数栈、动态链接、方法出口等信息}} 5个部分
		- {{cloze 申请不到足够内存时会抛出OOM异常；栈深度超过规定时会抛出StackOverFlowError}} 可能的异常
	- 程序计数器
		- {{cloze 当前线程所执行的字节码的行号}} 存储的内容
		- {{cloze 一个core只能运行一个线程，多线程是通过轮流切换、分配时间的方式来完成的。这就需要一个记录每个线程执行到哪里}} 为什么需要
	- 本地方法栈
		- {{cloze 线程私有的，执行的是native方法}}
	- 堆
		- {{cloze 线程共有，GC管理的内存区域}}
		- {{cloze 主要有 对象实例、字符串常量池、静态变量、和线程分配缓冲区}} 4个部分
	- 方法区
		- {{cloze 存储被虚拟机加载的类信息、常量、静态变量，即时编译后的代码缓存}} 4个部分
		- {{cloze 1.8-叫永久代，1.8+改名为元空间，使用机器内存}} 演变过程
	- 直接内存
		- {{cloze 基于NIO的 DirectByteBuffer 而产生的堆外内存}} NIO
		- {{cloze 通过DirectByteBuffer对象的虚引用实现堆外内存的释放}} 如何回收
- 堆内存的组成 #card
	- {{cloze 1/3的年轻代 1/2的老年代}} 2代
	- {{cloze  年轻代又分 eden区 s1和s2区 比例为 8:1:1}} 3区
	- {{cloze 通常 年轻代采用的GC算法是 复制算法，老年代采用的是标记-整理算法 }} GC算法
- 非堆内存有哪些 #card
	- {{cloze DirectByteBuffer 所申请的直接内存}} NIO
	- {{cloze 方法区的具体实现 元空间}}
- ## 为什么 字符串常量池 #常量池 被移动到了堆中？ #card
	- [[Java 永久代]]在 Full GC/Major GC  时才会触发，条件苛刻
	- 开发中会有大量字符串被创建，回收效率低导致永久代内存不足
	- 放在堆中，可以及时回收内存
- ## 几种变量存放在哪里？ #card
  card-last-interval:: -1
  card-repeats:: 0
  card-ease-factor:: 2.5
  card-last-reviewed:: nil
  card-next-schedule:: nil
  card-last-score:: nil
	- ``` java
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
		- {{cloze 1.8-随着Test类信息存放于 [[Java 方法区]] ，1.8+存放于[[Java 堆内存]]中}}
	- 实例变量&成员变量
		- 没有`static`修饰的，定义在类中
		- {{cloze 在[[Java 对象创建过程]] 时，从 运行时常量池 Runtime Constant pool #常量池 取出直接引用或值，随着Test对象实例存放于[[Java 堆内存]]中}}
	- 局部变量
		- 定义在类的方法中
		- {{cloze 方法被调用时放入[[Java 虚拟机栈]]的栈帧中，方法结束后从栈中弹出}}
	- 变量的引用对应的对象实体
		- {{cloze 存放在[[Java 堆内存]]}}
-
- ## [[Java 堆内存]] 与[[Java 虚拟机栈]] 的区别 #card
	- 物理地址
		- 堆的物理地址分配是不连续的。所以GC时有各种算法
		- 栈的物理地址分配所连续的
	- 内存分别
		- 堆分配的内存在**运行期**确认的，大小不固定，但远大于栈
		- 栈分配的内存大小在**编译器**将确认，大小所固定的
	- 存放的内容
		- 堆存放的所对象实例和数组，更侧重于数据的存储
		- 栈存放了 局部变量、操作数栈、返回结果，更侧重于方法的执行
	- 程序可见度
		- 堆对于整个应用程序都是共享的、可见的
		- 栈是线程私有的
- 对象创建过程 #card
	- 类加载
	- 分配内存
		- 两种方式 {{cloze 指针碰撞，空闲列表}}
	- 分配时的并发处理
		- 两种方式 {{cloze CAS+失败重试,本地线程分配缓冲}}
	- 初始化
	- <init>方法
- 对象如何访问 #card
	- 句柄
		- {{cloze 栈中对象的引用 ->堆中句柄池中的句柄 -> 堆中的实例 和 方法区的类型信息}} 栈-> 堆->堆和方法区
		- {{cloze 由于GC频繁导致对象的地址变更，这样做可以不用修改引用}} 为什么这么做
	- 直接指针
		- {{cloze 栈中的对象引用 -> 堆中的对象实例 -> 方法区中对应的类型信息}} 栈->堆->方法区
		- {{cloze 节省了一次指针定位的时间}} 好处
- JVM中的垃圾回收器有哪些,及其工作原理 #card
	- Serial
		-
	- Parral
	- CMS
	- G1
- ## 内存泄漏问题
	- 理论上来说 [[Java GC]]机制可以保证不再被使用的对象被自动回收，自动从内存中清除
	- 导致内存泄漏的根本原因是：**长生命周期**的对象持有**短生命周期**对象的引用而导致不能被回收
- {{embed ((63be8025-29b0-460e-8867-4258e648347e))}}
- ## 如何排查JVM问题
	- 使用`jmap`查看各区域的使用情况
	- 使用`jstack`查看线程的运行情况，关注阻塞的线程、是否出现了死锁
	- 使用`jstat`查看GC情况，特别注意Full GC
	- 使用`visualvm`分析大内存占用，如 短时间内产生的大量小对象，长期的大对象以及长期得不到回收的对象
-
- xxx