package org.eim.search.repository;

import org.eim.search.entity.FileMD5Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FileMD5Repository extends JpaRepository<FileMD5Entity, Long> {

  List<FileMD5Entity> findByFileName(String name);

  FileMD5Entity findByMd5(String name);

}
