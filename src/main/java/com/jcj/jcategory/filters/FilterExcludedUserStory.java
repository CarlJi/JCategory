package com.jcj.jcategory.filters;

import org.junit.runner.Description;
import com.jcj.jcategory.annotations.UserStory;

public class FilterExcludedUserStory extends JFilter{
	private String value;
	private UserStory userStory;
	
	public FilterExcludedUserStory(String value)
	{
		this.value = value;
	}

	@Override
	public boolean shouldRun(Description description) {
		if(description.isTest())
		{
			userStory = description.getAnnotation(UserStory.class);
			return filterRule();
		}
		return true;
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean filterRule() {
		if(value != null && userStory != null && value.equalsIgnoreCase(userStory.value()))
			return false;
		return true;
	}

}
