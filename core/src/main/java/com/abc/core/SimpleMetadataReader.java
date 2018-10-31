package com.abc.core;

import com.abc.core.io.Resource;
import com.sun.istack.internal.Nullable;
import org.objectweb.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleMetadataReader {
    private final Resource resource;
    public final ClassMetadata classMetadata;
    SimpleMetadataReader(Resource resource, @Nullable ClassLoader classLoader) throws IOException {
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;
        try {
            classReader = new ClassReader(is);
        }
        catch (IllegalArgumentException ex) {
            throw ex;
        }
        finally {
            is.close();
        }

        AsmClassVisitor visitor = new AsmClassVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
        this.classMetadata = visitor;
        this.resource = resource;

    }
}
