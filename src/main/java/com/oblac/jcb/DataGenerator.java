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
	 * Values are uniformly distributed.
	 */
	public static ArrayList<Integer> listOfRandomIntegers(int size) {
		ArrayList<Integer> list = new ArrayList<>(size);

		for (int i = 0; i <size; i++) {
			list.add(Integer.valueOf(RND.nextInt()));
		}

		return list;
	}


	/**
	 * Returns array of random uniformly distributed integers with specified size.
	 */
	public static int[] arrayOfRandomInts(int size) {
		int[] array = new int[size];

		for (int i = 0; i <size; i++) {
			array[i] = RND.nextInt();
		}

		return array;
	}
}