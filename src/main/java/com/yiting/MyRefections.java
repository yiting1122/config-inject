package com.yiting;

import com.yiting.annotation.ConfigFile;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by admin on 2017/4/27.
 */
public class MyRefections {



	public static void main(String[] args){
		Reflections reflections=new Reflections("com.yiting");
		Set<String> allTypes=reflections.getAllTypes();
		for(String name:allTypes){
			System.out.println(name);
		}
		Set<Class<?>> classSet=reflections.getTypesAnnotatedWith(ConfigFile.class);

		for(Class cls:classSet){

		}


	}


}
