/*
 * Copyright 2017, OpenCensus Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.example.gameoflife;

import io.opencensus.stats.AggregationData;
import io.opencensus.stats.View.AggregationWindow.Interval;
import io.opencensus.stats.ViewData;
import io.opencensus.stats.ViewData.AggregationWindowData.CumulativeData;
import io.opencensus.tags.TagValue;
import java.util.List;
import java.util.Map.Entry;

/* Util methods. */
final class GolUtils {

  static void printView(ViewData viewData) {
    if (viewData == null) {
      return;
    }
    if (viewData.getView().getWindow() instanceof Interval) {
      return; // Do not print interval views
    }
    CumulativeData cumulativeData = (CumulativeData) viewData.getWindowData();
    System.out.println(
        "\nViewData: starts at "
        + cumulativeData.getStart()
        + " ends at "
        + cumulativeData.getEnd());
    System.out.println("view: " + viewData.getView() + '\n');
    for (Entry<List<TagValue>, AggregationData> entry : viewData.getAggregationMap().entrySet()) {
      System.out.println("AggregationData: " + entry.getValue() + '\n');
    }
  }

  static int toInt(String s) {
    try {
      return Integer.valueOf(s);
    } catch (NumberFormatException exn) {
      return 0;
    }
  }

  static TagValue getTagValue(int dim, int gensPerGol, String suffix) {
    return TagValue.create(dim + "x" + dim + "-" + gensPerGol + "-" + suffix);
  }

  static String getRequest(String gen, int gensPerGol) {
    return "gol " + gensPerGol + " " + gen;
  }

  private GolUtils() {
  }
}
