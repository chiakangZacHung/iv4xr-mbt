/**
 * 
 */
package eu.fbk.iv4xr.mbt.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.MersenneTwister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.fbk.iv4xr.mbt.MBTProperties;

/**
 * Unique random number accessor
 * 
 * @author Gordon Fraser
 */
public class RandomnessMBT implements Serializable {

	private static final long serialVersionUID = -5934455398558935937L;

	private static final Logger logger = LoggerFactory.getLogger(RandomnessMBT.class);

	private static long seed = 0;

	private static MersenneTwister random = null;

	private static RandomnessMBT instance = new RandomnessMBT();

	private RandomnessMBT() {
		seed = MBTProperties.RANDOM_SEED;
		logger.info("No seed given. Using {}.", seed);
		
		random = new MersenneTwister(seed);
	}

	
	private RandomnessMBT(long s) {
		seed = s;
		logger.info("No seed given. Using {}.", seed);
		
		random = new MersenneTwister(seed);
	}
	
	
	/**
	 * <p>
	 * Getter for the field <code>instance</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.utils.Randomness} object.
	 */
	public static RandomnessMBT getInstance() {
		if (instance == null) {
			instance = new RandomnessMBT();
		}
		return instance;
	}

	
	/**
	 * <p>
	 * Getter for the field <code>instance</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.utils.Randomness} object.
	 */
	public static RandomnessMBT getInstance(long dummySeed) {
		instance = new RandomnessMBT(dummySeed);
		return instance;
	}
	
	/**
	 * <p>
	 * nextBoolean
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public static boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the
	 * specified value {@code max} (exclusive).
	 *
	 * @param max the upper bound
	 * @return a random number between 0 and {@code max - 1}
	 * @see Random#nextInt(int)
	 */
	public static int nextInt(int max) {
		return random.nextInt(max);
	}

	public static double nextGaussian() {
		return random.nextGaussian();
	}
	
	/**
	 * Returns a pseudorandom, uniformly distributed int value between the lower bound {@code min}
	 * (inclusive) and the upper bound {@code max} (exclusive).
	 *
	 * @param min the lower bound
	 * @param max the upper bound
	 * @return a random number between {@code min} and {@code max}
	 */
	public static int nextInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	/**
	 * <p>
	 * nextInt
	 * </p>
	 * 
	 * @return a int.
	 */
	public static int nextInt() {
		return random.nextInt();
	}

	/**
	 * <p>
	 * nextChar
	 * </p>
	 * 
	 * @return a char.
	 */
	public static char nextChar() {
		return (char) (nextInt(32, 128));
		//return random.nextChar();
	}

	/**
	 * <p>
	 * nextShort
	 * </p>
	 * 
	 * @return a short.
	 */
	public static short nextShort() {
		return (short) (random.nextInt(2 * 32767) - 32767);
	}

	/**
	 * <p>
	 * nextLong
	 * </p>
	 * 
	 * @return a long.
	 */
	public static long nextLong() {
		return random.nextLong();
	}

	/**
	 * <p>
	 * nextByte
	 * </p>
	 * 
	 * @return a byte.
	 */
	public static byte nextByte() {
		return (byte) (random.nextInt(256) - 128);
	}

	/**
	 * <p>
	 * returns a randomly generated double in the range [0,1]
	 * </p>
	 * 
	 * @return a double between 0.0 and 1.0
	 */
	public static double nextDouble() {
		return random.nextDouble();
	}

	/**
	 * <p>
	 * nextDouble
	 * </p>
	 * 
	 * @param min
	 *            a double.
	 * @param max
	 *            a double.
	 * @return a double.
	 */
	public static double nextDouble(double min, double max) {
		return min + (random.nextDouble() * (max - min));
	}

	/**
	 * <p>
	 * nextFloat
	 * </p>
	 * 
	 * @return a float.
	 */
	public static float nextFloat() {
		return random.nextFloat();
	}

	/**
	 * <p>
	 * Setter for the field <code>seed</code>.
	 * </p>
	 * 
	 * @param seed
	 *            a long.
	 */
	public static void setSeed(long seed) {
		RandomnessMBT.seed = seed;
		random.setSeed(seed);
	}

	/**
	 * <p>
	 * Getter for the field <code>seed</code>.
	 * </p>
	 * 
	 * @return a long.
	 */
	public static long getSeed() {
		return seed;
	}

	/**
	 * <p>
	 * choice
	 * </p>
	 * 
	 * @param list
	 *            a {@link java.util.List} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object or <code>null</code> if <code>list</code> is empty.
	 */
	public static <T> T choice(List<T> list) {
		if (list.isEmpty())
			return null;

		int position = random.nextInt(list.size());
		return list.get(position);
	}

	/**
	 * <p>
	 * choice
	 * </p>
	 * 
	 * @param set
	 *            a {@link java.util.Collection} object.
	 * @param <T>
	 *            a T object.
	 * @return a T object or <code>null</code> if <code>set</code> is empty.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T choice(Collection<T> set) {
		if (set.isEmpty())
			return null;

		int position = random.nextInt(set.size());
		return (T) set.toArray()[position];
	}

	/**
	 * <p>
	 * choice
	 * </p>
	 * 
	 * @param elements
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a T object or <code>null</code> if <code>elements.length</code> is zero.
	 */
	public static <T> T choice(T... elements) {
		if (elements.length == 0)
			return null;

		int position = random.nextInt(elements.length);
		return elements[position];
	}

	/**
	 * <p>
	 * shuffle
	 * </p>
	 * 
	 * @param list
	 *            a {@link java.util.List} object.
	 */
	public static void shuffle(List<?> list) {
		Collections.shuffle(list);
	}

	/**
	 * <p>
	 * nextString
	 * </p>
	 * 
	 * @param length
	 *            a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String nextString(int length) {
		char[] characters = new char[length];
		for (int i = 0; i < length; i++)
			characters[i] = nextChar();
		return new String(characters);
	}
}

