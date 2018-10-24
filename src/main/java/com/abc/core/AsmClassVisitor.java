package com.abc.core;

import com.abc.core.annotation.Component;
import com.abc.core.util.FileUtils;
import com.sun.istack.internal.Nullable;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AsmClassVisitor extends ClassVisitor implements ClassMetadata {

    private String className = "";

    private boolean isInterface;

    private boolean isAnnotation;

    private boolean isAbstract;

    private boolean isFinal;

    protected final Set<String> annotationSet = new LinkedHashSet<>(4);


    protected final LinkedHashMap<String, List<AnnotationAttributes>> attributesMap = new LinkedHashMap<>(6);

    @Nullable
    private String superClassName;

    private String[] interfaces = new String[0];

    public AsmClassVisitor() {
        super(CommonValues.ASM_VERSION);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

    @Override
    public void visit(
            int version, int access, String name, String signature, @Nullable String supername, String[] interfaces) {

        this.className = FileUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & CommonValues.ACC_INTERFACE) != 0);
        this.isAnnotation = ((access & CommonValues.ACC_ANNOTATION) != 0);
        this.isAbstract = ((access & CommonValues.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & CommonValues.ACC_FINAL) != 0);
        if (supername != null && !this.isInterface) {
            this.superClassName = FileUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = FileUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        final AnnotationVisitor annotationVisitor = new AnnotationAttributesReadingVisitor(className,attributesMap,ClassLoader.getSystemClassLoader());
        return annotationVisitor;
    }

    public List<AnnotationAttributes> getAttributesByAnnotationType(String annotationType){
        List<AnnotationAttributes> annotationAttributes = attributesMap.get(annotationType);
        return annotationAttributes;
    }

    @Override
    public String getSpecifiedBeanName(){
        Object o = getAttributesByAnnotationType(Component.class.getName()).get(0).get("value");
        if(o instanceof String)
            return (String)o;
        return null;
    }
}
