package com.cool.cool.hsql;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 11:50 AM
 * Email    : d.dim@gl-f.com
 */

public class FieldFilter {

    private String fieldName;
    private Object[] fieldValues;

    private FilterMode filterMode;

    public FieldFilter(String fieldName, FilterMode filterMode, Object... fieldValues) {
        this.fieldName = fieldName;
        this.filterMode = filterMode;
        this.fieldValues = fieldValues;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object[] getFieldValues() {
        return fieldValues;
    }

    public Object getFieldValue() {
        return fieldValues[0];
    }

    public Object getField2Value() {
        return fieldValues[1];
    }

    public void setFieldValue(Object... fieldValues) {
        if (!isSupportedField(fieldValues)) {
            throw new IllegalArgumentException("Only the following types are supported: Date, String, Number.");
        }
        this.fieldValues = fieldValues;
    }

    public FilterMode getFilterMode() {
        return filterMode;
    }

    public void setFilterMode(FilterMode filterMode) {
        this.filterMode = filterMode;
    }

    private boolean isSupportedField(Object fieldValue) {
//		return fieldValue instanceof String
//				|| fieldValue instanceof Date
//				|| Number.class.isAssignableFrom(fieldValue.getClass());
        return true;
    }
}
