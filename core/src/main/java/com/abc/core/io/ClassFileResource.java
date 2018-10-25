package com.abc.core.io;

import com.abc.core.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class ClassFileResource implements Resource {
    private String path;
    private File file;

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(this.file.toPath());
    }

    public ClassFileResource(File file){
        this.path = file.getPath();
        this.file = file;
    }


}
