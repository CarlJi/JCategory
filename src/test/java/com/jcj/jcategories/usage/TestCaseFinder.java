package com.jcj.jcategories.usage;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCaseFinder
{
  private List<Class<?>> testClasses = null;

  public TestCaseFinder(String packageName)
  {
    try
    {
      if(testClasses != null)
      {
        testClasses.clear();
      }
      testClasses = (ArrayList<Class<?>>)getClassesFromPkg(packageName);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public List<Class<?>> getAllTestClasses()
  {
    return testClasses;
  }

  public List<Method> getMethodsBasedWithAnnotation(Class<? extends Annotation> ac)
  {
    List<Method> methodLists = new ArrayList<Method>();
    for(Class<?> aClass : testClasses)
    {
      for(Method m : aClass.getDeclaredMethods())
      {
        Annotation aAnno = m.getAnnotation(ac);
        if(aAnno != null)
          methodLists.add(m);
      }
    }
    return methodLists;
  }

  /**
   * Group all methods based on the annotation
   * 
   * @return The maps between annotation and methods
   */
  public Map<String, ArrayList<Method>> getMethodsBasedOnAnnotation()
  {
    Map<String, ArrayList<Method>> annotationMethodMaps = new HashMap<String, ArrayList<Method>>();

    for(Class<?> aClass : testClasses)
    {
      Method[] allMethods = null;
      try
      {
        allMethods = aClass.getDeclaredMethods();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }

      for(Method method : allMethods)
      {
        addAnnotationIntoMap(annotationMethodMaps, method);
      }
    }

    return annotationMethodMaps;
  }

  private void addAnnotationIntoMap(Map<String, ArrayList<Method>> map, Method method)
  {
    ArrayList<Method> methods = new ArrayList<Method>();
    Annotation[] annotations = method.getAnnotations();
    for(Annotation anno : annotations)
    {
      String annoName = anno.annotationType().getName();
      if(map.containsKey(annoName))
      {
        methods = map.get(annoName);
      }

      methods.add(method);
      map.put(annoName, methods);
    }

  }

  /**
   * Scans all classes accessible from the context class loader which belong
   * to the given package and subpackages.
   * 
   * @param packageName
   *          The base package
   * @return The classes
   * @throws ClassNotFoundException
   * @throws IOException
   */
  private Iterable<Class<?>> getClassesFromPkg(String packageName) throws ClassNotFoundException, IOException
  {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String path = packageName.replace('.', '/');
    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<File>();
    while(resources.hasMoreElements())
    {
      URL resource = resources.nextElement();
      dirs.add(new File(resource.getFile()));
    }
    List<Class<?>> classes = new ArrayList<Class<?>>();
    for(File directory : dirs)
    {
      classes.addAll(findClasses(directory, packageName, classLoader));
    }

    return classes;
  }

  /**
   * Recursive method used to find all classes in a given directory and
   * subdirs.
   * 
   * @param directory
   *          The base directory
   * @param packageName
   *          The package name for classes found inside the base directory
   * @return The classes
   * @throws ClassNotFoundException
   */
  private List<Class<?>> findClasses(File directory, String packageName, ClassLoader loader)
  {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    Class<?> instance = null;
    if(!directory.exists())
    {
      return classes;
    }
    File[] files = directory.listFiles();
    for(File file : files)
    {
      if(file.isDirectory())
      {
        classes.addAll(findClasses(file, packageName + "." + file.getName(), loader));
      }
      else if(file.getName().endsWith(".class"))
      {
        try
        {
          instance = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));

          // instance =file.getName();
          classes.add(instance);
        }
        catch(ExceptionInInitializerError ex)
        {
          System.out.println("Unable to innitizlize the class: " + packageName + "." + file.getName());
          ex.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
          // ignore exception
        }
        catch(NoClassDefFoundError ex)
        {
          // ignore exception
        }
      }
    }
    return classes;
  }
}
