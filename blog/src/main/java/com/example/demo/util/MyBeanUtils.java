package com.example.demo.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class MyBeanUtils {
	
	//獲取所有屬性質為空的array
	public static String[] getNullPropertyNames(Object source) {
		
		BeanWrapper beanwrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = beanwrapper.getPropertyDescriptors();
		List<String> nullPropertyNames = new ArrayList<String>();
		for(PropertyDescriptor pd: pds) {
			String propertyName = pd.getName();
			if(beanwrapper.getPropertyValue(propertyName) == null) {
				nullPropertyNames.add(propertyName);
			}
		}
		return nullPropertyNames.toArray(new String [nullPropertyNames.size()]);
		
	}

}
