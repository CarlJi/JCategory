# English | [中文](Chinese.md) #

----------

# JCategory #


This tool is a junit4 extension tool, which can help you run your test cases more flexible, 

From technological level, this is a custom junit4 runner, which is more like `Categories`, but have done some modifications and adjustments:

- Automatically find all the test cases. We know that when we use `Categories` , we must use `SuiteClasses` to collect how many case we want to run manually. While `JCategory` has helped you do this part automatically(Refer:[ClasspathSuite](https://github.com/takari/takari-cpsuite)).
- Define all kinds of annotations which can be suitable for the test scenarios on agile/scrum mode or with a mass of test case.  

Currently, it defined the following new annotations:

1. **Sprint** - Mark which sprint the test case or feature was invovled
2. **UserStory** - Mark which user story the case was tested or targeted. The benefit of this annotation can help us easily understand which feature the case was tested even after a long time
3. **Defect** - If this is a regression case which cover a bug or there is a bug which would cause thiscase failed, we can use **Defect** annotation to mark it. 

So with all these annotations, we can develop our test case as this:
    
    @Test
	@Sprint("15.13")
	@UserStory("US2011")
	@Defect("1234")
	public void testJCategory()
	{
		// Case Logic
	}

 
### How to use it ? ###

After we marked our test cases with expected annotations, we can use `JCategory` to run test cases more flexible.
We just need create a class, then use `RunWith(JCategory.class)` to decorate it, same with the usage of junit `Categories`.

    import org.junit.runner.RunWith;
    import com.jcj.jcategory.JCategory;

    @RunWith(JCategory.class)
    public class AllTestSuite
    {
    }  

By default, it will run all the test cases.

 
### Integrate with Junit `Categories` ###

`JCategory` also can work with junit `Categoryies` in order to collect the test case automaticallly.
For example, if we only want to run the case was marked as BAT, we can do it like this:

    import org.junit.experimental.categories.Categories;
	import org.junit.experimental.categories.Categories.IncludeCategory;
	import org.junit.runner.RunWith;
	import org.junit.runners.Suite.SuiteClasses;
	import com.jcj.jcategory.annotations.BAT;
	
	@RunWith(Categories.class)
	@IncludeCategory(BAT.class)
	@SuiteClasses(AllTestSuite.class)
	public class RunBATTest
	{
	}

`AllTestSuite` is just the class we used in the previous example.


### Include the case marked with the annotations: Sprint，UserStor or Defect ###

For example, we only want to run the test case under sprint 15.14:

    import org.junit.runner.RunWith;
	import com.jcj.jcategory.JCategory;
	import com.jcj.jcategory.JCategory.IncludeSprint;
	
	@RunWith(JCategory.class)
	@IncludeSprint("15.14")
	public class JCategoryTest {
	}

Or, we want to run the test cases belong to User Story US40251 under sprint 15.14:

    @RunWith(JCategory.class)
	@IncludeSprint("15.14")
	@IncludeUserStory("US40251")
	public class JCategoryTest {
	}

Or only execute the regression tests for defect 2015:

    @RunWith(JCategory.class)
	@IncludeDefect("2015")
	public class JCategoryTest {
	}

### Exclude the case marked with the annotations: Sprint，UserStor or Defect ###
Same with junit `Categories`, we also can exclude the test cases that we do not want to run.

For example, not excute the test cases belong to sprint 15.14:

    @RunWith(JCategory.class)
	@ExcludeSprint("15.15")
	public class JCategoryTest {
	}

Or not execute the test cases belong to user story 40255:

    @RunWith(JCategory.class)
	@ExcludeUserStory("US40255")
	public class JCategoryTest {
	}



### Contact me ? ###

Email: **jinsdu@outlook.com** 

Blog: <http://www.cnblogs.com/jinsdu/>
