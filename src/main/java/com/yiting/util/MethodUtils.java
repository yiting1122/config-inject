package com.yiting.util;

import com.yiting.annotation.ConfigFileItem;
import com.yiting.config.ConfigTypeEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by admin on 2017/4/29.
 */
public class MethodUtils {

	public static Field getFieldFromMethod(Method method, Field[] expectFields, ConfigTypeEnum configTypeEnum) {
		String fieldName = null;
		if (configTypeEnum.getType() == ConfigTypeEnum.FILE.getType()) {
			ConfigFileItem configFileItem = method.getAnnotation(ConfigFileItem.class);
			fieldName = configFileItem.associateField();

		}
		if (fieldName == null) {
			fieldName = ClassUtils.getFieldNameByGetMethodName(method.getName());
		}

		if (fieldName == null) {
			return null;
		}

		for (Field f : expectFields) {
			if (fieldName.equals(f.getName())) {
				return f;
			}
		}
		return null;

	}

	public static Method getSetterMethodFromField(Class<?> curClass, Field field) {

		String fieldName = field.getName().toLowerCase();

		Set<Method> methods = ClassUtils.getAllMethod(curClass);
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals("set" + fieldName)) {
				return method;
			}
		}

		return null;
	}

}
