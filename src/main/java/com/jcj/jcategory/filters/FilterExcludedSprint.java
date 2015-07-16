package com.jcj.jcategory.filters;

import org.junit.runner.Description;
import com.jcj.jcategory.annotations.Sprint;

public class FilterExcludedSprint extends JFilter{

	private String value;
	private Sprint sprint;

	public FilterExcludedSprint(String value)
	{
		this.value = value;
	}

	@Override
	public boolean shouldRun(Description description) {
		if(description.isTest())
		{
			sprint = description.getAnnotation(Sprint.class);
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
		if(value != null && sprint!=null && value.equalsIgnoreCase(sprint.value()))
			return false;
		return true;
	}

}
