package org.eim.search.controller;

import org.eim.search.service.FileMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FilesController {

  public static final String PATH = "/updatefiles";

  @Autowired
  private FileMonitoringService fileMonitoringService;

  @RequestMapping(path = PATH)
  public void updatefiles() {
    if (fileMonitoringService.renew()) {
      fileMonitoringService.storeData();
      fileMonitoringService.finish();
    }
  }

}
