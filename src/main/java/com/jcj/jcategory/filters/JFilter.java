package com.jcj.jcategory.filters;

import org.junit.runner.manipulation.Filter;

public abstract class JFilter extends Filter {

	/**
	 * Define the rule to determine whether run target case or not
	 * 
	 * @return {@link Boolean}
	 */
	public abstract boolean filterRule();
}
