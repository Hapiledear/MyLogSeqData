package com.xiaomi.planet.user.data.service.flink.example;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Demo about
 * 本地的单词统计程序
 * 如何体验本项目:
 * 1. 安装nc 作为server |启动 nc -l -p 9999 |win nc.exe -l -p 9999
 * 		如果不能安装nc 则可以使用python 构建一个socket程序,但需要注意每一行的分隔符不要用\n
 * 		或者使用 NetAssist(网络调试助手) 也得注意\n 或者发送一个文件
 * 2. IDE中运行此Main方法 需要勾选 Include dependencies with "Provided" scope
 */
public class WorldCountLocal {
	private static final Logger log = LoggerFactory.getLogger(WorldCountLocal.class);

	public static void main(String[] args) throws Exception {
		//step.0 解析配置文件或命令行的参数
		Configuration conf = new Configuration();
		// 开启flink UI界面 http://localhost:8081
		conf.setString(RestOptions.BIND_PORT,"8081-8089");

		//step.1 初始化Stream 执行环境| 在ide中运行则是本地环境运行,打包成jar包并已命令行启动则是集群环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);
		//step.2 配置参数

		//step.3 读取外部数据| 从9999端口读取数据输入,分隔符是\n,如果使用python的socket 建议更换分隔符
		DataStream<Tuple2<String, Integer>> dataStream =
				env.socketTextStream("localhost",9999,"\n")
		//step.4 处理数据
				// Splitter是一个高阶UDF
				.flatMap(new Splitter())
				.keyBy(value -> value.f0)
				// 固定时间窗口
				.window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
				// 聚合
				.sum(1);
		//step5. 将结果写入外部
		log.info("prepare to print data");
			//虽然是System.out.print 但还是使用的是log,因此需要项目配置好log4j
		dataStream.print("==== res is ==== ").setParallelism(1);
//		dataStream.writeAsText("out.txt");
		//step6. 触发执行
		env.execute("Window WordCount");

	}

	public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
		@Override
		public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
			log.info("get data [{}]",sentence);
			for (String word: sentence.split("\\s")) {
				out.collect(new Tuple2<String, Integer>(word, 1));
			}
		}

	}

}
