package com.jcj.jcategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * 
 * Custom suite to run test case flexibility
 * 
 * @author Carl Ji
 *
 */
public class JCategory extends Suite
{
  private static final String DEFAULT_CLASSPATH_PROPERTY = "java.class.path";
  private static final SuiteType[] DEFAULT_SUITE_TYPES = new SuiteType[] { SuiteType.TEST_CLASSES };

  /**
   * The <code>ClasspathProperty</code> specifies the System property name
   * used to retrieve the java classpath which is searched for Test classes
   * and suites. Default is "java.class.path".
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface ClasspathProperty
  {
    String value();
  }

  /**
   * The <code>SuiteTypes</code> annotation specifies which types of tests
   * will be included in the test run. You can choose one or more from
   * TEST_CLASSES, RUN_WITH_CLASSES, JUNIT38_TEST_CLASSES. If none is
   * specified only JUnit4-style TEST_CLASSES will be run.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface SuiteTypes
  {
    public SuiteType[] value();
  }

  /**
   * Specify which sprints that tests were marked with will be included
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface IncludeSprint
  {
    String value();

    /*
     * This annotation will determine we want to run case without Sprint annotation
     * If not set, it is false
     */
    boolean isOnly() default false;
  }

  /**
   * Exclude which sprint that the tests were marked with will be excluded
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ExcludeSprint
  {
    String value();
  }

  /**
   * Specify which UserStory that the tests were marked with will be included
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface IncludeUserStory
  {
    String value();
  }

  /**
   * Exclude which UserStory that the tests were marked with will be excluded
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ExcludeUserStory
  {
    String value();
  }

  /**
   * Specify which UserStory that the tests were marked with will be included
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface IncludeDefect
  {
    String value();
  }

  /**
   * Specify which UserStory that the tests were marked with will be excluded
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ExcludeDefect
  {
    String value();
  }

  /**
   * Used by JUnit
   */
  public JCategory(Class<?> suiteClass, RunnerBuilder builder) throws InitializationError
  {
    super(builder, suiteClass, getTestclasses(new ClasspathClassesFinder(getClasspathProperty(suiteClass), new ClassChecker(
        getSuiteType(suiteClass))).find()));
    try
    {
      filter(new AnnotationsFilter(getIncludedSprint(suiteClass), getIncludedUserStory(suiteClass), getIncludedDefect(suiteClass),
          IsOnlyRunCaseWithSprintAnnotation(suiteClass)));
    }
    catch(NoTestsRemainException e)
    {
      throw new InitializationError(e);
    }

    assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
  }

  private static Class<?>[] getTestclasses(List<Class<?>> testclasses)
  {
    return testclasses.toArray(new Class[testclasses.size()]);
  }

  private boolean IsOnlyRunCaseWithSprintAnnotation(Class<?> classes)
  {
    IncludeSprint annotation = classes.getAnnotation(IncludeSprint.class);
    return annotation == null ? false : annotation.isOnly();
  }

  private String getIncludedSprint(Class<?> classes)
  {
    IncludeSprint annotation = classes.getAnnotation(IncludeSprint.class);
    return annotation == null ? null : annotation.value();
  }

  private String getIncludedUserStory(Class<?> classes)
  {
    IncludeUserStory annotation = classes.getAnnotation(IncludeUserStory.class);
    return annotation == null ? null : annotation.value();

  }

  private String getIncludedDefect(Class<?> classes)
  {
    IncludeDefect annotation = classes.getAnnotation(IncludeDefect.class);
    return annotation == null ? null : annotation.value();
  }

  private void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description) throws InitializationError
  {
    if(!canHaveCategorizedChildren(description))
    {
      assertNoDescendantsHaveCategoryAnnotations(description);
    }
    for(Description each : description.getChildren())
    {
      assertNoCategorizedDescendentsOfUncategorizeableParents(each);
    }
  }

  private void assertNoDescendantsHaveCategoryAnnotations(Description description) throws InitializationError
  {
    for(Description each : description.getChildren())
    {
      if(each.getAnnotation(Category.class) != null)
      {
        throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
      }
      assertNoDescendantsHaveCategoryAnnotations(each);
    }
  }

  // If children have names like [0], our current magical category code can't determine their
  // parentage.
  private static boolean canHaveCategorizedChildren(Description description)
  {
    for(Description each : description.getChildren())
    {
      if(each.getTestClass() == null)
      {
        return false;
      }
    }
    return true;
  }

  private static String getClasspathProperty(Class<?> suiteClass)
  {
    ClasspathProperty cpPropertyAnnotation = suiteClass.getAnnotation(ClasspathProperty.class);
    if(cpPropertyAnnotation == null)
    {
      return DEFAULT_CLASSPATH_PROPERTY;
    }
    return cpPropertyAnnotation.value();
  }

  private static SuiteType[] getSuiteType(Class<?> aClass)
  {
    SuiteTypes suiteType = aClass.getAnnotation(SuiteTypes.class);
    if(suiteType == null)
    {
      return DEFAULT_SUITE_TYPES;
    }

    return suiteType.value();
  }

}
