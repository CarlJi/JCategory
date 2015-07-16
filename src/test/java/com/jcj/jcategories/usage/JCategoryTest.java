package com.jcj.jcategories.usage;

import org.junit.runner.RunWith;

import com.jcj.jcategory.JCategory;
import com.jcj.jcategory.JCategory.ClasspathProperty;
import com.jcj.jcategory.JCategory.ExcludeSprint;
import com.jcj.jcategory.JCategory.ExcludeUserStory;
import com.jcj.jcategory.JCategory.IncludeSprint;
import com.jcj.jcategory.JCategory.IncludeUserStory;

@RunWith(JCategory.class)
//@ClasspathProperty("C:\\Dev\\ics-eprintcenter\\trunk\\ukas-tests\\target\\test-classes")
//@ClasspathProperty("cur")
//@IncludeUserStory("US2011")
//@com.jcj.jcategory.JCategory.IncludeDefect("1234")
@ExcludeSprint("15.14")
@ExcludeUserStory("abc")
public class JCategoryTest {

}
