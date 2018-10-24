package com.abc.core.util;

import com.abc.core.CommonValues;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class FileUtils {

    public static Enumeration<URL> findAllClassPathResources(String path){
        Enumeration<URL> urls = null;
        try {
            urls = ClassLoader.getSystemClassLoader().getResources(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    public static Set<File> getFile(URL url){
        Set<File> set = new LinkedHashSet<>(8);
        File rootFile =  new File(url.getFile());
        doRetrieveMatchingFiles(rootFile,set);
        return set;
    }

    public static void doRetrieveMatchingFiles(File file,Set<File> result){
        if(file.isDirectory()){
            File[] dirContents = file.listFiles();
            for(File subFile : dirContents){
                doRetrieveMatchingFiles(subFile,result);
            }
        }else{
            if(file.getName().endsWith(".class"))
                result.add(file);
        }
    }

    public static String convertClassNameToResourcePath(String className){
        return className.replace(CommonValues.PACKAGE_SEPARATOR, CommonValues.PATH_SEPARATOR);
    }
    public static String convertResourcePathToClassName(String path){
        return path.replace(CommonValues.PATH_SEPARATOR, CommonValues.PACKAGE_SEPARATOR);
    }
}
