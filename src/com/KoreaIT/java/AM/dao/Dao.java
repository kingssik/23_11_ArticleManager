package com.KoreaIT.java.AM.dao;

public abstract class Dao {
  public int lastId;

  public Dao() {
    lastId = 0;
  }

  public int getLastId() {
    return lastId;
  }
}
