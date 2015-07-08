package com.jcj.jcategory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

public class ClassChecker implements Checker
{

  private final SuiteType[] suiteTypes;

  public ClassChecker(SuiteType[] types)
  {
    this.suiteTypes = types;
  }

  public boolean checkName(String name)
  {
    // TODO: Filter some class based on its name here
    return true;
  }

  public boolean checkClass(Class<?> aClass)
  {
    if(isInSuiteTypes(SuiteType.TEST_CLASSES))
    {
      return acceptTestClass(aClass);
    }

    if(isInSuiteTypes(SuiteType.JUNIT38_TEST_CLASSES))
    {
      return acceptJUnit38Test(aClass);
    }

    if(isInSuiteTypes(SuiteType.RUN_WITH_CLASSES))
    {
      return acceptRunWithClass(aClass);
    }
    return false;
  }

  private boolean isInSuiteTypes(SuiteType oneType)
  {
    return Arrays.asList(suiteTypes).contains(oneType);
  }

  private boolean acceptTestClass(Class<?> aClass)
  {
    if(isAbstractClass(aClass))
    {
      return false;
    }
    try
    {
      for(Method method : aClass.getMethods())
      {
        if(method.getAnnotation(Test.class) != null)
        {
          return true;
        }
      }
    }
    catch(NoClassDefFoundError ex)
    {
    }
    return false;
  }

  private boolean acceptJUnit38Test(Class<?> aClass)
  {
    if(isAbstractClass(aClass))
    {
      return false;
    }

    return TestCase.class.isAssignableFrom(aClass);
  }

  private boolean acceptRunWithClass(Class<?> aClass)
  {
    return aClass.isAnnotationPresent(RunWith.class);
  }

  private boolean isAbstractClass(Class<?> aClass)
  {
    return (aClass.getModifiers() & Modifier.ABSTRACT) != 0;
  }
}
