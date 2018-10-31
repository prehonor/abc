package com.abc.core.io;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

//    private Properties properties;//读取.properties文件
    String path;

    public ClassPathResource(String path){
        this.path = path;
    }
    public InputStream getInputStream() throws IOException {

        assert (path!=null):"资源路径不能为空";
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

}
