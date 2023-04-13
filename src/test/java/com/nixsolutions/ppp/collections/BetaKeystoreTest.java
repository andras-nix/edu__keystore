package com.nixsolutions.ppp.collections;

class BetaKeystoreTest extends KeystoreTest {

  @Override
  Keystore getKeystore() {
    return new BetaKeystore();
  }
}