/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.runtime;

import com.google.uzaygezen.core.BitVector;
import com.google.uzaygezen.core.BitVectorFactories;
import com.google.uzaygezen.core.CompactHilbertCurve;
;

/**
 * 2-dimensional Hilbert space-filling curve.
 *
 * <p>Based upon <a href="https://github.com/locationtech/sfcurve">SFCurve</a>.
 */
public class HilbertCurve2D {
  final long precision;
  final CompactHilbertCurve chc;
  private final int resolution;

  public HilbertCurve2D(int resolution) {
    this.resolution = resolution;
    precision = (long) Math.pow(2, resolution);
    chc = new CompactHilbertCurve(new int[] {resolution, resolution});
  }

  long getNormalizedLongitude(double x) {
    return (long) ((x + 180) * (precision - 1) / 360d);
  }

  long getNormalizedLatitude(double y) {
    return (long) ((y + 90) * (precision - 1) / 180d);
  }

  public long toIndex(double x, double y) {
    final long normX = getNormalizedLongitude(x);
    final long normY = getNormalizedLatitude(y);
    final BitVector[] p = {
        BitVectorFactories.OPTIMAL.apply(resolution),
        BitVectorFactories.OPTIMAL.apply(resolution)
    };

    p[0].copyFrom(normX);
    p[1].copyFrom(normY);

    final BitVector hilbert = BitVectorFactories.OPTIMAL.apply(resolution * 2);
    chc.index(p, 0, hilbert);
    return hilbert.toLong();
  }
}
