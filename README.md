# English | [Chinese](Chinese.md) #

----------

# JCategory #


Please note this project has reference for [ClasspathSuite](https://github.com/takari/takari-cpsuite), which was used to find Test class.


### Project Goal ###

When we test a big project, we may have more and more test cases, then a problems arise: we may need a large of time to run all of test cases one time, and even we use TestSuite to orginaze our test cases, it still seems inconcenience, since we need add test case one by one.

To run cases based on Package/Class structure or different categories is a good choice, but what's more, thinking can we have another strategory to filter our cases more related with our requirements? Can we run test cases more specific for Scrum testing?

OK, here is the solution!
 
### How to use it ? ###

Add the defined annotations to mark the test case as needed like below:

```java
import org.junit.Test;
import org.junit.extensions.cpsuite.annotations.Defect;
import org.junit.extensions.cpsuite.annotations.Sprint;
import org.junit.extensions.cpsuite.annotations.UserStory;

public class ExampleCase
{
  @Test
  @Sprint("15.8")
  @UserStory("US45867")
  @Defect(CR = "12354", Title = "Unable update user name")
  public void test_PAMGetMiramInvitationResource_201_InviteOneUser()
  {
  }
}
```

The is no any influence for your other test cases.

 
### How to filter these cases ? ###

Based on the annotations you add above, now you have many options to run your test cases if you want.

- **You can only run Sprint 15.8 Test cases:**
  ```java
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeSprint;
import org.junit.runner.RunWith;

@RunWith(ClasspathSuite.class)
@IncludeSprint(value = "15.8", isOnly = true)
public class JUnitAnnotationExtensionExample
{
}
```

- **You can only run the test cases belong to US45867 in Sprint 15.8:**
  ```java
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeSprint;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeUserStory;
import org.junit.runner.RunWith;

@RunWith(ClasspathSuite.class)
@IncludeSprint(value = "15.8", isOnly = true)
@IncludeUserStory("US45867")
public class JUnitAnnotationExtensionExample
{
}
```

- **You can only run all test cases which cover a defect to do full regression testing:**
  ```java
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeDefect;
import org.junit.runner.RunWith;

@RunWith(ClasspathSuite.class)
@IncludeDefect("")
public class JUnitAnnotationExtensionExample
{
}
```

### Contact me ? ###

Email: **jinsdu@outlook.com** 

Blog: <http://www.cnblogs.com/jinsdu/>
