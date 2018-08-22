package com.githup.bigminions.clsloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by daren on 8/12/2017.
 */
public class DefineClassLoaderTest {

    public static class DefineClassLoader extends ClassLoader {

        private String loadPath;

        public DefineClassLoader(String path) {
            this.loadPath = path;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals("HelloWorld") || name.equals("Say")) {
                return findHelloClass(name);
            }
            return super.findClass(name);
        }

        private Class<?> findHelloClass(String name) {
            try {
                Path path = Paths.get(loadPath, name + ".class");
                System.out.println("load class " + name + " : " + path.toString());
                byte[] bytes = Files.readAllBytes(path);
                return defineClass(name, bytes, 0 , bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            String loadPath = System.getProperty("user.dir") + "/src/main/java/" + DefineClassLoaderTest.class.getPackage().getName().replace(".", "/") + "/classFiles";
            DefineClassLoader defineClassLoader = new DefineClassLoader(loadPath);
            Class helloClazz = defineClassLoader.loadClass("HelloWorld");
            Class sayClazz = defineClassLoader.loadClass("Say");

            Object sayObj = sayClazz.newInstance();
            Object helloObj = helloClazz.newInstance();

            System.out.println("Loader : " + sayObj.getClass().getClassLoader().toString());
            System.out.println("Loader : " + helloObj.getClass().getClassLoader().toString());

            Method method1 = sayClazz.getMethod("saySomething", null);
            method1.invoke(sayObj, null);
            Method method2 = helloClazz.getDeclaredMethod("main", String[].class);
            method2.invoke(helloObj, new Object[]{null});

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
