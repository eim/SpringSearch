package org.eim.search.repository;

import org.eim.search.entity.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author eim
 * @since 2016-07-17
 */
public interface QueryRepository extends JpaRepository<QueryEntity, Long> {

  List<QueryEntity> findByValue(String query);

}
