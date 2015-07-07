package com.diorsman.harbour.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Csv<T> {
    
    /**
     * 类型
     */
    private Class<T> classType;
    
    /**
     * 数据集
     */
    private Collection<T> dataSet;
    
    /**
     * 属性和列的对应关系
     */
    private List<AttributeColumnRelation> attributeColumnRelationList;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 初始化
     * 
     * @param dataSet
     */
    public Csv(Collection<T> dataSet) {
        
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            this.classType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        
        this.dataSet = dataSet;
        
    }
    
    /**
     * 导出 csv
     * 
     * @return File 文件句柄
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    public File export() throws Exception {
        
        FileOutputStream fileOutputStream =null;
        OutputStreamWriter outputStreamWriter =null;
        BufferedWriter bufferedWriter = null;
        
        File file = new File(filePath + fileName);
        
        try {
            
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
            bufferedWriter =new BufferedWriter(outputStreamWriter);
            
            List<String> columnNameList = new ArrayList<String>(attributeColumnRelationList.size());
            for (AttributeColumnRelation attributeColumnRelation : attributeColumnRelationList) {
                columnNameList.add(attributeColumnRelation.getColumnName());
            }
            
            String columnNames = StringUtils.join(columnNameList, ",");
            
            bufferedWriter.append(columnNames).append("\r");
            
            Iterator<T> iterator = dataSet.iterator();
            while (iterator.hasNext()) {

                StringBuffer rowData = new StringBuffer();
                
                T t = (T) iterator.next();
                
                for (AttributeColumnRelation attributeColumnRelation : attributeColumnRelationList) {
                    
                    String attributeName = "get"  
                            + attributeColumnRelation.getAttributeName().substring(0, 1).toUpperCase()  
                            + attributeColumnRelation.getAttributeName().substring(1); 
                    
                    Method method = classType.getMethod(attributeName, new Class[] {});
                    Object value = method.invoke(t, new Object[] {});
                    
                    rowData.append(value.toString()).append(",");
                    
                }
                
                bufferedWriter.append(rowData.toString()).append("\r");
                
            }
            
            bufferedWriter.flush();
        }
        catch (Exception e) {
        } finally {
            
            if(bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                    bufferedWriter=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            
            if(outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                    outputStreamWriter=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                    fileOutputStream=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        
        return file;
        
    }

    /**
     * @return the classType
     */
    public Class<T> getClassType() {
        return classType;
    }

    /**
     * @param classType the classType to set
     */
    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }

    /**
     * @return the dataSet
     */
    public Collection<T> getDataSet() {
        return dataSet;
    }

    /**
     * @param dataSet the dataSet to set
     */
    public void setDataSet(Collection<T> dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * @return the attributeColumnRelationList
     */
    public List<AttributeColumnRelation> getAttributeColumnRelationList() {
        return attributeColumnRelationList;
    }

    /**
     * @param attributeColumnRelationList the attributeColumnRelationList to set
     */
    public void setAttributeColumnRelationList(List<AttributeColumnRelation> attributeColumnRelationList) {
        this.attributeColumnRelationList = attributeColumnRelationList;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
    
}