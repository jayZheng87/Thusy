package com.rambo.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Create by Rambo on 2017/4/10
 **/
public class incFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        if (file.getName().contains(".")) {
            return false;
        }

        if (!file.isDirectory()) {
            return false;
        }
        return true;
    }
}