package org.eim.search.service;


import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.eim.search.entity.QueryEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

/**
 * @author eim
 * @since 2016-07-17
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class QuerySearch extends AbstractSearch {

  private void save(String query) {
    QueryEntity queryEntity = new QueryEntity();
    queryEntity.setValue(query);
    queryRepository.saveAndFlush(queryEntity);
  }

  public long getTermCount(String query) throws InterruptedException, IOException {
    save(query);
    return getTermCount(QueryEntity.class,"value",query);
  }

  @Override
  public long getTotalTermCount() throws IOException {
    return getTotalTermCount(QueryEntity.class,"value");
  }

}
