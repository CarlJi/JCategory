package com.jcj.jcategory;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import com.jcj.jcategory.annotations.Sprint;

/**
 * Filter rules for the annotation {@link Sprint}
 * 
 * @author Carl Ji
 *
 */
public class FilterSprint extends Filter
{
  private String tgValue = null;
  private Boolean _isOnly = false;

  public FilterSprint(String targetValue)
  {
    setTgValue(targetValue);
  }

  public FilterSprint(String targetValue, Boolean isOnly)
  {
    setTgValue(targetValue);
    _isOnly = isOnly;
  }

  public Boolean getIsOnly()
  {
    return _isOnly;
  }

  public void setIsOnly(Boolean isOnly)
  {
    this._isOnly = isOnly;
  }

  public String getTgValue()
  {
    return tgValue;
  }

  public void setTgValue(String tgValue)
  {
    this.tgValue = tgValue;
  }

  @Override
  public boolean shouldRun(Description description)
  {
    if(description.isTest())
    {
      Sprint aSprint = description.getAnnotation(Sprint.class);
      return filterRule(aSprint);
    }
    else
    {
      return true;
    }
  }

  @Override
  public String describe()
  {
    // TODO Auto-generated method stub
    return null;
  }

  // Implement of filter rule for Sprint Annotation
  private boolean filterRule(Sprint aSprint)
  {
    if(_isOnly)
    {
      if(aSprint != null && aSprint.value().equalsIgnoreCase(tgValue))
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      if(aSprint == null)
      {
        return true;
      }
      else
      {
        if(0 >= new StringComparator().compare(aSprint.value(), tgValue))
        {
          return true;
        }
      }
    }

    return false;

  }
}
