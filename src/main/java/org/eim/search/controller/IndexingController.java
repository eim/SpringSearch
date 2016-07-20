package org.eim.search.controller;

import org.eim.search.service.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author eim
 * @since 2016-07-17
 */
@Controller
public class IndexingController {

  @Autowired
  private IndexingService indexingService;

  @RequestMapping(path = "/indexing")
  public void reindex() {
    indexingService.reindex();
  }

}
