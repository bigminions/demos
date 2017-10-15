package com.githup.bigminions.clsloader;

import java.io.IOException;
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
            System.out.println("the define class loader will load : " + name);
            if (name.equals("HelloWorld")) {
                return findHelloClass();
            }
            return super.findClass(name);
        }

        private Class<?> findHelloClass() {
            try {
                Path path = Paths.get(loadPath, "HelloWorld.class");
                System.out.println(path.toString());
                byte[] bytes = Files.readAllBytes(path);
                return defineClass("HelloWorld", bytes, 0 , bytes.length);
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
            Class clazz = defineClassLoader.loadClass("HelloWorld");
            Object obj = clazz.newInstance();
            System.out.println("ClassName : " + obj.getClass().getName());
            System.out.println("Loader : " + obj.getClass().getClassLoader().toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
