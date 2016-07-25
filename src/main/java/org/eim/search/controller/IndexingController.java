package org.eim.search.controller;

import org.eim.search.service.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexingController {

  public static final String PATH = "/indexing";

  @Autowired
  private IndexingService indexingService;

  @RequestMapping(path = PATH)
  public void reindex() {
    indexingService.reindex();
  }

}
