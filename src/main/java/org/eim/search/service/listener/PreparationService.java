package org.eim.search.service.listener;

import org.eim.search.controller.FilesController;
import org.eim.search.service.FileMonitoringSerive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author eim
 * @since 2016-07-17
 */
@Service
public class PreparationService implements ApplicationListener<ApplicationReadyEvent> {

  @Value("http://${server.host}")
  private String host;
  @Value("${server.port}")
  private String port;
  private String suffix = FilesController.PATH;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    (new RestTemplate()).getForObject(host+":"+port+suffix, String.class);
  }

}
