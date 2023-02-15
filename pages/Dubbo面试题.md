- Dubbo调用原理 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-19T06:53:33.977Z
  card-last-reviewed:: 2023-02-15T06:53:33.978Z
  card-last-score:: 3
	- Provider暴露接口，写入注册中心
	- Consumer向注册中心询问接口，并拉取一份本地保存
	- Consumer使用轮询策略，使用Provider暴露出的jar包接口，直接与Provider的机器进行通信
- Dubbo的轮询策略 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-19T06:53:31.133Z
  card-last-reviewed:: 2023-02-15T06:53:31.134Z
  card-last-score:: 3
	- 随机轮询
	- 轮询和加权轮询
	- 一致性hash
	- 最小调用次数
- Dubbo的失败策略 #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-19T06:52:33.659Z
  card-last-reviewed:: 2023-02-15T06:52:33.660Z
  card-last-score:: 3
	- 快速失败，直接返回错误
	- 忽略失败
	- 重试一定次数
	- 切换机器重试一定次数
- 为什么采用Dubbo\\RPC 而不是HTTP #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.22
  card-next-schedule:: 2023-02-19T06:53:18.623Z
  card-last-reviewed:: 2023-02-15T06:53:18.624Z
  card-last-score:: 3
	- RPC 是基于TCP的自定义协议，请求报文的体积更小
	- RPC可自主决定使用哪种序列化方式，进一步压缩报文体积，减少序列化\\反序列化的消耗
	- RPC自带负载均衡和服务上下线，而传统的HTTP需要修改Nginx