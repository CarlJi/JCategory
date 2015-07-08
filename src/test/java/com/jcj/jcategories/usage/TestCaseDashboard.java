package com.jcj.jcategories.usage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jcj.jcategory.annotations.Sprint;

public class TestCaseDashboard
{

  /**
   * This method will print all test method grouped by Annotations
   * 
   * @param args
   */
  public static void main(String... args)
  {
    printAllSprintTestCases();
    // printAllTests();
  }

  public static void printAllTests()
  {
    TestCaseFinder finder = new TestCaseFinder("itest.com.hp.swp.test");
    Map<String, ArrayList<Method>> map = finder.getMethodsBasedOnAnnotation();
    for(String key : map.keySet())
    {
      System.out.println(key + ": " + map.get(key).size());
    }

  }

  public static void printAllSprintTestCases()
  {
    TestCaseFinder finder = new TestCaseFinder("itest.com.hp.swp.test");
    List<Method> methods = finder.getMethodsBasedWithAnnotation(Sprint.class);

    for(int i = 0; i < methods.size(); i++)
    {
      String value = methods.get(i).getAnnotation(Sprint.class).value();
      System.out.println("Sprint " + value + " for method " + methods.get(i).getName());
    }

  }
}
