package org.eim.search.service;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.eim.search.entity.FilePartEntity;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class TermSearch extends AbstractSearch {

  public long getTermCount(String query) throws IOException {
    return getTermCount(FilePartEntity.class, "entry", query);
  }

  public List<FilePartEntity> getEntityList(String query) throws IOException {
    FullTextQuery jpaQuery = null;
    TermQuery luceneQuery = new TermQuery(new Term("entry", query));
    FullTextSession fullTextSession = getFullTextSession();
    jpaQuery = fullTextSession.createFullTextQuery(luceneQuery, FilePartEntity.class);
    return jpaQuery.list();
  }

  @Override
  public long getTotalTermCount() throws IOException {
    return getTotalTermCount(FilePartEntity.class, "entry");
  }
}
