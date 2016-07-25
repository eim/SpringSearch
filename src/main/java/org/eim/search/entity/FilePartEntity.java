package org.eim.search.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Norms;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Indexed
@Entity
@AnalyzerDef(name = "commonanalyzer",
    tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
    filters = {@TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = StopFilterFactory.class),
        @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
            @Parameter(name = "pattern", value = "([^A-Za-z0-9 ]+)"),
            @Parameter(name = "replacement", value = ""),
            @Parameter(name = "replace", value = "all")}),
        @TokenFilterDef(factory = StandardFilterFactory.class),
        @TokenFilterDef(factory = TrimFilterFactory.class)
    })
@Table(name = "file_part")
public class FilePartEntity {


  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;

  @Field(index = Index.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES)
  @Analyzer(definition = "commonanalyzer")
  private String filePath;

  @Field(index = Index.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES)
  private String part;

  @Lob
  @Column(length = 2000)
  @Analyzer(definition = "commonanalyzer")
  @Field(index = Index.YES, norms = Norms.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES)
  private String entry;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public String getEntry() {
    return entry;
  }

  public void setEntry(String entry) {
    this.entry = entry;
  }
}
