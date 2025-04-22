package Utils;


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
 *
 * @author oooooooooooldbi
 * @date 2025/4/18 10:38
 * @email bithesenior@163.com
 */
public class MySATHandler extends DefaultHandler {
    String value = null;

    String entityPackage ="";

    List dtoList = new ArrayList();

    Map<String , Class> classMap = new HashMap();
    //
    List resultList = new ArrayList();

    public List getResultList() {
        return resultList;
    }

    public MySATHandler (String entityPackage) {
        this.entityPackage = entityPackage;
    }


    /**
     * 用来标识解析开始
     */
    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.startDocument();
        List<Class<?>> dtoClazz = PackageScanner.scanPackage(entityPackage);

        for (Class<?> cls : dtoClazz) {
            if (cls.isAnnotationPresent(ObjectXMLElement.class))
                classMap.put(cls.getAnnotation(ObjectXMLElement.class).name(), cls);
        }
    }

    /**
     * 用来标识解析结束
     */
    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
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
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            int num = attributes.getLength();
            for (int i = 0; i < num; i++) {
               Field[] declaredFields = aClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    FieldXMLElement annotation = field.getAnnotation(FieldXMLElement.class);
                    if (null!=annotation&&attributes.getQName(i).equals(annotation.name())){
                        try {

                            Method method = aClass.getMethod(annotation.setMethod(), String.class);
                            method.invoke(o,attributes.getValue(i));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
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

        if (dtoList.size()>0){

            Object o = dtoList.get(dtoList.size() - 1);

            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                FieldXMLElement annotation = field.getAnnotation(FieldXMLElement.class);

                if (null!=annotation&&qName.equals(annotation.name())) {
                    Method method = null;
                    try {
                        method = o.getClass().getMethod(annotation.setMethod(), String.class);
                        method.invoke(o,value);
                        System.out.println(o);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        if (classMap.containsKey(qName)) {
            Class aClass = classMap.get(qName);

            Object son = dtoList.get(dtoList.size() - 1);
            ObjectXMLElement annotation = (ObjectXMLElement) aClass.getAnnotation(ObjectXMLElement.class);
            String rootedMethod = annotation.rootMethod();

            if (!rootedMethod.equals("")) {
                try {
                    Method method =son.getClass().getMethod(rootedMethod);
                    method.invoke(son);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }else if(dtoList.size()>1){
                Object father = dtoList.get(dtoList.size() - 2);
                //Object son = dtoList.get(dtoList.size() - 1);
                //RootXMLElement annotation = (RootXMLElement) aClass.getAnnotation(RootXMLElement.class);
                try {
                    Method method = father.getClass().getMethod(annotation.addMethod(),son.getClass());
                    method.invoke(father,son);

                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }else {
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
        /*if (!value.trim().equals("")) {
            //System.out.println("节点值是：" + value);
        }*/
    }
}


