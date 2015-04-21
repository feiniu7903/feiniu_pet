/**
 * 
 */
package com.lvmama.comm.utils.json;

import java.util.HashSet;
import java.util.Set;

import net.sf.json.util.PropertyFilter;

import org.springframework.util.Assert;

/**
 * 针对jsonconfig当中使用，主要是使
 * @author yangbin
 *
 */
public class FieldPropertyFilter implements PropertyFilter {
	
	private Set<String> fields=new HashSet<String>();
	
	public FieldPropertyFilter(Set<String> fields){
		Assert.notNull(fields);
		this.fields=fields;
	}
	
	public FieldPropertyFilter(String...field){
		Assert.notNull(field);
		for(String f:field){
			fields.add(f);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.json.util.PropertyFilter#apply(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean apply(Object arg0, String arg1, Object arg2) {
		return (fields.contains(arg1));
	}

}
