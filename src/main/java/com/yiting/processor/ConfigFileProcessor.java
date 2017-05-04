package com.yiting.processor;

import com.yiting.model.ConfigBaseModel;
import com.yiting.model.ConfigFileModel;
import com.yiting.store.FileStore;

import java.util.Map;

/**
 * Created by admin on 2017/5/3.
 */
public class ConfigFileProcessor implements ConfigProcessor {

	public void inject2Instance(Object obj, String fileName) {

	}

	public void storeOneFile(ConfigBaseModel configBaseModel) {

	}

	public void inject2Conf() {
		for (String key : FileStore.configFileMap.keySet()) {
			ConfigFileModel fileModel = (ConfigFileModel) FileStore.configFileMap.get(key);
			injectOneFile(key, fileModel);
		}
	}

	private void injectOneFile(String key, ConfigFileModel configFileModel) {
		if (configFileModel == null) {
			return;
		}

		try {
			Object object = configFileModel.getObject();
			if (object == null) {
				Object ret = configFileModel.getCls().newInstance();
				object = ret;
				configFileModel.setObject(object);
			}

			Map<String,ConfigFileModel.FileItemValue> fileItemValueMap=configFileModel.getFileItemValueMap();
			Map<String,String>keyValueProp=FileStore.getProperties(configFileModel.getConfigFileName());
			if(fileItemValueMap!=null&&keyValueProp!=null){
				for(String propertyKey:fileItemValueMap.keySet()){
					ConfigFileModel.FileItemValue fileItemValue=fileItemValueMap.get(propertyKey);

					if(fileItemValue.isStatic()){
						fileItemValue.setValueForStaticFileItem(keyValueProp.get(propertyKey));
					}else {
						fileItemValue.setValueForFileItem(object,keyValueProp.get(propertyKey));
					}
				}
			}
			System.out.println(object);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		Object object1=new Object();
		Object object2=object1;
		Object object3=new Object();
		object2=object3;
		System.out.println(object2==object1);
	}

}
