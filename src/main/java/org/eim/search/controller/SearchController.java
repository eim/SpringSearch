package org.eim.search.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eim.search.entity.FilePartEntity;
import org.eim.search.service.FileMonitoringSerive;
import org.eim.search.service.IndexingService;
import org.eim.search.service.QuerySearch;
import org.eim.search.service.TermSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author eim
 * @since 2016-07-16
 */
@Controller
public class SearchController {

  public static final String PATH = "/search";

  @Autowired
  private TermSearch termSearch;

  @Autowired
  private QuerySearch querySearch;

  @Autowired
  private FileMonitoringSerive fileMonitoringSerive;

  @Autowired
  private IndexingService indexingService;

  @RequestMapping(path = PATH)
  public String search(String q, Model model)
      throws IOException, InterruptedException {

    if (!fileMonitoringSerive.prepared()) {
      model.addAttribute("started", fileMonitoringSerive.started());
      model.addAttribute("prepared", false);
      return "search";
    }

    if (indexingService.getStatus()) {
      model.addAttribute("indexing", true);
      model.addAttribute("prepared", false);
      return "search";
    }

    fileMonitoringSerive.storeData();

    long currentQueryCount = querySearch.getTermCount(q) + 1; // plus one - current value
    long totalQueryCount = querySearch.getTotalTermCount();
    float frequencyQuery =
        (totalQueryCount > 0 ? (float) currentQueryCount / totalQueryCount : 0.0f);

    long currentTermCount = termSearch.getTermCount(q);
    long totalTermCount = termSearch.getTotalTermCount();
    float frequencyTerm = (totalTermCount > 0 ? (float) currentTermCount / totalTermCount : 0.0f);

    List<FilePartEntity> filePartEntityList = termSearch.getEntityList(q);
    List<String> listOfFiles = new ArrayList<>();
    if (filePartEntityList != null && !filePartEntityList.isEmpty()) {
      listOfFiles = filePartEntityList.stream().map(a -> a.getFilePath()).distinct().sorted()
          .collect(Collectors.toList());
    }

    model.addAttribute("prepared", true);

    model.addAttribute("currentQueryCount", currentQueryCount);
    model.addAttribute("totalQueryCount", totalQueryCount);
    model.addAttribute("frequencyQuery",
        BigDecimal.valueOf(frequencyQuery).setScale(3, BigDecimal.ROUND_CEILING).floatValue());

    model.addAttribute("currentTermCount", currentTermCount);
    model.addAttribute("totalTermCount", totalTermCount);
    model.addAttribute("frequencyTerm",
        BigDecimal.valueOf(frequencyTerm).setScale(3, BigDecimal.ROUND_CEILING).floatValue());

    model.addAttribute("listOfFiles", listOfFiles);

    return "search";
  }

  @RequestMapping("/")
  @ResponseBody
  public String index() {
    return "Try to go here: " + "<a href='/search?q=whatsup'>/search?q=whatsup</a>";
  }

}