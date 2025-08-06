package utils;


import anos.FieldXMLElement;
import anos.ObjectXMLElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oooooooooooldbi
 * @date 2025/4/18 10:38
 * @email bithesenior@163.com
 */
public class MySATHandler extends DefaultHandler {
    String value = null;

    String entityPackage = "";

    String defaultString = "##default";

    List dtoList = new ArrayList();

    Map<String, Class> classMap = new HashMap();
    //
    List resultList = new ArrayList();

    public List getResultList() {
        return resultList;
    }

    public MySATHandler(String entityPackage) {
        this.entityPackage = entityPackage;
    }


    /**
     * 用来标识解析开始
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        List<Class<?>> dtoClazz = PackageScanner.scanPackage(entityPackage, true);

        for (Class<?> cls : dtoClazz) {

            if (cls.isAnnotationPresent(ObjectXMLElement.class)) {

                String name = cls.getAnnotation(ObjectXMLElement.class).name();

                if (defaultString.equals(name)) {
                    name = SetMethodDefaultValue.classNameDefaultValue(cls);
                }

                classMap.put(name, cls);
            }
        }
    }

    /**
     * 用来标识解析结束
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     * 解析xml元素
     */
    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        if (classMap.containsKey(qName)) {
            Object o = null;
            Class aClass = classMap.get(qName);
            try {
                o = aClass.newInstance();
                dtoList.add(o);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            int num = attributes.getLength();
            for (int i = 0; i < num; i++) {
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    FieldXMLElement annotation = field.getAnnotation(FieldXMLElement.class);
                    if (annotation != null) {
                        if ((defaultString.equals(annotation.name()) && attributes.getQName(i).equals(field.getName())) || attributes.getQName(i).equals(annotation.name())) {
                            try {
                                String setMethod = annotation.setMethod();
                                if (defaultString.equals(setMethod)) {
                                    setMethod = SetMethodDefaultValue.setMethodDefaultValue(field);
                                }

                                Method method = aClass.getMethod(setMethod, String.class);
                                method.invoke(o, attributes.getValue(i));
                            } catch (NoSuchMethodException  | IllegalAccessException e) {
                                throw new SAXException(e);
                            }catch (InvocationTargetException e){
                                throw new SAXException(e);
                            }
                        }
                    }
                }
            }
        }

      /*  else if (!qName.equals("name") && !qName.equals("bookstore")) {

        }*/
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (dtoList.size() > 0) {
            Object o = dtoList.get(dtoList.size() - 1);
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                FieldXMLElement annotation = field.getAnnotation(FieldXMLElement.class);
                //标签与字段注解对应，则set
                Class<?> type = field.getType();

                if (null != annotation && String.class.equals(type)) {
                    if ((defaultString.equals(annotation.name()) && qName.equals(field.getName())) || qName.equals(annotation.name())) {
                        try {
                            String setMethod = annotation.setMethod();
                            if (defaultString.equals(setMethod)) {
                                setMethod = SetMethodDefaultValue.setMethodDefaultValue(field);
                            }
                            Method method = o.getClass().getMethod(setMethod, String.class);
                            method.invoke(o, value);
                        } catch (NoSuchMethodException| IllegalAccessException e) {
                            throw new SAXException(e);
                        }catch (InvocationTargetException e){
                            throw new SAXException(e);
                        }
                    }
                }
            }

            //标签与类注解对应，则将此对象加入上一级节点
            if (classMap.containsKey(qName)) {
                Class aClass = classMap.get(qName);

                Object son = dtoList.get(dtoList.size() - 1);
                ObjectXMLElement annotation = (ObjectXMLElement) aClass.getAnnotation(ObjectXMLElement.class);
                String rootedMethod = annotation.rootMethod();

                if (!rootedMethod.equals(defaultString)) {
                    //制定了单独处理方法则调单独处理方法
                    try {
                        Method method = son.getClass().getMethod(rootedMethod);
                        method.invoke(son);
                    } catch (NoSuchMethodException| IllegalAccessException e) {
                        throw new SAXException(e);
                    }catch (InvocationTargetException e){
                        throw new SAXException(e);
                    }
                } else if (dtoList.size() > 1) {
                    //不是根节点则加入上一级
                    Object father = dtoList.get(dtoList.size() - 2);
                    String addMethod = annotation.addMethod();
                    //如果子类制定了addMethod则直接使用
                    if (!addMethod.equals(defaultString)) {
                        try {
                            Method method = father.getClass().getMethod(addMethod, son.getClass());
                            method.invoke(father, son);
                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            throw new SAXException(e);
                        }catch (InvocationTargetException e){
                            throw new SAXException(e);
                        }
                    } else {
                        //子类没有指定addMethod则使用默认值
                        Field[] declaredFields1 = father.getClass().getDeclaredFields();
                        for (Field field : declaredFields1) {
                            FieldXMLElement annotation1 = field.getAnnotation(FieldXMLElement.class);
                            if (annotation1 != null) {
                                String name = annotation1.name();
                                if (defaultString.equals(name)) {
                                    name = field.getName();
                                }
                                if (name.equals(qName)) {
                                    try {
                                        addMethod = SetMethodDefaultValue.setMethodDefaultValue(field);
                                        Method method = father.getClass().getMethod(addMethod, son.getClass());
                                        method.invoke(father, son);
                                    } catch (NoSuchMethodException | IllegalAccessException e) {
                                        throw new SAXException(e);
                                    }catch (InvocationTargetException e){
                                        throw new SAXException(e);
                                    }
                                }
                            }
                        }
                    }

                } else {
                    //是根节点则加入resultList
                    resultList.add(dtoList.get(dtoList.size() - 1));
                }
                dtoList.remove(dtoList.size() - 1);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}


