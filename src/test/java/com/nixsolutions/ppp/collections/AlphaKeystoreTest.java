package com.nixsolutions.ppp.collections;

class AlphaKeystoreTest extends KeystoreTest {

  @Override
  Keystore getKeystore() {
    return new AlphaKeystore();
  }
}