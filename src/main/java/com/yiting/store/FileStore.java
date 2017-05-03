package com.yiting.store;

import com.yiting.model.ConfigBaseModel;
import com.yiting.model.ConfigFileModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by admin on 2017/4/30.
 */
public class FileStore {
	public static Map<String, Map<String, String>> store = new HashMap<String, Map<String, String>>();

	public static Map<String,ConfigBaseModel> configFileMap=new HashMap<String, ConfigBaseModel>();


	public static void loadFileProperties(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			File[] propertyFiles = file.listFiles();
			for (File propertyFile : propertyFiles) {
				Map<String, String> singleFileMap = new HashMap<String, String>();
				if (propertyFile.getName().endsWith("properties")) {
					Properties properties = new Properties();
					FileInputStream stream=null;
					try {
						stream=new FileInputStream(propertyFile);
						properties.load(stream);
						for (Object key : properties.keySet()) {
							singleFileMap.put((String) key, properties.getProperty((String) key, ""));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						if(stream!=null){
							try {
								stream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				store.put(propertyFile.getName(), singleFileMap);
			}

		}

	}

	public static Map<String, String> getProperties(String fileName) {
		return store.get(fileName);
	}

	public static Set<String> getKeys(){
		return store.keySet();
	}



	public static void transferScanData(List<ConfigBaseModel> baseModels){
		for(ConfigBaseModel baseModel:baseModels) {
			ConfigFileModel fileModel=(ConfigFileModel)baseModel;
			if (store.containsKey(fileModel.getConfigFileName())){
				transferScanData(fileModel);
			}
		}

	}

	public static void transferScanData(ConfigFileModel fileModel){
		configFileMap.put(fileModel.getConfigFileName(),fileModel);

	}




	public static void main(String[] args){
		File file=new File(System.getProperty("user.dir"));

		System.out.println(FileStore.class.getResource("").getPath());
		System.out.println(FileStore.class.getResource("/").getPath());
		System.out.println(FileStore.class.getClassLoader().getResource("").getPath());
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

		System.out.println(FileStore.class.getResource(""));
		File propertiesDir=new File(file.getPath()+"/src/main/"+"resources");
		FileStore.loadFileProperties(propertiesDir.getPath());

		for(String fileName:FileStore.getKeys()){
			System.out.println(fileName);
		}
	}


}
