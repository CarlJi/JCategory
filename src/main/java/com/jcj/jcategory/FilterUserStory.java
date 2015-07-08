package com.jcj.jcategory;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import com.jcj.jcategory.annotations.UserStory;

public class FilterUserStory extends Filter
{
  private String tgValue = null;

  public String getTgValue()
  {
    return tgValue;
  }

  public void setTgValue(String tgValue)
  {
    this.tgValue = tgValue;
  }

  public FilterUserStory(String tgValue)
  {
    this.tgValue = tgValue;
  }

  @Override
  public String describe()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean shouldRun(Description description)
  {
    if(tgValue != null && description.isTest())
    {
      UserStory us = description.getAnnotation(UserStory.class);

      return(us != null && us.value().equalsIgnoreCase(tgValue));
    }

    return true;
  }
}
