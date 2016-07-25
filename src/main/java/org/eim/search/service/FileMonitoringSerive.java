package org.eim.search.service;

import org.eim.search.entity.FileMD5Entity;
import org.eim.search.entity.FilePartEntity;
import org.eim.search.repository.FileJpaRepository;
import org.eim.search.repository.FileMD5Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional
public class FileMonitoringSerive {

  @Value("${source.files.directory}")
  private String FILES_DIRECTORY;

  @Autowired
  private FileMD5Repository md5Repository;

  @Autowired
  private FileJpaRepository fileEntityRepository;

  private AtomicBoolean started = new AtomicBoolean(false);

  private AtomicBoolean finished = new AtomicBoolean(false);

  /**
   * Calculate MD5 sum for a file
   * 
   * @param filePath
   * @return
   * @throws NoSuchAlgorithmException
   * @throws IOException
   */
  private String getMD5(String filePath) throws NoSuchAlgorithmException, IOException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    try (FileInputStream fis = new FileInputStream(filePath)) {

      byte[] bytes = new byte[1024];

      int iread = 0;
      while ((iread = fis.read(bytes)) != -1) {
        md.update(bytes, 0, iread);
      }

    }
    byte[] md5bytes = md.digest();

    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < md5bytes.length; i++) {
      sb.append(Integer.toString((md5bytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    return sb.toString();
  }

  public boolean renew() {
    if (finished.get()) {
      finished.compareAndSet(true, false);
      started.compareAndSet(true, false);
    }
    if ((!started.get() && !finished.get())) {
      return true;
    }
    return false;
  }

  public boolean started() {
    return started.get();
  }

  public boolean prepared() {
    return finished.get();
  }

  public void finish() {
    if (!finished.get()) {
      finished.compareAndSet(false, true);
    }
  }

  @Transactional
  public void storeData() {
    if (finished.get() || started.get())
      return;
    started.compareAndSet(false, true);

    try {
      DirectoryStream<Path> directoryStream =
          Files.newDirectoryStream(Paths.get(FILES_DIRECTORY), "*.{txt}");

      // List of the parts of a file
      List<FilePartEntity> fileEntityList = null;
      List<FileMD5Entity> fileMD5s = null;

      // md5 sum - detection changes in a file
      String md5 = null;
      // found or not found md5 sum in db
      boolean found = false;

      // Temporary file name storage
      String filePath = null;

      List<String> existsFiles = md5Repository.findAll().stream().map(a -> a.getFileName())
          .distinct().sorted().collect(Collectors.toList());

      for (Path path : directoryStream) {
        if (!Files.isDirectory(path)) {

          found = false;
          md5 = getMD5(path.toAbsolutePath().toString());
          filePath = path.toFile().getName();
          fileMD5s = md5Repository.findByFileName(filePath);

          if (fileMD5s != null && fileMD5s.size() > 0) {
            for (FileMD5Entity FileMD5Entity : fileMD5s) {
              if (FileMD5Entity != null && FileMD5Entity.getMd5() != null) {
                if (FileMD5Entity.getMd5().equalsIgnoreCase(md5)) {
                  found = true;
                  existsFiles.remove(filePath);
                  break;
                } else {
                  FileMD5Entity fileMD51 = md5Repository.findByMd5(md5);
                  md5Repository.delete(fileMD51);
                }
              }
            }
          }

          if (!found) {
            fileEntityList = getFileEntity(path);
            for (FilePartEntity filePartEntity : fileEntityList) {
              fileEntityRepository.saveAndFlush(filePartEntity);
            }

            FileMD5Entity fileMD5Entity = new FileMD5Entity();
            fileMD5Entity.setFileName(filePath);
            fileMD5Entity.setMd5(md5);
            md5Repository.saveAndFlush(fileMD5Entity);
            existsFiles.remove(filePath);
          }
        }
      }

      for (String fileToRemove : existsFiles) {
        System.out.println("Remove: " + fileToRemove);
        md5Repository.delete(md5Repository.findByFileName(fileToRemove));
        fileEntityRepository.delete(fileEntityRepository.findByFilePath(fileToRemove));
      }
      md5Repository.flush();
      fileEntityRepository.flush();
    } catch (IOException e) {
      System.out.println("An error occurred trying to build the serach index: " + e.toString());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

  }

  /**
   * Get all parts of a file
   * 
   * @param path
   * @return
   * @throws IOException
   */
  private List<FilePartEntity> getFileEntity(Path path) throws IOException {

    FilePartEntity FilePartEntity = null;
    String filePath = path.toFile().getName();

    List<String> lines = Files.readAllLines(path);
    List<FilePartEntity> fileEntities = new ArrayList<>();
    StringBuilder stringBuilder = null;
    int part = 0;
    String _line = null;
    for (String line : lines) {
      if (stringBuilder == null) {
        stringBuilder = new StringBuilder();
        part += 1;
      }
      _line = line.trim();
      if (_line.length() > 0) {
        stringBuilder.append(' ').append(_line);
        if (stringBuilder.length() > 2000) {
          FilePartEntity = new FilePartEntity();
          FilePartEntity.setFilePath(filePath);
          FilePartEntity.setPart("" + part);
          FilePartEntity.setEntry(stringBuilder.toString());
          fileEntities.add(FilePartEntity);
          stringBuilder = null;
        }
      }
    }

    return fileEntities;
  }
}
