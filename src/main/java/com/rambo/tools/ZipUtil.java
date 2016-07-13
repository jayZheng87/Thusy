package com.rambo.tools;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 通过Java的Zip输入输出流实现压缩和解压文件
 * Create by Yet on 2016/7/9
 */
public final class ZipUtil {

    private ZipUtil() {

    }

    /**
     * 压缩文件
     *
     * @param filePath 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            String sourceName = source.getName();
            String zipName = sourceName.contains(".") ? sourceName.substring(0, sourceName.indexOf(".")) : sourceName;
            target = new File(source.getParent(), zipName + ".rar");
            if (target.exists()) {
                boolean delete = target.delete();//删除旧的压缩包
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));

                addEntry(null, source, zos);  //添加对应的文件Entry
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zos, fos);
            }
        }
        return target;
    }

    /**
     * 扫描添加文件Entry
     *
     * @param base   基路径
     * @param source 源文件
     * @param zos    Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
        String entry = (base != null) ? base + source.getName() : source.getName(); //按目录分级，形如：aaa/bbb.txt
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    addEntry(entry + "/", file, zos);// 递归列出目录下的所有文件，添加文件 Entry
                }
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read;
                zos.putNextEntry(new ZipEntry(entry)); //如果只是想将文件夹下的所有文件压缩，不需名要压缩父目录,约定文件名长度 entry.substring(length)
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                IOUtil.closeQuietly(bis, fis);
            }
        }
    }

    /**
     * 解压文件
     *
     * @param filePath 压缩文件路径
     */
    public static void unzip(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                    File target = new File(source.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        // 创建文件父目录
                        target.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read;
                    byte[] buffer = new byte[1024 * 10];
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zis, bos);
            }
        }
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
    public static boolean deletefile(String delpath) throws Exception {
        File file = new File(delpath);
        if (file.isDirectory()) {
            String[] filelist = file.list();
            for (String delFile : filelist) {
                File delfile = new File(delpath + File.separator + delFile);
                if (delfile.isDirectory()) {
                    deletefile(delpath + File.separator + delFile);
                } else
                    System.out.println("正在删除文件：" + delfile.getPath() + ",删除是否成功：" + delfile.delete());
            }
            System.out.println("正在删除空文件夹：" + file.getPath() + ",删除是否成功：" + file.delete());
        } else
            System.out.println("正在删除文件：" + file.getPath() + ",删除是否成功：" + file.delete());
        return true;
    }

    public static void main(String[] args) {
  /*      File file = ZipUtil.zip("F:\\Garbage\\webservice_Client");
        System.out.println(file);
        ZipUtil.unzip("F:\\Garbage\\webservice_Client.rar");*/

       /* File file1 = new File("F:/AAA/BBB/CCC");
        if (file1.mkdirs()) {
            System.out.println("多级层文件夹创建成功！创建后的文件目录为：" + file1.getPath() + ",上级文件为:" + file1.getParent());
        }

        File file2 = new File("F:/AAA/BBB/CCC/DDD");
        if (file2.mkdir()) {
            System.out.println("单文件夹 DDD 创建成功！创建后的文件目录为：" + file2.getPath() + ",上级文件为:" + file2.getParent());
        }

        File file3 = new File("F:/AAA/BBB/CCC/DDD","mytest.txt");
        try {
            if (file3.createNewFile()) {
                System.out.println("多级层文件夹下文件创建成功！创建了的文件为:" + file3.getAbsolutePath() + ",上级文件为:" + file3.getParent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            deletefile("F:\\Garbage\\JXKP_20160701 - 副本 - 副本");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}