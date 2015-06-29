package com.jcj.jcategories;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

public class AnnotationsFilter extends Filter
{

  List<Filter> filters = null;

  @Override
  public boolean shouldRun(Description description)
  {

    List<Boolean> result = new ArrayList<Boolean>();

    for(Filter filter : filters)
    {
      if(filter != null && filter.shouldRun(description))
      {
        result.add(true);
      }
      else
      {
        result.add(false);
      }
    }

    if(result.contains(false))
    {
      return false;
    }
    else
    {
      return true;
    }
  }

  @Override
  public String describe()
  {
    return "Sprint: " + fAnnotationSprintValue + " UserStory: " + fAnnotationUserStoryValue + " Defect: " + fAnnotationDefectValue;
  }

  private final String fAnnotationSprintValue;
  private final String fAnnotationUserStoryValue;
  private final String fAnnotationDefectValue;

  public AnnotationsFilter(String sprint, String userStory, String defect, Boolean isOnlyRunCaseWithAnnotation)
  {
    fAnnotationSprintValue = sprint;
    fAnnotationUserStoryValue = userStory;
    fAnnotationDefectValue = defect;
    filters = FilterFactory.createFilters(sprint, userStory, defect, isOnlyRunCaseWithAnnotation);
  }

}
