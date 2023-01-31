- {{cards [[Java项目介绍面试题]] }}
- 介绍一下项目的架构，请求如何进入我们项目的 #card
  card-last-interval:: 3
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-30T12:27:36.841Z
  card-last-reviewed:: 2023-01-27T12:27:36.841Z
  card-last-score:: 3
	- 接入层 {{cloze LVS Linux虚拟服务器}} + {{cloze Nginx}}
	- 服务层 {{cloze Spring 网关}} + {{cloze 本地缓存}} + {{cloze Redis集群}} + {{cloze 数据库 兜底}}
- 从高并发的角度介绍项目 10 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-04T02:44:46.646Z
  card-last-reviewed:: 2023-01-31T02:44:46.647Z
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
-