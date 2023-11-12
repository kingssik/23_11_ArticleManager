package com.KoreaIT.java.AM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  // 현재 날짜 및 시간
  public static String getNowDateStr() {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();
    return sdf1.format(now);
  }
}
