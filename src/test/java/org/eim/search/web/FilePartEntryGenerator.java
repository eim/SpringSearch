package org.eim.search.web;

import org.eim.search.entity.FilePartEntity;

public class FilePartEntryGenerator {

  public static FilePartEntity generate(int i) {
    FilePartEntity filePartEntity = new FilePartEntity();
    filePartEntity.setId(Long.valueOf(i));
    filePartEntity.setEntry("Test entry " + i);
    filePartEntity.setFilePath("/users/user/path_in_home" + i);
    filePartEntity.setPart(String.valueOf(i));
    return filePartEntity;
  }

}
