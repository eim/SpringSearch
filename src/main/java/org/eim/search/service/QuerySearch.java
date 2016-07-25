package org.eim.search.service;

import org.eim.search.entity.QueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class QuerySearch extends AbstractSearch {

  private void save(String query) {
    QueryEntity queryEntity = new QueryEntity();
    queryEntity.setValue(query);
    queryRepository.saveAndFlush(queryEntity);
  }

  public long getTermCount(String query) throws InterruptedException, IOException {
    save(query);
    return getTermCount(QueryEntity.class, "value", query);
  }

  @Override
  public long getTotalTermCount() throws IOException {
    return getTotalTermCount(QueryEntity.class, "value");
  }

}
