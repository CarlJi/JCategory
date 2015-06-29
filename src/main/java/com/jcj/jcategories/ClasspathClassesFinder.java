package com.jcj.jcategories;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class to find classes within the class path, both inside and outside
 * of jar files. Inner and anonymous classes are not being considered in the
 * first place.
 * 
 * It's originally evolved out of ClassPathTestCollector in JUnit 3.8.1
 */
public class ClasspathClassesFinder
{
  private static final int CLASS_SUFFIX_LENGTH = ".class".length();
  private static final String FALLBACK_CLASSPATH_PROPERTY = "java.class.path";

  private final String classpathProperty;
  private final Checker checker;

  public ClasspathClassesFinder(String classpathProperty, Checker checker)
  {
    this.classpathProperty = classpathProperty;
    this.checker = checker;
  }

  public List<Class<?>> find()
  {
    return findClassesInClasspath(getClasspath());
  }

  private String getClasspath()
  {
    String classPath = System.getProperty(getClasspathProperty());
    if(classPath == null)
      classPath = System.getProperty(FALLBACK_CLASSPATH_PROPERTY);
    return classPath;
  }

  private List<Class<?>> findClassesInClasspath(String classPath)
  {
    return findClassesInRoots(splitClassPath(classPath));
  }

  private List<Class<?>> findClassesInRoots(List<String> roots)
  {
    List<Class<?>> classes = new ArrayList<Class<?>>(100);
    for(String root : roots)
    {
      gatherClassesInRoot(new File(root), classes);
    }
    return classes;
  }

  private void gatherClassesInRoot(File classRoot, List<Class<?>> classes)
  {
    Iterable<String> relativeFilenames = new NullIterator<String>();
    if(classRoot.isDirectory())
    {
      relativeFilenames = new RecursiveFilenameIterator(classRoot);
    }
    gatherClasses(classes, relativeFilenames);
  }

  private void gatherClasses(List<Class<?>> classes, Iterable<String> filenamesIterator)
  {
    for(String fileName : filenamesIterator)
    {
      if(!isClassFile(fileName))
      {
        continue;
      }
      String className = classNameFromFile(fileName);
      if(!checker.checkName(className))
      {
        continue;
      }
      try
      {
        Class<?> clazz = Class.forName(className);
        if(clazz == null || clazz.isLocalClass() || clazz.isAnonymousClass())
        {
          continue;
        }
        if(checker.checkClass(clazz))
        {
          classes.add(clazz);
        }
      }
      catch(ClassNotFoundException cnfe)
      {
        // ignore not instantiable classes
      }
      catch(NoClassDefFoundError ncdfe)
      {
        // ignore not instantiable classes
      }
      catch(ExceptionInInitializerError ex)
      {
        // ex.printStackTrace();
      }
      catch(UnsatisfiedLinkError ule)
      {
        // ignore not instantiable classes
      }
    }
  }

  private boolean isClassFile(String classFileName)
  {
    return classFileName.endsWith(".class");
  }

  private List<String> splitClassPath(String classPath)
  {
    final String separator = System.getProperty("path.separator");
    return Arrays.asList(classPath.split(separator));
  }

  private String classNameFromFile(String classFileName)
  {
    // convert /a/b.class to a.b
    String s = replaceFileSeparators(cutOffExtension(classFileName));
    if(s.startsWith("."))
      return s.substring(1);
    return s;
  }

  private String replaceFileSeparators(String s)
  {
    String result = s.replace(File.separatorChar, '.');
    if(File.separatorChar != '/')
    {
      // In Jar-Files it's always '/'
      result = result.replace('/', '.');
    }
    return result;
  }

  private String cutOffExtension(String classFileName)
  {
    return classFileName.substring(0, classFileName.length() - CLASS_SUFFIX_LENGTH);
  }

  public String getClasspathProperty()
  {
    return classpathProperty;
  }
}
