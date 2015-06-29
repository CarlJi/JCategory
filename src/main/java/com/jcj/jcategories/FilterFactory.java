package com.jcj.jcategories;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.manipulation.Filter;

public class FilterFactory
{
  public static List<Filter> filters = new ArrayList<Filter>();

  public static List<Filter> getFilters()
  {
    return filters;
  }

  private static FilterSprint fSprint = null;
  private static FilterUserStory fUserStory = null;
  private static FilterDefect fDefect = null;

  public static List<Filter> createFilters(String sprint, String userStory, String defect, Boolean isOnlyRunCaseWithSprintAnnotation)
  {
    if(sprint != null)
    {
      fSprint = new FilterSprint(sprint, isOnlyRunCaseWithSprintAnnotation);
      filters.add(fSprint);
    }

    if(userStory != null)
    {
      fUserStory = new FilterUserStory(userStory);
      filters.add(fUserStory);
    }

    if(defect != null)
    {
      fDefect = new FilterDefect(defect);
      filters.add(fDefect);
    }

    return filters;
  }
}
