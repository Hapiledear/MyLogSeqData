- 小文件产生的原因
	- 本质上 `文件数量=ReduceTask数量*分区数`,如果没有Reduce阶段,则 `文件数量=MapTask数量*分区数`
	- 具体的原因则可能是如下几种
		- 每使用一次inset into 语句,就会产生一个文件 `insert into table A values (1,'zhangsan',88),(2,'lisi',61);`
		- 通过load方式导入别的文件\文件夹下的数据,数量会保持不变 `load data local inpath '/export/score' overwrite into table A `
		- 通过 insert select 语句,有多少个ReduceTask则会产生多少个文件
			- ```sql
			  insert overwrite table A  
			  select s_id,c_name,s_score from B;
			  ```
		-
	-
- 小文件过多的影响
	- 会导致 [[NameNode]] 元数据特别大, 占用太多内存，严重影响HDFS的性能
	- [[MapReduce]]
	- 每一个小文件都会启动一个map任务,而一个Map任务启动和初始化的时间远远大于逻辑处理的时间，就会造成很大的资源浪费。而且，同时可执行的Map数量是受限的。
- 怎么解决小文件过多
	- 减少Reduce数量 最常用!!
		- ```sql
		  #reduce 的个数决定了输出的文件的个数，所以可以调整reduce的个数控制hive表的文件数量，
		  #hive中的分区函数 distribute by 正好是控制MR中partition分区的，
		  #然后通过设置reduce的数量，结合分区函数让数据均衡的进入每个reduce即可。
		  
		  #设置reduce的数量有两种方式，第一种是直接设置reduce个数
		  set mapreduce.job.reduces=10;
		  
		  #第二种是设置每个reduce的大小，Hive会根据数据总大小猜测确定一个reduce个数
		  set hive.exec.reducers.bytes.per.reducer=5120000000; -- 默认是1G，设置为5G
		  
		  #执行以下语句，将数据均衡的分配到reduce中
		  set mapreduce.job.reduces=10;
		  insert overwrite table A partition(dt)
		  select * from B
		  distribute by floor (rand()*5);
		  
		  解释：如设置reduce数量为10，则使用 rand()， 随机生成一个数 x % 10 ，
		  这样数据就会随机进入 reduce 中，防止出现有的文件过大或过小
		  ```
	- 减少Map数量
	  collapsed:: true
		- ```sql
		  
		  #执行Map前进行小文件合并
		  #CombineHiveInputFormat底层是 Hadoop的 CombineFileInputFormat 方法
		  #此方法是在mapper中将多个文件合成一个split作为输入
		  set hive.input.format=org.apache.hadoop.hive.ql.io.CombineHiveInputFormat; -- 默认
		  
		  #每个Map最大输入大小(这个值决定了合并后文件的数量)
		  set mapred.max.split.size=256000000;   -- 256M
		  
		  #一个节点上split的至少的大小(这个值决定了多个DataNode上的文件是否需要合并)
		  set mapred.min.split.size.per.node=100000000;  -- 100M
		  
		  #一个交换机下split的至少的大小(这个值决定了多个交换机上的文件是否需要合并)
		  set mapred.min.split.size.per.rack=100000000;  -- 100M
		  
		  #设置map端输出进行合并，默认为true
		  set hive.merge.mapfiles = true;
		  
		  #设置reduce端输出进行合并，默认为false
		  set hive.merge.mapredfiles = true;
		  
		  #设置合并文件的大小
		  set hive.merge.size.per.task = 256*1000*1000;   -- 256M
		  
		  #当输出文件的平均大小小于该值时，启动一个独立的MapReduce任务进行文件merge
		  set hive.merge.smallfiles.avgsize=16000000;   -- 16M
		  
		  # hive的查询结果输出是否进行压缩
		  set hive.exec.compress.output=true;
		  
		  # MapReduce Job的结果输出是否使用压缩
		  set mapreduce.output.fileoutputformat.compress=true;
		  ```
	- 使用concatenate 命令 自动合并小文件
	  collapsed:: true
		- ```sql
		  alter table B partition(day=20201224) concatenate;
		  ```
		- 只适用于RCFILE 和 ORC 文件类型
	-