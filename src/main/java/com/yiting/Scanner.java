package com.yiting;

import com.yiting.annotation.ConfigFile;
import com.yiting.annotation.ConfigFileItem;
import com.yiting.model.ConfigBaseModel;
import com.yiting.model.ConfigFileModel;
import com.yiting.model.ScanStaticModel;
import com.yiting.store.FileStore;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by admin on 2017/4/27.
 */
public class Scanner {
	private String packageList;
	private ScanStaticModel scanStaticModel;

	public Scanner() {
		scanStaticModel = new ScanStaticModel();
		packageList = null;
	}


	public void scan() {
//		Reflections reflections = new Reflections("com.yiting");
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage("com.yiting"))
				.setScanners(new ResourcesScanner(),
						new TypeAnnotationsScanner(),
						new MethodAnnotationsScanner(),
						new SubTypesScanner()));
		Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(ConfigFile.class);
		scanStaticModel.setConfigFileClassSet(classSet);
		Set<Method> methodSet=reflections.getMethodsAnnotatedWith(ConfigFileItem.class);
		scanStaticModel.setConfigFileItemMethodSet(methodSet);


		analysis();

		List<ConfigBaseModel> configBaseModelList = getConfigFiles();

		FileStore.loadFileProperties(FileStore.class.getResource("/").getPath());
		FileStore.transferScanData(configBaseModelList);


	}

	public List<ConfigBaseModel> getConfigFiles() {
		List<ConfigBaseModel> baseModels = new ArrayList<ConfigBaseModel>();
		for (Class<?> cls : scanStaticModel.getConfigFileClassSet()) {
			Set<Method> methods = scanStaticModel.getConfigFileItemMap().get(cls);
			if (methods == null) {
				continue;
			}
			ConfigFileModel configFileModel = ConfigFileModel.transfer(cls, methods);
			baseModels.add(configFileModel);
		}
		return baseModels;
	}


	public void analysis() {
		Map<Class<?>, Set<Method>> configFileItemMap = new HashMap<Class<?>, Set<Method>>();
		Set<Class<?>> classSet = scanStaticModel.getConfigFileClassSet();
		Set<Method> methodSet = scanStaticModel.getConfigFileItemMethodSet();
		for (Class<?> clsFile : classSet) {
			configFileItemMap.put(clsFile, new HashSet<Method>());
		}

		for (Method method : methodSet) {
			Class<?> cls = method.getDeclaringClass();
			if (configFileItemMap.containsKey(cls)) {
				Set<Method> mSet = configFileItemMap.get(cls);
				mSet.add(method);
				configFileItemMap.put(cls, mSet);
			} else {
				System.out.println("this method is not belong to a configfile:" + method.toString());
			}
		}

		Iterator<Class<?>> iterator = configFileItemMap.keySet().iterator();
		while (iterator.hasNext()) {
			Class<?> clsFile = iterator.next();
			if (configFileItemMap.get(clsFile).isEmpty()) {
				System.out.println("this config File has no fileItem,cls:" + clsFile.toString());
				continue;
			}
			ConfigFile configFile = clsFile.getAnnotation(ConfigFile.class);
			if (!isConfigFileTypeRight(configFile)) {
				System.err.println("system just allow '.properties' config file");
				continue;
			}
		}

		scanStaticModel.setConfigFileItemMap(configFileItemMap);

	}

	private boolean isConfigFileTypeRight(ConfigFile configFile) {
		String fileName = configFile.fileName();
		if (fileName.endsWith(".properties")) {
			return true;
		}
		return false;
	}


	public static void main(String[] args){
		Scanner scanner=new Scanner();

		scanner.scan();
	}

}
