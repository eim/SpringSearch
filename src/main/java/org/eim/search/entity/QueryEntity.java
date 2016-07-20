package org.eim.search.entity;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author eim
 * @since 2016-07-17
 */

@Entity
@Indexed
@Table(name = "query_entity")
//@AnalyzerDef(name = "commonanalyzer",
//    tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
//    filters = {@TokenFilterDef(factory = LowerCaseFilterFactory.class),
//        @TokenFilterDef(factory = StopFilterFactory.class),
//        @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
//            @Parameter(name = "pattern", value = "([^A-Za-z0-9 ]+)"),
//            @Parameter(name = "replacement", value = ""),
//            @Parameter(name = "replace", value = "all")}),
//        @TokenFilterDef(factory = StandardFilterFactory.class),
//        @TokenFilterDef(factory = TrimFilterFactory.class)
//    })
public class QueryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Analyzer(definition = "commonanalyzer")
  @Field(index = Index.YES, norms = Norms.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES)
  @NotNull
  private String value;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}