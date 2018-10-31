package com.abc.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class YamlResource implements Resource {

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    public YamlResource(File file){

    }
}
