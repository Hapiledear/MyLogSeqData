- Dubbo调用原理 #card
  card-last-interval:: 0.57
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-08T22:43:36.830Z
  card-last-reviewed:: 2023-02-08T09:43:36.830Z
  card-last-score:: 3
	- Provider暴露接口，写入注册中心
	- Consumer向注册中心询问接口，并拉取一份本地保存
	- Consumer使用轮询策略，使用Provider暴露出的jar包接口，直接与Provider的机器进行通信
- Dubbo的轮询策略 #card
  card-last-interval:: 0.57
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-08T22:43:33.039Z
  card-last-reviewed:: 2023-02-08T09:43:33.039Z
  card-last-score:: 3
	- 随机轮询
	- 轮询和加权轮询
	- 一致性hash
	- 最小调用次数
- Dubbo的失败策略 #card
  card-last-interval:: 0.57
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-08T22:41:08.207Z
  card-last-reviewed:: 2023-02-08T09:41:08.208Z
  card-last-score:: 3
	- 快速失败，直接返回错误
	- 忽略失败
	- 重试一定次数
	- 切换机器重试一定次数
- 为什么采用Dubbo\\RPC 而不是HTTP #card
  card-last-interval:: 0.57
  card-repeats:: 1
  card-ease-factor:: 2.36
  card-next-schedule:: 2023-02-08T22:43:19.566Z
  card-last-reviewed:: 2023-02-08T09:43:19.566Z
  card-last-score:: 3
	- RPC 是基于TCP的自定义协议，请求报文的体积更小
	- RPC可自主决定使用哪种序列化方式，进一步压缩报文体积，减少序列化\\反序列化的消耗
	- RPC自带负载均衡和服务上下线，而传统的HTTP需要修改Nginx