package com.today.todayfarm.dom;

import java.util.List;

public class FieldInfo {


	String fieldId;
	String fieldName;
	String fieldArea;
	String fieldBoundary;
	String userId;
	String fieldcrop;


    public String getFieldcrop() {
        return fieldcrop;
    }

    public void setFieldcrop(String fieldcrop) {
        this.fieldcrop = fieldcrop;
    }

    public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldArea() {
		return fieldArea;
	}

	public void setFieldArea(String fieldArea) {
		this.fieldArea = fieldArea;
	}

	public String getFieldBoundary() {
		return fieldBoundary;
	}

	public void setFieldBoundary(String fieldBoundary) {
		this.fieldBoundary = fieldBoundary;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
