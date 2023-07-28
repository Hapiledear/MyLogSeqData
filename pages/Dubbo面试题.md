- Dubbo服务请求流程 #card
  card-last-score:: 3
  card-repeats:: 5
  card-next-schedule:: 2023-05-27T19:49:50.745Z
  card-last-interval:: 22.51
  card-ease-factor:: 1.8
  card-last-reviewed:: 2023-05-05T07:49:50.746Z
	- Provider暴露接口，写入注册中心
	- Provider-注册中心 ： 注册中心返回ip列表给provider，如果列表有更新，由注册中心主动推送给provider
	- Consumer向注册中心订阅自己需要的信息，包括 provider、config、routers，并拉取一份本地保存
	- 订阅消息变更时，会收到注册中心的变更推送
	- Consumer使用轮询策略，直接与Provider的机器进行通信
	- Provider与Consumer 在内存中累计调用次数和耗时，定时上报给监控中心
- Dubbo的轮询策略 #card
  card-last-interval:: 22.51
  card-repeats:: 5
  card-ease-factor:: 1.8
  card-next-schedule:: 2023-05-27T19:49:47.587Z
  card-last-reviewed:: 2023-05-05T07:49:47.588Z
  card-last-score:: 3
	- 随机
	- 轮询和加权轮询
	- 一致性hash
	- 最小调用次数
- Dubbo的失败策略 #card
  card-last-score:: 3
  card-repeats:: 5
  card-next-schedule:: 2023-05-27T19:48:51.425Z
  card-last-interval:: 22.51
  card-ease-factor:: 1.8
  card-last-reviewed:: 2023-05-05T07:48:51.425Z
	- 自动重试其它服务器(默认)
	- 立即报错，只发起一次调用
	- 出现异常时，直接忽略
	- 记录失败请求，定时重发
	- 并行-调用多个服务器，只要一个成功即返回
	- 广播-逐个调用所有提供者，任意一个报错则报错
- 为什么采用Dubbo\\RPC 而不是HTTP #card
  card-last-interval:: 22.51
  card-repeats:: 5
  card-ease-factor:: 1.8
  card-next-schedule:: 2023-05-27T19:49:39.659Z
  card-last-reviewed:: 2023-05-05T07:49:39.659Z
  card-last-score:: 3
	- RPC 是基于TCP的自定义协议，请求报文的体积更小
	- RPC可自主决定使用哪种序列化方式，进一步压缩报文体积，减少序列化\\反序列化的消耗
	- RPC自带负载均衡和服务上下线，而传统的HTTP需要修改Nginx
- Dubbo的主要应用场景 #card
	- 作为单纯的 {{cloze RPC}} 使用
	- 微服务化 对应用进行 {{cloze 服务拆分}} 解决 {{cloze 服务依赖关系}} 和 {{cloze 服务扩容}} 问题
- Dubbo如何时效服务治理 #card
	- {{cloze 自动生成服务间的调用链路}} 链路
	- {{cloze 服务访问压力以及时长统计}} 时长
	- {{cloze 服务可用性监控、失败告警监控}} 监控
- Dubbo的工作原理及分层 #card
	- Biz
		- service {{cloze 实现业务代码}}
	- RPC
		- config {{cloze Dubbo相关配置}}
		- proxy {{cloze 透明生成客户端的stub和服务端的skeleton}} {{cloze 调用的是接口，没有实现类。所以得生成代理}}
		- registery {{cloze 服务注册与发现}}
		- cluster层 {{cloze 提供路由及负载均衡}}
	- monitor {{cloze 对调用次数和时长进行监控}}
	- protocol {{cloze 封装RPC调用}}
	- exchange {{cloze 封装请求响应模式，同步转异步}}
	- transport {{cloze 抽象 mina 和 netty 为统一接口}}
	- serialize {{cloze 数据序列化}}
-
-