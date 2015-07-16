package com.jcj.jcategory.filters;

import org.junit.runner.Description;
import com.jcj.jcategory.annotations.Defect;

public class FilterExcludedDefect extends JFilter{

	private String value;
	private Defect defect;

	public FilterExcludedDefect(String value)
	{
		this.value = value;
	}

	@Override
	public boolean shouldRun(Description description) {
		if(description.isTest())
		{
			defect = description.getAnnotation(Defect.class);
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
		if(value != null && defect!= null && value.equalsIgnoreCase(defect.value()))
			return false;
		return true;
	}

}
