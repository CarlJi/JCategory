package com.jcj.jcategory;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import com.jcj.jcategory.annotations.Defect;

public class FilterDefect extends Filter
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

  public FilterDefect(String tgValue)
  {
    this.tgValue = tgValue;
  }

  /**
   * if tgValue == "", then execute all test case with Defect annotation
   * if tgValue != null && tgValue != "", execute test case whose Defect annotation value == tgValue
   */
  @Override
  public boolean shouldRun(Description description)
  {

    if(tgValue != null && description.isTest())
    {
      Defect defect = description.getAnnotation(Defect.class);

      if(defect == null || (!tgValue.equalsIgnoreCase("") && !defect.CR().equalsIgnoreCase(tgValue)))
      {
        return false;
      }
    }

    return true;

  }

  @Override
  public String describe()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
