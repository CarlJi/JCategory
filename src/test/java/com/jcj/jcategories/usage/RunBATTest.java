package com.jcj.jcategories.usage;

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
