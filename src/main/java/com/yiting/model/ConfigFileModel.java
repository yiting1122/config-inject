package com.yiting.model;

import com.yiting.annotation.ConfigFileItem;
import com.yiting.config.ConfigTypeEnum;
import com.yiting.util.ClassUtils;
import com.yiting.annotation.ConfigFile;
import com.yiting.util.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017/4/27.
 */
public class ConfigFileModel extends ConfigBaseModel {
	private Map<String, FileItemValue> fileItemValueMap = new HashMap<String, FileItemValue>();
	private String configFileName;
	private String copyToPath;
	private Class<?> cls;

	public Map<String, FileItemValue> getFileItemValueMap() {
		return fileItemValueMap;
	}

	public void setFileItemValueMap(Map<String, FileItemValue> fileItemValueMap) {
		this.fileItemValueMap = fileItemValueMap;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public String getCopyToPath() {
		return copyToPath;
	}

	public void setCopyToPath(String copyToPath) {
		this.copyToPath = copyToPath;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public Map<String, Object> getKV() {

		assert fileItemValueMap != null;
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : fileItemValueMap.keySet()) {
			map.put(key, fileItemValueMap.get(key).getValue());
		}

		return map;
	}

	public static ConfigFileModel transfer(Class<?> cls, Set<Method> methods) {
		ConfigFileModel configFileModel = new ConfigFileModel();
		ConfigFile configFile = cls.getAnnotation(ConfigFile.class);
		if (configFile != null) {
			configFileModel.setCls(cls);
			configFileModel.setConfigFileName(configFile.fileName());

			Map<String, FileItemValue> keyMaps = new HashMap<String, FileItemValue>();
			Field[] expectFileds = cls.getDeclaredFields();

			for (Method method : methods) {
				Field field = MethodUtils.getFieldFromMethod(method, expectFileds, ConfigTypeEnum.FILE);
				if (field == null) {
					continue;
				}
				ConfigFileItem configFileItem = method.getAnnotation(ConfigFileItem.class);
				String keyName = configFileItem.name();
				field.setAccessible(true);

				Method setterMethod = MethodUtils.getSetterMethodFromField(cls, field);

				if (Modifier.isStatic(field.getModifiers())) {
					try {
						FileItemValue fileItemValue=new FileItemValue(field.get(null),field,setterMethod);
						keyMaps.put(keyName,fileItemValue);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else {
					FileItemValue fileItemValue=new FileItemValue(null,field,setterMethod);
					keyMaps.put(keyName,fileItemValue);
				}

			}

			configFileModel.setFileItemValueMap(keyMaps);

		}
		return configFileModel;

	}


	public static class FileItemValue {
		private Object value;
		private Field field;
		private Method setMethod;

		public FileItemValue(Object value, Field field, Method setMethod) {
			this.value = value;
			this.field = field;
			this.setMethod = setMethod;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}


		public void setField(Field field) {
			this.field = field;
		}


		public boolean isStatic() {
			return Modifier.isStatic(field.getModifiers());
		}

		public Object setValueForStaticFileItem(Object val) {
			if (isStatic()) {
				try {
					if (setMethod != null) {
						setMethod.invoke(null, val);
					} else {
						field.set(null, val);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("this filed is not static,filed:" + field.toString());
			}

			return val;
		}

		public Object setValueForFileItem(Object obj, Object val) {
			if (!isStatic()) {
				try {
					if (setMethod != null) {
						setMethod.invoke(obj, val);
					} else {
						field.set(obj, val);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return obj;
		}

		public Object getFieldValueByType(Object fieldValue) throws Exception {
			return ClassUtils.getValeByType(field.getType(), fieldValue);
		}

		public Object getFieldDefaultValue(Object object) throws Exception {
			return field.get(object);
		}

	}
}
