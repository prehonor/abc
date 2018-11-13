package com.abc.core.parser;

import com.abc.core.*;
import com.abc.core.io.ClassPathResource;
import com.abc.core.io.Resource;
import com.abc.core.parser.support.PropertyValue;
import com.abc.core.parser.support.RuntimeBeanReference;
import com.abc.core.parser.support.TypedStringValue;
import com.abc.core.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlBeanDefinitionReader implements ConfigFileParser {
    private static final Log logger = LogFactory.getLog(YamlBeanDefinitionReader.class);
    private DefaultListableBeanFactory factory = null;
    private final String SCAN = "scan:componet-scan";
    private final String BASE_PACKAGE = "base-package";
    private final String IMPORT = "import";
    private final String BEAN = "bean";
    private String[] pathes;

    public YamlBeanDefinitionReader(DefaultListableBeanFactory factory,String[] pathes){
        this.factory = factory;
        this.pathes = pathes;
    }
    private List<BeanDefinitionHolder> beanDefinitionHolders = new ArrayList<>();

//    @Override
    public ParserData parserFrom(Resource resource) {
        BeansYamlObject parseData = new BeansYamlObject();
        doParserFrom(parseData,resource);
        return parseData;
    }

    private void doParserFrom(BeansYamlObject parseData,Resource resource){
        try {
            Yaml yaml = new Yaml();
            Object ret = yaml.load(resource.getInputStream());
            parseImport(parseData,ret);
            parseData.add(ret);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseImport(BeansYamlObject parseData,Object yamlObject){
        if(yamlObject instanceof Map){
            Object value = ((Map) yamlObject).get(IMPORT);
            if(value!=null){
                String path = (String)((Map) value).get(BASE_PACKAGE);
                Resource resource = new ClassPathResource(path);
                doParserFrom(parseData,resource);
            }
        }else if(yamlObject instanceof List){
            List list = (List)yamlObject;
            for(Object o : list){
                Object value = ((Map) o).get(IMPORT);
                if(value!=null){
                    String path = (String)((Map) value).get("resource");
                    Resource resource = new ClassPathResource(path);
                    doParserFrom(parseData,resource);
                }
            }
        }


    }

//    @Override
    public List<BeanDefinitionHolder> convertToBeanDefinition(ParserData data) {
        BeansYamlObject yamlObject = (BeansYamlObject)data;
        for(Object o : yamlObject.getList()){
            if(o instanceof Map){
                beanDefinitionHolders.add(parseBean((Map)o));
                parseScanPackage((Map)((Map) o).get(SCAN));
            }
            else if(o instanceof List){
                parseBean((List)o);
            }

        }
        return beanDefinitionHolders;
    }

    public BeanDefinitionHolder parseBean(Map map){
        BeanDefinitionHolder beanDefinitionHolder;
        Map bean = (Map)map.get(BEAN);
        String beanName = (String)bean.get("id");
        String className = (String)bean.get("class");
        GenericBeanDefinition bd = new GenericBeanDefinition(beanName,className);
        parseProperty(bd,bean);
        beanDefinitionHolder = new BeanDefinitionHolder(beanName,bd);
        return beanDefinitionHolder;
    }

    /**
     * 解析 bean 下的属性
     * @param bd spring中bean定义的数据结构,存储了整个bean的信息
     * @param bean yaml读取的bean的数据结构,存储了bean下的属性信息
     * */
    public void parseProperty(GenericBeanDefinition bd, Map bean){

        Object property = bean.get("property");
        if(property==null) return;
        if(property instanceof Map){
            Map propertyMap = (Map)property;
            parsePropertyMap(bd,propertyMap);
        }
        else if(property instanceof List){
            List properties = (List)property;
            for(Object subproperty : properties) {
                Map propertyMap = (Map)subproperty;
                parsePropertyMap(bd,propertyMap);
            }
        }
    }

    public void parsePropertyMap(GenericBeanDefinition bd, Map propertyMap){
        String name = (String) propertyMap.get("name");
        String refName = (String) propertyMap.get("ref");
        String value = (String) propertyMap.get("value");
        if(StringUtils.stringNotEmpty(name)){
            if(StringUtils.stringNotEmpty(refName)){
                RuntimeBeanReference ref = new RuntimeBeanReference(refName);
                PropertyValue pv = new PropertyValue(name,ref);
                bd.getPropertyValues().addPropertyValue(pv);
            }else if(StringUtils.stringNotEmpty(value)){
                TypedStringValue typeString = new TypedStringValue(value);
                PropertyValue pv = new PropertyValue(name,typeString);
                bd.getPropertyValues().addPropertyValue(pv);
            }else {
                if (logger.isDebugEnabled()) {
                    logger.debug("property :  " + name + "下的ref和value都不存在");
                }
            }
        }
    }


    public List<BeanDefinitionHolder> parseScanPackage(Map map){

        String pckage = (String)map.get(BASE_PACKAGE);

        beanDefinitionHolders.addAll(scanCandidateComponents(pckage));
        beanDefinitionHolders.addAll(getInnerComponents());

        return beanDefinitionHolders;
    }

    public List<BeanDefinitionHolder> parseBean(List list){
        for(Object map : list){
            Map one = (Map)map;
            if(one.keySet().contains(SCAN)){
                parseScanPackage((Map)one.get(SCAN));
            }else if(one.keySet().contains(BEAN)){
                beanDefinitionHolders.add(parseBean(one));
            }
        }
        return beanDefinitionHolders;
    }

    public List<BeanDefinitionHolder> scanCandidateComponents(String value){
        ComponentBeanDefinitionScanner componentBeanDefinitionScanner = new ComponentBeanDefinitionScanner();
        try {
            return componentBeanDefinitionScanner.registryComponentBeanDefinition(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * 注册处理依赖注入的类的beandefinition
     * */
    public List<BeanDefinitionHolder> getInnerComponents(){
        List<BeanDefinitionHolder> list = new ArrayList<>();
        Set<BeanDefinitionHolder> beanDefinitionHolders = AnnotationConfigUtils.registryAnnotatedBeanPostProcessors(factory);
        list.addAll(beanDefinitionHolders);
        return list;
    }

    @Override
    public void registryBeanDefinition() {
        for(String path: pathes){
            Resource resource = new ClassPathResource(path);
            ParserData data = parserFrom(resource);
            registryBeanDefinition(convertToBeanDefinition(data),factory);
        }
    }
}
