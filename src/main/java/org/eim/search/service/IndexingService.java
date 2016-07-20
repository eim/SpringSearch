package org.eim.search.service;

import groovy.lang.Singleton;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author eim
 * @since 2016-07-17
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional
public class IndexingService {

  private AtomicBoolean ONLYONCE = new AtomicBoolean(false);

  @PersistenceContext
  private EntityManager entityManager;

  public void reindex() {
    if (!ONLYONCE.get()) {
      ONLYONCE.compareAndSet(false,true);
      FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
      try {
        fullTextEntityManager.createIndexer().startAndWait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ONLYONCE.compareAndSet(true,false);
    }
  }

  public boolean getStatus() {
    return ONLYONCE.get();
  }

}
