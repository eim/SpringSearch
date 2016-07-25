package org.eim.search.repository;

import org.eim.search.entity.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface QueryRepository extends JpaRepository<QueryEntity, Long> {

  List<QueryEntity> findByValue(String query);

}
