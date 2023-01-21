- {{cards [[MySql面试题]] }}
- 如无特殊说明，题中出现的存储引擎均为 [[InnoDb]]
- SQL查询过程 #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-24T00:44:43.921Z
  card-last-reviewed:: 2023-01-20T00:44:43.922Z
  card-last-score:: 3
	- 建立连接 {{cloze 连接器}}
	- 执行计划の缓存 {{cloze 查询缓存}}
	- SQL分析 {{cloze 分析器}}
	- SQL优化 {{cloze 优化器}} 生成 执行计划
	- 委托给存储引擎 {{cloze 执行器}}
- 查询语句的各部分执行顺序 9 #card
  card-last-interval:: 3.46
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-22T20:39:05.942Z
  card-last-reviewed:: 2023-01-19T09:39:05.943Z
  card-last-score:: 3
	- {{cloze from -> join t2 on xxx  -> where yyy -> group by aaa -> having -> bbb -> select aaa,bbb,ccc -> distinct aaa ->order by bbb -> limit m,n}}
- 存储引擎InnoDb和MyISAM的区别 #card
  card-last-interval:: 3.22
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-22T14:41:24.338Z
  card-last-reviewed:: 2023-01-19T09:41:24.338Z
  card-last-score:: 3
	- MyISAM
		- {{cloze 读快更新慢，因为上了全表锁}}
		- {{cloze 不支持事物，不支持行锁和外键}}
	- InnoDb
		- {{cloze 支持事物，行锁和外键约束}}
- 事物的四大特性，分别是如何解决的 #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-23T09:35:33.884Z
  card-last-reviewed:: 2023-01-19T09:35:33.885Z
  card-last-score:: 3
	- 原子性
		- {{cloze 在事物中的一组操作，要么全部成功提交，要么全部失败回滚}}
		- {{cloze Undo Log}}
	- 一致性
		- {{cloze 事物在执行前后其状态一致,A+B=100 无论A B怎么转账，最终都是100}}
		- {{cloze Undo Log}}
	- 持久性
		- {{cloze 事物一旦提交，发生的改变就是永久的}}
		- {{cloze Redo Log}}
	- 隔离性
		- 多个事物同时进行时，会出现3种问题.为解决这三个问题，进而提出了4种隔离级别
			- 3个问题 {{cloze 脏读}} {{cloze 不可重复读}} {{cloze 幻读}}
			- 4种隔离级别 {{cloze 读未提交}} {{cloze 读已提交}} {{cloze 可重复读读}} {{cloze 串行化}}
		- {{cloze 锁机制 和 MVVC机制}}
- Undo Log 和Redo Log #card
  card-last-interval:: 3.11
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-22T11:42:25.997Z
  card-last-reviewed:: 2023-01-19T09:42:25.997Z
  card-last-score:: 3
	- Undo Log
		- 存储的是 {{cloze 旧数据的版本链集合}}
		- 原子性的体现 {{cloze 如果出错，可以用它来回滚数据}}
	- Redo Log
		- 存储的是 {{cloze 最新版本的数据}}
		- 持久性的体现 {{cloze 如果系统崩溃，可以用它来进行恢复}}
- 描述一下事物产生的三个问题 #card
  card-last-interval:: 3.86
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-23T05:37:36.741Z
  card-last-reviewed:: 2023-01-19T09:37:36.741Z
  card-last-score:: 3
	- 脏读
		- {{cloze 事物A读取到了事物B 未提交的数据}}
	- 不可重复读
		- {{cloze 事物A在读取数据时，两次读取的**数据结果**不一致}}
	- 幻读
		- 范围查询 {{cloze 事物A在进行范围查询时，两次读取的**记录数**不一致}}
- 事物的4种隔离级别 #card
	- 读未提交
		- {{cloze 允许读取到还没有提交的数据}}
	- 读已提交
		- {{cloze 在事物A中只可以 读到事物B已经提交的数据}}
	- 可重复读
		- {{cloze 事物A中，多次读取同一个数据时，其值都和开始时一致}}
		- 是否存在幻读？ {{cloze 快照读时(非上锁语句)不存在幻读。由于MVCC + Read View}}
	- 串行化
		- {{cloze 所有事物被串行执行}}
- 什么是幻读？如何防止？ #card
  card-last-interval:: 3.86
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-23T05:37:06.603Z
  card-last-reviewed:: 2023-01-19T09:37:06.604Z
  card-last-score:: 3
	- 范围查询 {{cloze 事物A的两次查询之间，被事物B插入or删除了记录，产生结果集的不一致}}
	- 防止方式 {{cloze 间隙锁}} + {{cloze 行锁}} 的组合方式
		- 记录锁 & 行锁
			- {{cloze 锁住索引记录}}
			- 上锁时机
				- {{cloze select ... for update}}
				- {{cloze 通过 主键索引与唯一索引 对数据进行update操作}}
		- 间隙所
			- {{cloze 锁住索引记录之间的间隙}}
		- 临键锁 next-key lock
			- {{cloze 加在某条记录以及这条记录前面的间隙(左开右闭) 上锁}}
			- {{cloze 只与 非唯一索引列 有关}}
			-
- 读取数据的两种方式 #card
	- mvcc {{cloze 快照读}}
		- 简单的select操作
		- 如何解决幻读 {{cloze MVCC + Read View}}
	- 最新记录 {{cloze 当前读}}
		- 加锁的select操作 {{cloze lock in share mode}} {{cloze for update}}
		- 插入、更新、删除操作
		- 如何解决幻读 {{cloze next-key Lock 行锁与间隙锁的组合}}
- 什么是MVCC #card
	- 多版本并发控制
	- 只工作在两种事物隔离级别下
		- {{cloze 读已提交}} {{cloze 可重复读}}
	- 具体实现依靠 {{cloze undo log}} 形成 {{cloze 读视图}} 机制 关键词 copyOnWrite 版本号-> 版本链
		- Undo Log 存储旧的数据，并构成版本链
		- Read View 读视图 用以判断事务的可见性
			- {{cloze 当前事务ID}} {{cloze 未提交的事务列表}} {{cloze 最小事务ID和最大事务ID}}
		- 读已提交的实现
			- {{cloze 每次读之前都生成一个Read View}}
		- 可重复读的实现
			- {{cloze 只在第一次执行查询语句时生成 Read View}
- MySql中的锁有哪些 #card
	- 表锁
		- 意向锁
			- 作用 {{cloze 让表锁和行锁的共存更高效}}
			- 分类 {{cloze 排它型、共享型}}
			- 举例 for update 锁住一行后，事务b要想获得该表t1的表锁，必须检查2项
				- {{cloze 没有其它事务持有t1的排它锁}}
				-
	- 行锁 or 记录锁
		- 作用 {{cloze 锁住该条记录，防止其它事务的插入、更新、删除}}
		- 限制条件 {{cloze 必须要有 唯一索引 和 主键索引做条件 (查出一条记录) 否则退化成 临界锁}}
		- 共享型 读
			- 手动加s锁 {{cloze select ... lock in share mode}}
		- 排它型 写
			- 自动加x锁 {{cloze update\delete\insert 自动加x锁}}
			- 手动加x锁 {{cloze select ... for update }}
	- 间隙锁
		- 作用 {{cloze 在 RR 级别下解决 直接读的 幻读 问题}} {{cloze 锁住 一段 开区间}}
		- 限制条件 {{cloze 非唯一索引}}
	- next-key lock 临界锁
		- 限制条件 {{cloze 非唯一索引}}
		- 作用 {{cloze 锁住一段 左开右闭的 区间}} 相当于 行锁+间隙锁
- InnoDb与MyISAM的比对
- 元数据和基本数据类型及其使用
	- 日期类型
- 权限控制及安全性问题
- 索引和锁
	- 间隙锁
	- 快照读和当前读
- 整体的优化方案
- 事务
- 视图和存储过程
- Sql执行流程、执行顺序
- 大数据量的插入&删除&查询
- 分库分表
- MRR
- Explain关键字