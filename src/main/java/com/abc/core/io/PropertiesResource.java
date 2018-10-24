package com.abc.core.io;

import com.abc.core.Resource;
import com.abc.core.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesResource implements Resource {

//    private Properties properties;//读取.properties文件
    String path;

    public PropertiesResource(String path){
        this.path = path;
    }
    public InputStream getInputStream() throws IOException {

        assert (path!=null):"资源路径不能为空";
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }


}
