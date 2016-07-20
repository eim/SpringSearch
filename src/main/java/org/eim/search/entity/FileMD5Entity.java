package org.eim.search.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "file_md5")
public class FileMD5Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field(store = Store.YES, index = Index.YES)
    @NotNull
    private String fileName;

    @Field(store = Store.YES, index = Index.YES)
    private String md5;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getFileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

    public String getMd5() {
      return md5;
    }

    public void setMd5(String md5) {
      this.md5 = md5;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("FileMD5{");
      sb.append("id=").append(id);
      sb.append(", fileName='").append(fileName).append('\'');
      sb.append(", md5='").append(md5).append('\'');
      sb.append('}');
      return sb.toString();
    }
  }

