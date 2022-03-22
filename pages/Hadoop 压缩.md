- 在使用[[压缩]]方面,Hadoop主要考虑的是压缩速度和压缩文件的可分割性.
- Hadoop支持的压缩格式
	- DEFLATE
	- gzip
	- bzip
	- Snappy
- 编码\解码器
	- `CompressionCodec`接口
		- 使用 [[抽象工厂]] 模式,用于创建一系列相关或相互依赖的对象.
		- `createOutputStream()` 创建对应压缩算法的**压缩流**
		- `createCompressor()` 创建对应的**压缩器**
		- `createDecompressor()` 创建对应的解压缩器
		- `createInputStream()` 创建对应的解压缩流
		- `getDefaultExtension()` 获取文件的扩展名
	- `CompressionCodecFactory` 类
		- 通过文件路扩展获取相应的处理类
			- 使用一个有序Map保存处理类 codecs
				- key是后缀的翻转,value是处理它的类
				- ```
				  {
				      2zb. : org.apache.hadoop.io.compress.BZip2Codec,
				      etalfed. : org.apache.hadoop.io.compress.DeflateCodec,
				      yppans. : org.apache.hadoop.io.compres.SnappyCodec,
				      yzg. : my.self.Codec,
				      zg. : org.apache.hadoop.io.compress.GzipCodec
				  }
				  ```
			- ` getCodec(Path file)`方法,获取具体的处理类
				- 翻转fileName
				- 使用 headMap() 获取到离后缀最接近的map集合
				  collapsed:: true
					- 假如翻转后的文件名是`2zb.txt.EMDAER` 则返回结果是
					  collapsed:: true
						- ```
						  {
						  	2zb. : org.apache.hadoop.io.compress.BZip2Codec
						  }
						  ```
					- 假如翻转后的文件名是 `zg.txt.EMDAER` 则返回的结果是
					  collapsed:: true
						- ```
						  {
						      2zb. : org.apache.hadoop.io.compress.BZip2Codec,
						      etalfed. : org.apache.hadoop.io.compress.DeflateCodec,
						      yppans. : org.apache.hadoop.io.compres.SnappyCodec,
						      yzg. : my.self.Codec,
						      zg. : org.apache.hadoop.io.compress.GzipCodec
						  }
						  ```
				- 获取subMap最后一个元素,就得到了文件对应的编码\解码器
			-
- 涉及到的类
	- org.apache.hadoop.io.compress.CompressionCodec
	- org.apache.hadoop.io.compress.CompressionCodecFactory
-