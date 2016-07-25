package org.eim.search.web;

import com.connect_group.thymeleaf.testing.ThymeleafTestEngine;
import com.connect_group.thymeleaf.testing.config.ThymesheetTestSpringContext;
import com.connect_group.thymesheet.query.HtmlElements;

import org.eim.search.entity.FilePartEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import static org.eim.search.web.FilePartEntryGenerator.generate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ThymesheetTestSpringContext.class })
public class ThymeLeafTest {

  private final static String[] FIELDS = {
    "totalQueryCount", "currentQueryCount","frequencyQuery",
    "totalTermCount", "currentTermCount","frequencyTerm"};

  private final static String FORMAT_SELECTOR =
      "body > table > tbody > tr:nth-child(%d) > td:nth-child(%d)";

  private final long currentQueryCount = 5; // plus one - current value
  private final long totalQueryCount = 15;
  private final float frequencyQuery =
      (totalQueryCount > 0 ? (float) currentQueryCount / totalQueryCount : 0.0f);

  private final long currentTermCount = 10;
  private final long totalTermCount = 1000;
  private final float frequencyTerm =
      (totalTermCount > 0 ? (float) currentTermCount / totalTermCount : 0.0f);

  @Autowired
  private ThymeleafTestEngine testEngine;

  @Test
  public void shouldSetTitleToExpectedValue() {

    Map<String,Object> model = new HashMap<String,Object>();

    List<FilePartEntity> filePartEntityList = new ArrayList<>();
    for (int i=0; i<5; i++)
      filePartEntityList.add(generate(i));

    model.put("prepared", true);

    model.put("currentQueryCount", currentQueryCount);
    model.put("totalQueryCount", totalQueryCount);
    model.put("frequencyQuery",
        BigDecimal.valueOf(frequencyQuery).setScale(3, BigDecimal.ROUND_CEILING).floatValue());

    model.put("currentTermCount", currentTermCount);
    model.put("totalTermCount", totalTermCount);
    model.put("frequencyTerm",
        BigDecimal.valueOf(frequencyTerm).setScale(3, BigDecimal.ROUND_CEILING).floatValue());

    model.put("listOfFiles", filePartEntityList);

    HtmlElements tags = testEngine.process("src/test/resources/templates/search.html", model);

    Document doc = Jsoup.parse(tags.html());

    IntStream.range(0,FIELDS.length).forEach(idx -> testValue(doc,idx));
  }

  private void testValue(Document doc, int idx) {
    Field field = null;
    Object fieldValue = null;
    try {
      field = this.getClass().getDeclaredField(FIELDS[idx]);
      fieldValue = field.get(this);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    int group = (idx < 3?2:3);
    int index = idx%3 + 2;
    String css_selector = String.format(FORMAT_SELECTOR,group,index);

    Elements elements = doc.select(css_selector);
    if (elements != null && elements.size() > 0) {
      testValue(fieldValue, elements.get(0).text());
    }
  }

  private void testValue(Object expected, String current) {
    assertNotNull(expected);
    if (expected instanceof Float) {
      testValue((Float)expected,current);
    } else if (expected instanceof Long) {
      testValue((Long)expected,current);
    } else {
      fail();
    }
  }

  private void testValue(Float expected, String current) {
    assertEquals(expected.floatValue(),Float.valueOf(current).floatValue(),0.001f);
  }

  private void testValue(Long expected, String current) {
    assertEquals(expected.longValue(),Long.valueOf(current).longValue());
  }

}
