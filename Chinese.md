# [English](README.md) | 中文 #

----------

# JCategory #

这是一款Junit4的拓展工具，目的是让我们能够灵活的选择要执行哪些Test Case。

从技术上讲，这是一个定制化的Junit4的Runner，灵感来源于原生的Categories，但是拓展了两个功能：

- 自动查找要跑的Cases。再使用原生的Categories时，我们必须传入SuiteClasses，如果要是Case文件很多的话，你就得一个一个把他们手动添加上。而JCategory会自动帮你完成这一步。
- 自定义了更丰富的标记Case的注解，以适应海量自动化Case的Filter，以及更贴切敏捷模式下的Case模式。
 
主要引入了下面几个新注解：
  
1. Sprint--用来标记这条Case是在什么时候引进的，或者说这条Case是哪个Sprint的加入的功能	            
2. UserStory--用来标记这条Case测试的是哪个UserStory，或者说覆盖的那个UserStory，这样即使过了很长时间，我们也能清晰地知道，我们为什么加这条Case，以及它到底测的是什么功能 	             
3. Defect--用来标记这条Case覆盖的Defect，或者说覆盖的Defect	  

所以我们在写Junit4 的Test Case时就可以这么用：

    
    @Test
	@Sprint("15.13")
	@UserStory("US2011")
	@Defect("1234")
	public void testJCategory()
	{
		// Case Logic
	}

详细的设计思路，可参见我的一篇博客：

[JUnit扩展：引入新注解Annotation](http://www.cnblogs.com/jinsdu/p/4373070.html)

Note：因为代码重构类多次，博客里的代码可能不是最新的。



#如何使用#

当我们把Test Case打上标签之后，我们就可以自由的使用它们了。使用也很简单，我们只需要创建一个Class，然后用JCategory修饰这个类就，跟Junit4 的Categories使用非常像，比如下面：

    import org.junit.runner.RunWith;
    import com.jcj.jcategory.JCategory;

    @RunWith(JCategory.class)
    public class AllTestSuite
    {
    }

默认执行所有的Test Cases.

##与Categories结合##

因为JCategory继承自suite，所有它也能与Categories结合使用，达到代替suiteClass，自动收集Test Class的功能。
比如我们想只执行BAT的Test Cases，我们就可以结合上面的AllTestSuite类，这么用：

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

##Include选择自定义的注解：Sprint，UserStory，Defect##

比如我想只执行Sprint15.14的Test Cases:

    import org.junit.runner.RunWith;
	import com.jcj.jcategory.JCategory;
	import com.jcj.jcategory.JCategory.IncludeSprint;
	
	@RunWith(JCategory.class)
	@IncludeSprint("15.14")
	public class JCategoryTest {
	}

或者只测Sprint15.14的UserStory US40251:

    @RunWith(JCategory.class)
	@IncludeSprint("15.14")
	@IncludeUserStory("US40251")
	public class JCategoryTest {
	}

或者做Defect 2015的Regression Tests：

    @RunWith(JCategory.class)
	@IncludeDefect("2015")
	public class JCategoryTest {
	}


##Exclude不希望执行的Test Cases##
类似于Junit4的Categories，我们也可以Exclude掉不期望的执行的Case。
比如：不执行所有属于Sprint15.15的Test Cases：

    @RunWith(JCategory.class)
	@ExcludeSprint("15.15")
	public class JCategoryTest {
	}

不执行所有属于UserStory 40255的Test Cases:

    @RunWith(JCategory.class)
	@ExcludeUserStory("US40255")
	public class JCategoryTest {
	}


## Contact me ? ##

Email: **jinsdu@outlook.com** 

Blog: <http://www.cnblogs.com/jinsdu/>