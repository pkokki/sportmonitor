package com.panos.sportmonitor.spark.streams;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class AbstractJavaStream<T> extends JavaDStream<T> {
    public AbstractJavaStream(JavaDStream<T> stream) {
        super(stream.dstream(), stream.classTag());
    }

    @SafeVarargs
    public final void output(VoidFunction2<JavaRDD<T>, Time>... functions) {
        this.foreachRDD((rdd, time) -> {
            for (VoidFunction2<JavaRDD<T>, Time> function: functions) {
                function.call(rdd, time);
            }
        });
    }
}
