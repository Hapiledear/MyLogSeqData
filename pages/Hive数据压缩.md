- 压缩的优点
	- 减少占用的存储空间
	- 加快文件传输效率
	- 降低IO次数
- 压缩的缺点
	- 压缩解压本身需要耗时，使用的算法越复杂，时间越长。
- Hive支持的压缩算法，也就是 [[Hadoop压缩算法]]
	- Bzip2
	- Gzip
	- Lzo
	- Lz4
	- Snappy
- 压缩配置
	- 中间传输数据压缩功能 `set hive.exec.compress.intermediate=true`
	- map端输出压缩功能 `set mapreduce.map.output.compress=true`
	- 设置mpa输出数据的压缩方式 `set mapreduce.map.output.compress.codec=org.apache.hadoop.io.compress.SnappyCodec`
	- 开启最终输出数据压缩`set hive.exec.compress.output = true`
	- reduce端输出压缩 `set mapreduce.output.fileoutputformat.compress=true`
	- 设置reduce的压缩方式
	- 设置reduce的压缩为压缩块
-
	-