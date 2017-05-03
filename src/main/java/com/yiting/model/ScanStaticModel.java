package com.yiting.model;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017/4/27.
 */
public class ScanStaticModel {
	private Reflections reflections;
	private Set<Class<?>> configFileClassSet;
	private Set<Method> configFileItemMethodSet;
	private Map<Class<?>,Set<Method>> configFileItemMap;
	private Set<Method> configItemMethodSet;
	private Set<String> justHostFiles;
	private Set<String> reloadableFiles;

	public Reflections getReflections() {
		return reflections;
	}

	public void setReflections(Reflections reflections) {
		this.reflections = reflections;
	}

	public Set<Class<?>> getConfigFileClassSet() {
		return configFileClassSet;
	}

	public void setConfigFileClassSet(Set<Class<?>> configFileClassSet) {
		this.configFileClassSet = configFileClassSet;
	}

	public Set<Method> getConfigFileItemMethodSet() {
		return configFileItemMethodSet;
	}

	public void setConfigFileItemMethodSet(Set<Method> configFileItemMethodSet) {
		this.configFileItemMethodSet = configFileItemMethodSet;
	}

	public Map<Class<?>, Set<Method>> getConfigFileItemMap() {
		return configFileItemMap;
	}

	public void setConfigFileItemMap(Map<Class<?>, Set<Method>> configFileItemMap) {
		this.configFileItemMap = configFileItemMap;
	}

	public Set<Method> getConfigItemMethodSet() {
		return configItemMethodSet;
	}

	public void setConfigItemMethodSet(Set<Method> configItemMethodSet) {
		this.configItemMethodSet = configItemMethodSet;
	}

	public Set<String> getJustHostFiles() {
		return justHostFiles;
	}

	public void setJustHostFiles(Set<String> justHostFiles) {
		this.justHostFiles = justHostFiles;
	}

	public Set<String> getReloadableFiles() {
		return reloadableFiles;
	}

	public void setReloadableFiles(Set<String> reloadableFiles) {
		this.reloadableFiles = reloadableFiles;
	}
}
