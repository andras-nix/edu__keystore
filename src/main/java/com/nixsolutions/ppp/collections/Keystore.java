package com.nixsolutions.ppp.collections;

/**
 * Set-like keystore based on {@link String} with fixed {@link Keystore#MAX_CAPACITY capacity} that
 * allows neither {@link String#isBlank() blank} or duplicate elements nor {@code null}. More
 * formally, {@link Keystore} contains no pair of elements {@code e1} and {@code e2} such that
 * {@code e1.equals(e2)}, and contains no element {@code e} where {@code e.isBlank()} or
 * {@code e == null} evaluates to true.
 * <p>
 * An implementation should have only no-arg constructor that instantiates an empty instance.
 */
public interface Keystore {

  /**
   * The maximum capacity of a {@link Keystore} instance.
   */
  int MAX_CAPACITY = 32;

  /**
   * Adds the specified element ({@code key}) to this {@link Keystore} if it fulfills all criteria
   * (optional operation). If and only if the addition is successful the method returns with
   * {@code true}.
   * <p>
   * More formally, adds the specified element {@code key} to this {@link Keystore} if:
   * <ul>
   *   <li>its {@link Keystore#size() size} is below the {@link Keystore#MAX_CAPACITY}</li>
   *   <li>it contains no element {@code key2} such that {@code key2.equals(key)}</li>
   *   <li>and both {@code key == null} and {@code key.isBlank()} evaluates to {@code false}.</li>
   * </ul>
   * <p>
   * If the given argument violates some of the constraints, the call leaves {@link Keystore}
   * unchanged and returns {@code false}. In combination with the restriction on constructors, this
   * ensures that {@link Keystore} never contain invalid elements.
   *
   * @param key element to be added to this {@link Keystore}
   * @return {@code true} if the addition was successful
   */
  boolean add(String key);

  /**
   * Removes all elements (keys) from this {@link Keystore} (optional operation). The
   * {@link Keystore} will be empty after this call returns.
   */
  void clear();

  /**
   * Returns {@code true} if this {@link Keystore} contains the specified element: {@code key}
   * <p>
   * More formally, returns {@code true} if and only if the {@link Keystore} contains an element
   * {@code e} such that {@code e.equals(key)}.
   *
   * @param key element whose presence in this {@link Keystore} is to be tested
   * @return {@code true} if this {@link Keystore} contains the specified element
   */
  boolean contains(String key);

  /**
   * Returns the element (key) at the specified position in this {@link Keystore}. If the
   * {@code index} is out of bound or the given position is empty, the call returns with
   * {@code null}.
   * <p>
   * Note: This index is zero based.
   *
   * @param index index of the element to return
   * @return the element (key) at the specified position in this {@link Keystore} or {@code null}
   */
  String get(int index);

  /**
   * Returns the number of elements (keys) in this {@link Keystore} (its cardinality).
   *
   * @return the number of elements (keys) in this {@link Keystore}
   */
  int size();
}
