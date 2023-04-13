package com.nixsolutions.ppp.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
abstract class KeystoreTest {

  private static final String KEY = "AA-00001";
  private Keystore keystore;

  abstract Keystore getKeystore();

  @BeforeEach
  void setUp() {
    keystore = getKeystore();

    assertKeystoreIsEmpty();
  }

  private void assertKeystoreIsEmpty() {
    assertEquals(0, keystore.size());
  }

  @Test
  void constructor_OnlyNoArgsDeclared() {
    var implementationType = keystore.getClass();

    var constructors = implementationType.getConstructors();

    assertEquals(1, constructors.length);
    assertEquals(0, constructors[0].getParameterCount());
  }

  @Test
  void constructor_NoArgs_InitEmptyKeystore() throws Exception {

    var noArgsConstructor = getKeystore().getClass().getConstructor();

    Keystore instance = noArgsConstructor.newInstance();

    assertEquals(0, instance.size());
  }

  @Test
  void add_NewKey_ReturnTrueAndAddIt() {
    assertFalse(keystore.contains(KEY));

    assertTrue(keystore.add(KEY));

    assertKeystoreContainsOnlyOneKey(KEY);
  }

  private void assertKeystoreContainsOnlyOneKey(String key) {
    assertTrue(keystore.contains(key));
    assertEquals(1, keystore.size());
  }

  @Test
  void add_NotNewKey_ReturnFalseWithoutAdd() {
    keystore.add(KEY);
    assertKeystoreContainsOnlyOneKey(KEY);

    assertFalse(keystore.add(KEY));

    assertKeystoreContainsOnlyOneKey(KEY);
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\r\n", "\n\r"})
  void add_BlankOrNullKey_ReturnFalseWithoutAdd(String key) {
    assertKeystoreIsEmpty();

    assertFalse(keystore.add(key));

    assertKeystoreIsEmpty();
  }

  @Test
  void add_NewKeyIfKeystoreIsFull_ReturnFalseWithoutAdd() {
    fillKeystoreWithKeys();
    assertKeystoreIsFull();
    assertFalse(keystore.contains(KEY));

    assertFalse(keystore.add(KEY));

    assertFalse(keystore.contains(KEY));
    assertKeystoreIsFull();
  }

  private void fillKeystoreWithKeys() {
    IntStream.range(0, Keystore.MAX_CAPACITY).mapToObj("CC-%05d"::formatted).forEach(keystore::add);
  }

  private void assertKeystoreIsFull() {
    assertEquals(Keystore.MAX_CAPACITY, keystore.size());
  }

  @Test
  void clear_ContainsKeys_RemoveAll() {
    fillKeystoreWithKeys();
    assertKeystoreIsFull();

    keystore.clear();

    assertKeystoreIsEmpty();
  }

  @Test
  void clear_EmptyKeystore_NoOperation() {
    assertKeystoreIsEmpty();

    keystore.clear();

    assertKeystoreIsEmpty();
  }

  @Test
  void contains_KeyNotAdded_ReturnFalse() {
    assertKeystoreIsEmpty();

    assertFalse(keystore.contains(KEY));
  }

  @Test
  void contains_KeyAdded_ReturnTrue() {
    keystore.add(KEY);

    assertTrue(keystore.contains(KEY));
  }

  @Test
  void contains_Null_ReturnFalse() {
    keystore.add(KEY);

    assertFalse(keystore.contains(null));
  }

  @Test
  void get_ValidIndex_ReturnKey() {
    keystore.add(KEY);

    assertEquals(KEY, keystore.get(0));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  void get_ValidButEmptyIndex_ReturnNull(int index) {
    keystore.add(KEY);

    assertNull(keystore.get(index));
  }

  @ParameterizedTest
  @ValueSource(ints = {Integer.MIN_VALUE, -1, Keystore.MAX_CAPACITY, Integer.MAX_VALUE})
  void get_InvalidIndex_ReturnNull(int index) {
    keystore.add(KEY);

    assertNull(keystore.get(index));
  }

  @Test
  void size_AfterInit_ReturnsZero() {
    assertKeystoreIsEmpty();
  }

  @Test
  void size_AfterAddition_Increases() {
    assertKeystoreIsEmpty();

    keystore.add(KEY);

    assertEquals(1, keystore.size());
  }
}