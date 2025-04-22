package org.example;

import Utils.SATxmlUtils;

import java.util.List;

/**
 * @author: oooooooooldbi
 * @date: 2025/4/19 10:57
 * @email: bithesenior@163.com
 */
public class Main {
    public static void main(String[] args) {



        List euEntity1 = SATxmlUtils.readXMLToEntity("C:/Users/bithe/Desktop/test/test.xml", "dto");

        //System.out.println(euEntity);
        System.out.println(euEntity1);
    }
}