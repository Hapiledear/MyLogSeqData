- 避免小文件生成
	- MapReduce中每个小文件都会启动一个MapTask，导致资源的浪费。
	- Hive中可以自动判断是否小文件，且自动合并
	  ```SQL
	  -- 将MapTask产生的小文件合并
	  set hive.merge.mapfiles=true;
	  -- 将ReduceTask产生的小文件进行合并
	  set hive.merge.mapredfiles=true;
	  -- 每一个合并的文件的大小
	  set hive.merge.size.pre.task=26000000000;
	  -- 平均每个文件的大小，如果小于这个值就会被合并
	  set hive.merge.smallfiles.avgsize=160000000;
	  ```
- 实际使用中，是在Hive SQL后加 `distribute by rand()` 来控制输出文件的个数，进而控制大小。
-