package com.jcj.jcategory;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import com.jcj.jcategory.filters.FilterDefect;
import com.jcj.jcategory.filters.FilterExcludedDefect;
import com.jcj.jcategory.filters.FilterExcludedSprint;
import com.jcj.jcategory.filters.FilterExcludedUserStory;
import com.jcj.jcategory.filters.FilterSprint;
import com.jcj.jcategory.filters.FilterUserStory;

public class AnnotationsFilterBuilder extends Filter
{
  List<Filter> filters = new ArrayList<Filter>();

  @Override
  public boolean shouldRun(Description description)
  {
    for(Filter filter : filters)
    {
      if(filter != null && !filter.shouldRun(description))
      {
    	  return false;
      }
    }

    return true;
  }

  @Override
  public String describe()
  {
    return null;
  }

  private final String sprint;
  private final String userStory;
  private final String defect;
  private final String excludedSprint;
  private final String excludedUserStory;
  private final String excludedDefect;

  public AnnotationsFilterBuilder(String sprint, String userStory, String defect, String excludedSprint, String excludedUserStory, String excludedDefect)
  {
    this.sprint = sprint;
    this.userStory = userStory;
    this.defect = defect;
    this.excludedSprint = excludedSprint;
    this.excludedUserStory = excludedUserStory;
    this.excludedDefect = excludedDefect;
    
    createFilters();
  }
  
  private void createFilters()
  {
	  filters.add(new FilterSprint(sprint));
	  filters.add(new FilterUserStory(userStory));
	  filters.add(new FilterDefect(defect));
	  filters.add(new FilterExcludedDefect(excludedDefect));
	  filters.add(new FilterExcludedSprint(excludedSprint));
	  filters.add(new FilterExcludedUserStory(excludedUserStory));
  }
  
  

}
