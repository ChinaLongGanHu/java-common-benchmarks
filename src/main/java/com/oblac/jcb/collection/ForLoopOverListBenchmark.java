package com.oblac.jcb.collection;

import com.oblac.jcb.DataGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark for different ways of iterating the List.
 * <ul>
 *     <li>Using Enhanced for loop ('for in')</li>
 *     <li>Common index loop</li>
 *     <li>Optimized index loop, as IntelliJ Idea suggest</li>
 * </ul>
 *
 * <pre>

 # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/bin/java
 # VM options: -Dfile.encoding=UTF-8 -Duser.country=US -Duser.language=en -Duser.variant

 Benchmark                                                  Mode  Samples    Score   Error   Units
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                thrpt       20  232.383 ± 3.718  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop             thrpt       20  239.481 ± 1.672  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop    thrpt       20  240.767 ± 3.549  ops/ms * </pre>

 * </pre>
 * <p>
 * Conclusion: Enhanced loop if as fast as indexed loop.
 *
 * todo: test on different volumes of data
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
public class ForLoopOverListBenchmark {

	private ArrayList<Integer> arrayList;

	@Setup
	public void setup() {
		arrayList = DataGenerator.listOfRandomIntegers(10000);
	}

	@Benchmark
	public void forInLoop(Blackhole bh) {
		int count = 0;
		for (Integer integer : arrayList) {
			count += integer.intValue();
		}
		bh.consume(count);
	}

	@Benchmark
	public void forIndexLoop(Blackhole bh) {
		int count = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			Integer integer = arrayList.get(i);
			count += integer.intValue();
		}
		bh.consume(count);
	}

	@Benchmark
	public void forIndexOptimizedLoop(Blackhole bh) {
		int count = 0;
		for (int i = 0, arrayListSize = arrayList.size(); i < arrayListSize; i++) {
			Integer integer = arrayList.get(i);
			count += integer.intValue();
		}
		bh.consume(count);
	}

}