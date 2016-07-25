package org.eim.search.controller;

import org.eim.search.service.FileMonitoringSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FilesController {

  public static final String PATH = "/updatefiles";

  @Autowired
  private FileMonitoringSerive fileMonitoringSerive;

  @RequestMapping(path = PATH)
  public void updatefiles() {
    if (fileMonitoringSerive.renew()) {
      fileMonitoringSerive.storeData();
      fileMonitoringSerive.finish();
    }
  }

}
