package com.wzj.production.jvm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * （必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法
 *  此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 *  @author wangzhijie
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            // new 一个 Hello 对象
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            // 获取 hello 方法
            Method helloMethod = helloClass.getMethod("hello");
            // 执行 Hello 对象的目标方法 hello
            helloMethod.invoke(helloClass.newInstance());
            // 最后方法输出 Hello, classLoader!
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get("src/main/java/com/wzj/production/jvm/Hello.xlass");
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

}
