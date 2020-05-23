package com.codurance.principle12.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Convert {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
