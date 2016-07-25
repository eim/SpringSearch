package org.eim.search.web;

import static org.eim.search.web.FilePartEntryGenerator.generate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.eim.search.controller.SearchController;
import org.eim.search.entity.FilePartEntity;
import org.eim.search.service.FileMonitoringService;
import org.eim.search.service.IndexingService;
import org.eim.search.service.QuerySearch;
import org.eim.search.service.TermSearch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@RunWith(MockitoJUnitRunner.class)
public class SearchTest {

  @InjectMocks
  private SearchController searchController;

  @Mock
  private TermSearch termSearch;

  @Mock
  private QuerySearch querySearch;

  @Mock
  private FileMonitoringService fileMonitoringService;

  @Mock
  private IndexingService indexingService;


  private MockMvc mockMvc;

  @Before
  public void setup() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/view/");
    viewResolver.setSuffix(".jsp");
    MockitoAnnotations.initMocks(this);
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(searchController).setViewResolvers(viewResolver).build();
  }

  @Test
  public void testSearchTermSuccessful() throws Exception {

    List<FilePartEntity> filePartEntityList = new ArrayList<>();
    for (int i = 0; i < 5; i++)
      filePartEntityList.add(generate(i));

    long currentQueryCount = 10;
    long totalQueryCount = 100;
    float frequencyQuery =
        (totalQueryCount > 0 ? (float) currentQueryCount / totalQueryCount : 0.0f);

    long currentTermCount = 20;
    long totalTermCount = 1000;
    float frequencyTerm = (totalTermCount > 0 ? (float) currentTermCount / totalTermCount : 0.0f);

    when(fileMonitoringService.prepared()).thenReturn(true);
    when(indexingService.getStatus()).thenReturn(false);
    when(querySearch.getTermCount(anyString())).thenReturn((long) 10);
    when(querySearch.getTotalTermCount()).thenReturn((long) 100);
    when(termSearch.getTermCount(anyString())).thenReturn((long) 20);
    when(termSearch.getTotalTermCount()).thenReturn((long) 1000);
    when(termSearch.getEntityList(anyString())).thenReturn(filePartEntityList);

    MvcResult mvcResult =
        mockMvc.perform(get("/search?q=test")).andExpect(status().isOk()).andReturn();

    assertEquals("/WEB-INF/jsp/view/search.jsp", mvcResult.getResponse().getForwardedUrl());
  }

}
