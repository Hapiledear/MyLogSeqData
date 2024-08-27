- 在查询计划中，通常把小表(所占页数较少的)放在左侧，称为*驱动表*。
- Join算子的输出
	- R表中的记录r与S表中的记录s，满足匹配条件，r&s组成一条新记录，返回给上级算子。
	- 影响输出的三个因素
		- SQL处理模型
		- 存储模型
		- 整个SQL所需的数据
	- 输出的内容
		- R和S的全tuple
		- R和S的记录id
- Join操作的开销计算
	- 主要是计算磁盘的IO开销。
	- 假设R表的大小为M页，m条数据; S表大小为N页，n条数据。
- Nested Loop Join 嵌套循环join
  id:: 66cd6e0e-e915-48c9-a5d0-58c9a8bcfc7a
	- ![image.png](../assets/image_1724739289356_0.png){:height 241, :width 326}
	- $$ cost =M+(m*N)$$
	- 优化一、基于页的嵌套循环join Block Nested Loop Join
		- 1个page放R表数据 B_r ,1个page放S表数据B_s
		- ![image.png](../assets/image_1724739435884_0.png){:height 279, :width 449}
		- $$cost=M+(M*N)$$
		- 更进一步，如果缓存区很大，可以拿出B-2个page给outer table，1个page给inner table，1个page用于记录输出结果
		- ![image.png](../assets/image_1724739546429_0.png)
		- $$cost=M+(\left \lceil M/(B-1) \right \rceil * N)$$
	- 嵌套循环join慢的原因：对于outer table中的每一行数据，都需要遍历inner table。可以使用inner table的index来加速寻找匹配项
		- index可以使用现成的索引
		- 也可以临时建立一个(Hash表,B+树)，临时用
	- 优化二：基于索引的嵌套循环join Index Nested Loop Join
		- ![image.png](../assets/image_1724739862669_0.png)
		- $$cost=M+(m*C)$$ C为index上查找每个tuple的均值
- Sort Merge Join
	- 阶段一：Sort
		- 以join key为基准，对两张表数据进行排序，可以用外排序。
	- 阶段二：Merge
		- 使用两个指针指向排序好的两张表，然后进行匹配。
		- 根据join类型的不同，inner table的指针可能需要回退。
	- ![image.png](../assets/image_1724740205277_0.png)
	- $$\begin{align}
	  SortRCost & = 2M*(1+\left \lceil log_{B-1}\left \lceil M/B \right \rceil  \right \rceil )
	  \newline
	  SortSCost & = 2N(1+\left \lceil log_{B-1}\left \lceil N/B \right \rceil  \right \rceil )
	  \newline
	  Merge Cost & = (M+N)
	  \end{align}$$
	- 在最糟糕的情况下,join的两列所有自动值都相等，指针回溯，退化成原始的 ((66cd6e0e-e915-48c9-a5d0-58c9a8bcfc7a))
- Hash Join
	- 阶段一：Build 索引构建
		- 扫描outer table中的数据，使用`h1(joinKey)`，构建Hash Table
	- 阶段二：Probe 点查询
		- 对inner table中的每条数据，以join key为参数，使用`h1(joinKey)`,定位Hash Table进行查询。如果找到能够匹配的KV，就可以完成join操作
	- ![image.png](../assets/image_1724740911861_0.png)
	- Hash Table存储细节
		- Key=join条件中的字段
		- Value=全量数据full tuple or tuple ID
	- 优化一：布隆过滤器
		- 构建Hash Table的同时，构建一个Bloom Filter。用于过滤无效查询(库中没有相应值)
	- 优化二：Grace Hash Join
		- 当内存容量不足以存下整张HashTable时，只能将部分数据驱逐到磁盘
		- build阶段：对2章表使用`h1(joinKey)` 分别构建两张HashTable
		- Probe阶段：将tuple所在的两个Hash桶的数据