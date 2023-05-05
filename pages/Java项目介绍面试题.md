- {{cards [[Java项目介绍面试题]] }}
- 介绍一下项目的架构，请求如何进入我们项目的 #card
  card-last-interval:: 22.51
  card-repeats:: 5
  card-ease-factor:: 1.8
  card-next-schedule:: 2023-05-27T19:27:30.091Z
  card-last-reviewed:: 2023-05-05T07:27:30.092Z
  card-last-score:: 3
	- 接入层 {{cloze LVS Linux虚拟服务器}} + {{cloze Nginx}}
	- 服务层 {{cloze Spring 网关}} + {{cloze 本地缓存}} + {{cloze Redis集群}} + {{cloze 数据库 兜底}}
- 从高并发的角度介绍项目 10 #card
  card-last-interval:: 30.37
  card-repeats:: 6
  card-ease-factor:: 1.66
  card-next-schedule:: 2023-06-04T15:32:01.096Z
  card-last-reviewed:: 2023-05-05T07:32:01.097Z
  card-last-score:: 3
	- 拆分为微服务
		- {{cloze 方便重要业务的水平扩展，如基础模块，用户模块}}
	- 分库分表
		- {{cloze MySql单表数据不超过500W,性能最佳}}
	- 读写分离
		- {{cloze 数据库分主从，主库写，从库读}}
	- 链接池化
		- {{cloze 使用的都是框架，所有链接均放在链接池中}}
	- 缓存
		- {{cloze Redis二级缓存+本地三级缓存}}
	- 消息队列削峰
		- {{cloze CPA任务中使用了}}
	- 熔断降级
		- {{cloze 所有一级页面都有兜底数据}}
	- 限流
		- {{cloze Redis分布式限流}}
	- 水平扩容
		- {{cloze 服务自身加机器，依赖的中间件加机器}}
	- 海量数据处理
		- {{cloze ES + HBase}}
- 抽奖模块设计要略 #card
  id:: 63d86c04-4b9e-4b39-adb5-66cc686129ff
  card-last-interval:: 22.51
  card-repeats:: 5
  card-ease-factor:: 1.8
  card-next-schedule:: 2023-05-27T19:50:30.857Z
  card-last-reviewed:: 2023-05-05T07:50:30.857Z
  card-last-score:: 3
	- 步骤拆分
		- 抽奖准入->产生奖品->扣减库存 -> 记录日志
	- 用户的抽奖次数扣减，高并发下如何保证不超扣
		- 分布式锁，key=固定前缀+userId
		- CAS操作，最后扣减
		- 抽奖次数为0的用户缓存，这部分请求不打入数据库
	- 库存扣减，高并发下如何保证不超扣
		- 同一类型的奖品ID设置多个，尽可能的分散请求
		- for update 行锁 ，保证顺序执行
		- 库存为0的记录缓存，这部分请求不打入数据库
	- 保底机制
		- 过程中一旦发生异常，走保底奖品(虚拟、不限量)
		- 大盘监控，奖品库每15分钟播报一次
		- 每日对账
-