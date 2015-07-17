# JCategory

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

相信的设计思路，可参见我的一篇博客：

[JUnit扩展：引入新注解Annotation](http://www.cnblogs.com/jinsdu/p/4373070.html)

Note：因为代码重构类多次，博客里的代码可能不是最新的。



##如何使用##

当我们把Test Case打上标签之后，我们就可以自由的使用它们了。

- 



