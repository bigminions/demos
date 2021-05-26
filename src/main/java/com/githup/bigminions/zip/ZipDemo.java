package com.githup.bigminions.zip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDemo {

    private static void appendFileToZip(String zipRoot, String srcRootDir, File file, ZipOutputStream zos) throws IOException {

        zipRoot = StrUtil.addSuffixIfNot(zipRoot, StrUtil.SLASH);
        String relPath = FileUtil.subPath(srcRootDir, file);
        String zipPath = zipRoot + relPath;

//        System.out.println(file.getPath() + ">" + zipPath);

        if (file.isDirectory()) {
            zipPath = StrUtil.addSuffixIfNot(zipPath, StrUtil.SLASH);
            zos.putNextEntry(new ZipEntry(zipPath));
            File[] fileArr = file.listFiles();
            if (fileArr != null && fileArr.length > 0) {
                for (File child : fileArr) {
                    appendFileToZip(zipRoot, srcRootDir, child, zos);
                }
            }
        } else {
            zos.putNextEntry(new ZipEntry(zipPath));
            IoUtil.copy(FileUtil.getInputStream(file), zos);
        }

        zos.closeEntry();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        File picDir = FileUtil.file("D:\\Pictures");

        ExecutorService es = Executors.newFixedThreadPool(32);
        try (ZipOutputStream zos = new ZipOutputStream(FileUtil.getOutputStream("1234567.zip"))) {
            String zipPath = "demo1";

            File[] fileArr = picDir.listFiles();
            if (fileArr != null && fileArr.length > 0) {

                CountDownLatch cd = new CountDownLatch(fileArr.length);
                for (File child : fileArr) {
                    es.execute(() -> {
                        synchronized (ZipDemo.class) {
                            try {
                                System.out.println("开始压缩:" + child.getPath());
                                appendFileToZip(zipPath, picDir.getAbsolutePath(), child, zos);
                                System.out.println("结束压缩:" + child.getPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                cd.countDown();
                            }
                        }
                    });
                }

                cd.await();
                es.shutdown();
            }
        }
    }
}
