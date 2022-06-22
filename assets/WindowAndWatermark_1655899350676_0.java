package com.xiaomi.planet.user.data.service.flink.example;

import java.io.File;
import java.time.Duration;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaomi.planet.user.data.service.flink.beans.SessionMock;

/**
 * demo about 窗口函数和watermark
 * 想使用WaterMark，需要3个步骤：
 * 1、对数据进行timestamp提取，即调用assignTimestampsAndWatermarks函数，
 *     使用WatermarkStrategy 策略初始化 watermark生成策略 和 指定哪个字段用作 timestamp
 * 2、设置使用事件时间，因为WaterMark是基于事件时间(默认)
 * 3、定义时间窗口：翻滚窗口（TumblingEventTimeWindows）、滑动窗口（SlidingEventTimeWindows）
 */
public class WindowAndWatermark {

	private static final Logger log = LoggerFactory.getLogger(WindowAndWatermark.class);

	public static void main(String[] args) throws Exception {
		//step.0 解析配置文件或命令行的参数
		Configuration conf = new Configuration();

		//step.1 初始化Stream 执行环境| 在ide中运行则是本地环境运行,打包成jar包并已命令行启动则是集群环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);

		String filePath = "E:\\code\\planet\\user-data-service\\user-data-service-flink\\src\\main\\resources\\SessionMock.csv";

		//<editor-fold desc="读取csv文件">
		File dataFile = new File(filePath);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema =
				mapper.schemaFor(SessionMock.class).withoutQuoteChar();

		CsvReaderFormat<SessionMock> csvFormat =
				CsvReaderFormat.forSchema(mapper, schema, TypeInformation.of(SessionMock.class));
		//</editor-fold>

		FileSource<SessionMock> source =
				FileSource.forRecordStreamFormat(csvFormat, Path.fromLocalFile(dataFile)).build();
		//1. 指定timestamp和window策略
		env.fromSource(source,(WatermarkStrategy.<SessionMock>forBoundedOutOfOrderness(Duration.ofHours(1))
						.withTimestampAssigner(new SerializableTimestampAssigner<SessionMock>(){
							@Override
							public long extractTimestamp(SessionMock sessionMock, long l) {
								return sessionMock.getClientTime();
							}
						})),"read csv")
// 1. 如果是读取txt文件,这样做.
//		env.readTextFile(filePath)
//				.map(line -> {
//					String[] sp = line.split(",");
//					SessionMock res = new SessionMock();
//					res.setPlatform(sp[0]);
//					res.setClientTime(Long.valueOf(sp[1]));
//					res.setUserId(sp[2]);
//					res.setPage(sp[3]);
//					return res;
//				})
//				.assignTimestampsAndWatermarks(WatermarkStrategy.<SessionMock>forBoundedOutOfOrderness(Duration.ofHours(1))
//						.withTimestampAssigner(new SerializableTimestampAssigner<SessionMock>(){
//							@Override
//							public long extractTimestamp(SessionMock sessionMock, long l) {
//								return sessionMock.getClientTime();
//							}
//						})
//				)
				// 业务处理
				.map(item -> new Tuple2<SessionMock,Integer>(item,1))
				.returns(TypeInformation.of(new TypeHint<Tuple2<SessionMock, Integer>>() {}))
				.keyBy(new KeySelector<Tuple2<SessionMock,Integer>, String>() {
						   @Override
						   public String getKey(Tuple2<SessionMock,Integer> tuple2) throws Exception {
							   return tuple2.f0.getUserId();
						   }
					   }
				)
				//2. 时间窗口
				.window(TumblingEventTimeWindows.of(Time.hours(10)))
				.sum(1)
				// 将结果写入文件
				.addSink( StreamingFileSink.<Tuple2<SessionMock,Integer>>forRowFormat(
						new Path("E:\\code\\planet\\user-data-service\\user-data-service-flink\\src\\main\\resources\\res"),
						new SimpleStringEncoder<>()
				).build());
		//step6. 触发执行
		env.execute("session compute");

		log.info("task finish");
	}

}