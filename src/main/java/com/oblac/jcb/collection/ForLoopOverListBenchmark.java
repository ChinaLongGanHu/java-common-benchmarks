package com.oblac.jcb.collection;

import com.oblac.jcb.DataGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
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

 Benchmark                                                 (size)   Mode  Samples    Score   Error   Units
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                     1  thrpt       20  262.586 ± 3.108  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                    10  thrpt       20  263.901 ± 2.660  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                   100  thrpt       20  262.818 ± 4.625  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                  1000  thrpt       20  238.309 ± 3.818  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forInLoop                 10000  thrpt       20  230.548 ± 5.020  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                  1  thrpt       20  277.934 ± 3.958  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                 10  thrpt       20  276.160 ± 3.233  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop                100  thrpt       20  276.219 ± 3.745  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop               1000  thrpt       20  247.458 ± 4.571  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexLoop              10000  thrpt       20  239.914 ± 2.185  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop         1  thrpt       20  279.704 ± 3.953  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop        10  thrpt       20  279.319 ± 4.056  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop       100  thrpt       20  280.749 ± 2.809  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop      1000  thrpt       20  249.631 ± 1.410  ops/ms
 c.o.j.c.ForLoopOverListBenchmark.forIndexOptimizedLoop     10000  thrpt       20  243.168 ± 3.077  ops/ms
 * </pre>
 * <p>
 * Conclusion: Enhanced loop if as fast as indexed loop, if not faster.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
public class ForLoopOverListBenchmark {

	@Param({"1", "10", "100", "1000", "10000"})
	private int size;

	private ArrayList<Integer> arrayList;

	@Setup
	public void setup() {
		arrayList = DataGenerator.listOfRandomIntegers(size);
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