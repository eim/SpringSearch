package org.eim.search.service;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.eim.search.entity.QueryEntity;
import org.eim.search.repository.FileJpaRepository;
import org.eim.search.repository.QueryRepository;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author eim
 * @since 2016-07-17
 */
public abstract class AbstractSearch {


  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  protected QueryRepository queryRepository;


  @Autowired
  protected FileJpaRepository fileJpaRepository;

  public FullTextSession getFullTextSession() {
    Session session = entityManager.unwrap(Session.class);
    return Search.getFullTextSession(session);
  }

  /**
   *
   * @param clazz
   * @param field
   * @param term
   * @return
   * @throws IOException
   */
  public long getTermCount(Class<?> clazz, String field, String term) throws IOException {
    try (IndexReader indexReader =
             getSearchFactory().getIndexReaderAccessor().open(clazz)) {
      return indexReader.totalTermFreq(new Term(field, term));
    }
  }

  /**
   *
   * @param clazz
   * @param field
   * @return
   * @throws IOException
   */
  long getTotalTermCount(Class<?> clazz, String field) throws IOException {
    try (IndexReader indexReader =
             getSearchFactory().getIndexReaderAccessor().open(clazz)) {
      return indexReader.getSumTotalTermFreq(field);
    }
  }

  abstract long getTotalTermCount() throws IOException;

  public SearchFactory getSearchFactory() {
    return getFullTextSession().getSearchFactory();
  }

}
