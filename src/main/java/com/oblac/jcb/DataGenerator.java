package com.oblac.jcb;

import java.util.ArrayList;
import java.util.Random;

/**
 * Data generator provides various data for the benchmark testing.
 */
public class DataGenerator {

	private static final Random RND = new Random();

	/**
	 * Returns list of random integers with specified size.
	 */
	public static ArrayList<Integer> listOfRandomIntegers(int size) {
		ArrayList<Integer> list = new ArrayList<>(size);

		for(int i = 0; i <10000; i++) {
			list.add(Integer.valueOf(RND.nextInt(size)));
		}

		return list;
	}
}