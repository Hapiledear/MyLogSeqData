- {{cards [[MQ面试题]] }}
- 项目中是如何使用MQ的？5  #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-28T02:35:28.590Z
  card-last-reviewed:: 2023-01-24T02:35:28.591Z
  card-last-score:: 3
	- {{cloze 异步处理}} 注销用户
	- {{cloze 应用解耦}} 用户成长值加分
	- {{cloze 流量削峰}} cps任务
	- {{cloze 日志处理}} op_log
	- {{cloze 消息通讯}} 数仓数据写入mq,业务读取
- 消息队列有什么优缺点 #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-28T02:42:31.921Z
  card-last-reviewed:: 2023-01-24T02:42:31.921Z
  card-last-score:: 3
	- 优点3 {{cloze 解耦、异步、削峰}}
	- 缺点3
		- {{cloze 降低系统可用性}} MQ本身不能挂
		- {{cloze 系统复杂度提高}} 幂等性、可靠性
		- {{cloze 一致性问题}}  BCD三个系统都消费同一条MQ BD成功而C失败
- Rabbit MQ 如何保证消息下顺序性? #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-28T02:36:12.044Z
  card-last-reviewed:: 2023-01-24T02:36:12.044Z
  card-last-score:: 3
	- 拆分多个queue,每个queue对应一个consumer。
- Rabbit MQ 消息基于什么传输? #card
	- 建立TCP连接后，基于信道channel的方式传输数据
	- channel是建立在TCP连接上的虚拟连接，没有数量限制。达到复用TCP连接的目的
- Rabbit MQ  如何保证不重复消费? 幂等性  #card
	- 缓存 {{cloze 使用缓存记录唯一ID，设置一个过期时间}}
	- 数据库 {{cloze 使用一张表记录已消费的数据，发生主键冲突则证明已消费}}
- Rabbit MQ  如何保证消息的不丢失？ #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-28T02:38:12.362Z
  card-last-reviewed:: 2023-01-24T02:38:12.362Z
  card-last-score:: 3
	- 发送方确认
		- {{cloze 消息会生成一个唯一ID}} id
		- {{cloze 当消息被队列接收且持久化后，返回这个唯一ID 给生产者}} 成功
		- {{cloze 如果发生错误没有接收到，返回nack消息}} 失败
		- 整个过程都是异步的
	- 消息队列持久化
		- 队列自身有持久化功能，但会影响性能。
	- 消费方确认
		- 手动确认机制 {{cloze 自己在代码里处理完业务逻辑之后，手动确认}}
- Rabbit MQ 如何保证高可用 #card
  card-last-interval:: 4
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-28T02:37:48.478Z
  card-last-reviewed:: 2023-01-24T02:37:48.479Z
  card-last-score:: 3
	- 镜像集群模式
		- {{cloze 每个mq节点都有一个一模一样的queue}}
		- {{cloze 数据发送时，会自动同步到所有机器上}}
		- 缺点 {{cloze 同步开销大，占用网络带宽}}
- 消息队列满了怎么处理？几百万消息积压几个小时。 #card
  card-last-interval:: 3.34
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-01-27T10:46:38.162Z
  card-last-reviewed:: 2023-01-24T02:46:38.162Z
  card-last-score:: 3
	- 消费者. {{cloze 快速定位问题，修复consumer}} {{cloze 停止线上的consumer消费}}
	- 机器扩容 {{cloze 上线一个分发consumer，分发到新建立的topic，partition是原来的10倍}} {{cloze 消费consumer上线，也是原来的10倍}}
	- 历史数据补回 {{cloze 找寻冗余字段 或 日志信息，尽可能的补回那些因本次事故过期\未正常处理的数据}}
- 项目中如何使用MQ 架构是怎样的
- MQ有哪些常见问题，是如何解决的
- Rabbit MQ 的工作原理 如何保证 顺序性，幂等性
- Kafka的基本组件 如何保证顺序性，幂等性