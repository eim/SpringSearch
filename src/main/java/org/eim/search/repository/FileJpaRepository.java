package org.eim.search.repository;

import org.eim.search.entity.FilePartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eim
 * @since 2016-07-16
 */
@Transactional
@Repository
public interface FileJpaRepository extends JpaRepository<FilePartEntity,Long> {

  @Query
  List<FilePartEntity> findByEntryContaining(String query);

  List<FilePartEntity> findByFilePath(String query);

}
