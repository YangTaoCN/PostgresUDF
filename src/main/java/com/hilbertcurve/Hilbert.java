package com.hilbertcurve;

import org.postgresql.pljava.annotation.Function;
import com.runtime.HilbertCurve2D;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Hilbert {
  @Function
  public static long hilbert(double latitude, double longitude) {
      return new HilbertCurve2D(8).toIndex(longitude, latitude);
  }
}