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
- 怎么解决小文件过多