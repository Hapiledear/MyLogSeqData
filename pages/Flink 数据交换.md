- 数据传递模式
	- PULL模式
		- 用于批计算. 上游完全计算完毕之后，下游从上游拉取数据开始下一阶段计算，直到最终所有的阶段都计算完毕，输出结果
	- PUSH模式
		- 用于流式计算.上游主动向下游推送数据，上下游之间采用#生产者-消费者  模式，下游收到数据触发计算，没有数据则进入等待状态
	- ![image.png](../assets/image_1659667153414_0.png)
-