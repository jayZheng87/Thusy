package com.rambo.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Create by Rambo on 2017/4/10
 * 生成增量文件目录结构时的文件过滤
 **/
public class incFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return !file.getName().contains(".") && file.isDirectory();
    }
}