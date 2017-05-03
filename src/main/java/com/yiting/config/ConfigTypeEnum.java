package com.yiting.config;

/**
 * Created by admin on 2017/4/29.
 */
public enum ConfigTypeEnum {

	FILE(0, "配置文件"),
	ITEM(1, "配置项");

	private int type = 0;
	private String modelName = null;

	private ConfigTypeEnum(int type, String modelName) {
		this.type = type;
		this.modelName = modelName;
	}

	public static ConfigTypeEnum getByType(int type) {
		int index = 0;
		ConfigTypeEnum[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			ConfigTypeEnum disConfigTypeEnum = arr$[i$];
			if (type == index) {
				return disConfigTypeEnum;
			}

			++index;
		}

		return null;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
}


