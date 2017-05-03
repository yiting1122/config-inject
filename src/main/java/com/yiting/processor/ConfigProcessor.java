package com.yiting.processor;

import com.yiting.model.ConfigBaseModel;

/**
 * Created by admin on 2017/4/30.
 */
public interface ConfigProcessor {

	void inject2Instance(Object obj,String keyName);

	void storeOneFile(ConfigBaseModel configBaseModel);

}
