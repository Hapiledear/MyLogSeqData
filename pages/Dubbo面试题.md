- {{cards Dubbo面试题}}
- Dubbo服务注册和请求流程 #card
  card-last-score:: 3
  card-repeats:: 6
  card-next-schedule:: 2023-08-30T10:38:32.792Z
  card-last-interval:: 30.37
  card-ease-factor:: 1.66
  card-last-reviewed:: 2023-07-31T02:38:32.792Z
	- Provider暴露接口，写入注册中心
	- Provider-注册中心 ： 注册中心返回ip列表给provider，如果列表有更新，由注册中心主动推送给provider
	- Consumer向注册中心订阅自己需要的信息，包括 provider、config、routers，并拉取一份本地保存
	- 订阅消息变更时，会收到注册中心的变更推送
	- Consumer使用轮询策略，直接与Provider的机器进行通信
	- Provider与Consumer 在内存中累计调用次数和耗时，定时上报给监控中心
- Dubbo的轮询策略 #card
  card-last-interval:: 30.37
  card-repeats:: 6
  card-ease-factor:: 1.66
  card-next-schedule:: 2023-08-30T10:37:38.766Z
  card-last-reviewed:: 2023-07-31T02:37:38.766Z
  card-last-score:: 3
	- 随机
	- 轮询和加权轮询
	- 一致性hash
	- 最小调用次数
- Dubbo的失败策略 #card
  card-last-score:: 3
  card-repeats:: 6
  card-next-schedule:: 2023-08-30T10:34:27.558Z
  card-last-interval:: 30.37
  card-ease-factor:: 1.66
  card-last-reviewed:: 2023-07-31T02:34:27.559Z
	- 故障转移：自动重试其它服务器(默认)
		- 保证了服务的 {{cloze 高可用}}
		- 需要设置合理的 {{cloze 请求有效时间}} 及 {{cloze 重试次数}} 。需要服务具有 {{cloze 幂等性}}
	- 快速失败：立即报错，只发起一次调用
		- 非 {{cloze 幂等服务}}，如 支付接口
	- 安全失败：出现异常时，直接忽略
		- {{cloze 旁路逻辑}} 后续处理不会依赖其返回值，或者它的返回值是什么都不会影响后续处理的结果
	- 故障恢复：记录失败请求，异步重试
		- {{cloze 幂等性服务}} {{cloze 实时性要求不高}} 或 {{cloze 不需要返回值的旁路逻辑}}
	- 并行调用：调用多个服务器，只要一个成功即返回
		- 快速获取返回值且成功率高
		- 消耗额外资源
	- 广播调用：逐个调用所有提供者，任意一个报错则报错
		- 批量操作，如刷新缓存
		- 消耗额外资源 且 失败概率高
	- 并不提供 沉默失败 策略：即 服务熔断
- 为什么采用Dubbo\\RPC 而不是HTTP #card
  card-last-interval:: 30.37
  card-repeats:: 6
  card-ease-factor:: 1.66
  card-next-schedule:: 2023-08-30T10:36:41.785Z
  card-last-reviewed:: 2023-07-31T02:36:41.785Z
  card-last-score:: 3
	- RPC 是属于TCP层的协议，相比于HTTP的应用层协议，请求报文的体积更小
	- RPC可自主决定使用哪种序列化方式，进一步压缩报文体积，减少序列化\\反序列化的消耗
	- RPC框架通常自带负载均衡和服务上下线，而传统的HTTP需要修改Nginx
	- 但是Dubbo自身并没有 熔断器、网关、服务跟踪等其他微服务必要组件
- Dubbo的主要应用场景 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:53:06.851Z
  card-last-reviewed:: 2023-08-16T08:53:06.851Z
  card-last-score:: 3
	- 作为单纯的 {{cloze RPC}} 使用
	- 微服务化 对应用进行 {{cloze 服务拆分}} 解决 {{cloze 服务依赖关系}} 和 {{cloze 服务扩容}} 问题
- Dubbo如何实现服务治理 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:54:56.042Z
  card-last-reviewed:: 2023-08-16T08:54:56.043Z
  card-last-score:: 3
	- {{cloze 自动生成服务间的调用链路}} 链路，需要借助其他框架如 {{cloze SkyWalking}}
	- {{cloze 服务访问压力以及时长统计}} 时长
	- {{cloze 服务可用性监控、失败告警监控}} 监控
- Dubbo的工作原理及分层 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:51:20.646Z
  card-last-reviewed:: 2023-08-16T08:51:20.647Z
  card-last-score:: 3
	- Biz 业务逻辑 自己来提供 {{cloze 接口和实现还有一些配置信息}}
		- service {{cloze 实现业务代码}}
		- config {{cloze Dubbo相关配置}}
	- RPC 封装整个RPC的 {{cloze 调用过程、负载均衡、集群容错、代理}}
		- proxy {{cloze 透明生成客户端的stub和服务端的skeleton}} {{cloze 调用的是接口，没有实现类。所以得生成代理}}
		- registery {{cloze 服务注册与发现}}
		- cluster {{cloze 提供路由及负载均衡}}
		- monitor {{cloze 对调用次数和时长进行监控}}
		- protocol {{cloze 封装RPC调用}}
	- remoting 对 网络传输协议和数据转换的封装
		- exchange {{cloze 封装请求响应模式，同步转异步}}
		- transport {{cloze 抽象 mina 和 netty 为统一接口}}
		- serialize {{cloze 数据序列化}}
- 说说常见的限流算法 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:53:39.930Z
  card-last-reviewed:: 2023-08-16T08:53:39.930Z
  card-last-score:: 3
	- 固定窗口/计数器
		- 计算一个周期T内的访问次数
	- 滑动窗口
		- 将一个大周期T分为若干个小周期N,分别记录每个小周期内的访问次数；并根据时间滑动删除过期的小周期。
		- 当滑动窗口的格子划分的越多，那么滑动窗口的滚动就越平滑，限流的统计就会越精确
	- 漏桶
		- 访问请求到达时直接放入漏桶，漏桶以固定的速率进行释放访问请求（即请求通过），直到漏桶为空
		- 流入速率任意，但流出速率固定
	- 令牌桶
		- 程序以r（r=时间周期/限流值）的速度向令牌桶中增加令牌，直到令牌桶满
		- 请求到达时向令牌桶请求令牌，如获取到令牌则通过请求，否则触发限流策略
		- 支持突发流量
- Dubbo中用到了哪些设计模式 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:55:14.132Z
  card-last-reviewed:: 2023-08-16T08:55:14.132Z
  card-last-score:: 3
	- Filter 责任链模式
	- RegisteryService 观察者模式
	- xxxWrapper 修饰器模式
	- xxxFactory 工厂模式
	- 适配器模式
	- 代理模式
- Dubbo的服务暴露流程 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:52:31.426Z
  card-last-reviewed:: 2023-08-16T08:52:31.426Z
  card-last-score:: 3
	- serviceConfig -> invoker -> expoder -> server -> 注册中心
	- 解析 {{cloze dubbo标签}} ,通过触发ContextRefreshEvent事件的回调方法开始暴露服务的动作
	- 使用 {{cloze 代理模式}} ，把服务暴露接口封装成 {{cloze invoker}} 对象，在该对象里包含需要执行的方法名、参数和对应的URL地址。
	- 通过 {{cloze DubboProtocol}} 的实现类，把包装后的 {{cloze invoker}} 转换成 {{cloze exporter}} 对象。随后启动服务器端的server来监听端口，等待服务调用的到来
	- 通过 {{cloze RegistryProtocol}} 对象，保存URL地址和 {{cloze invoker}} 之间的映射关系，同时把这层映射关系注册到服务中心
- Dubbo的服务引用流程 #card
  card-last-interval:: 8.04
  card-repeats:: 3
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-24T08:54:25.982Z
  card-last-reviewed:: 2023-08-16T08:54:25.982Z
  card-last-score:: 3
	- 注册中心拉取配置 -> 开启client -> 创建invoker -> 创建代理服务
	- Dubbo客户端根据config文件里的信息从 {{cloze 注册中心}} 里订阅服务，并 {{cloze 缓存到本地}} ，后续的服务相关信息的会动态更新到本地。
	- 根据provider的地址和接口连接到 {{cloze 服务端server}} ，开启 {{cloze 客户端client}} ，再 {{cloze 创建invoker}} 。
	- 用 {{cloze invoker}} 为服务接口生成代理对象，这个代理对象是用来远程调用。
- 设计一个RPC框架要考虑什么 #card
  card-last-interval:: 9.49
  card-repeats:: 4
  card-ease-factor:: 2.08
  card-next-schedule:: 2023-08-25T19:56:14.786Z
  card-last-reviewed:: 2023-08-16T08:56:14.786Z
  card-last-score:: 3
	- {{cloze 注册中心}} 让消费者知道有哪些服务，提供者知道自己提供了什么服务
	- {{cloze 负载均衡}} 一个接口多台实例，client该调用哪一个
	- {{cloze 容错机制}} 发生异常后该如何处理
	- {{cloze 通信协议 和 序列化协议}} p-c之间如何交互，参数怎么传递
	- {{cloze 监控、配置、日志}} 附加功能
	-
-