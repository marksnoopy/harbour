package com.diorsman.harbour.csv;

/**
 * 
 * class 中的属性和 csv 中的列的对应关系
 *
 * @author Mark Snoopy
 *
 * @date 2015年7月6日
 *
 */
public class AttributeColumnRelation {
    
    /**
     * 属性名
     */
    private String attributeName;
    
    /**
     * 列名
     */
    private String columnName;

    /**
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
