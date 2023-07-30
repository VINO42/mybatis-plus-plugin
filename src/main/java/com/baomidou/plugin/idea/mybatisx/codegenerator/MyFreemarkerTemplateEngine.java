package com.baomidou.plugin.idea.mybatisx.codegenerator;

import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.GenConfig;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSource;
import com.jfinal.template.source.FileSource;
import com.jfinal.template.source.ISource;
import com.jfinal.template.source.ISourceFactory;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.template.ITemplate;
import com.mybatisflex.codegen.template.impl.EnjoyTemplate;
import com.mybatisflex.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


/**
 * 只能读取外部的文件，要提供全部文件，不然不能找到
 */
public class MyFreemarkerTemplateEngine implements ITemplate {

    private static final Logger logger= LoggerFactory.getLogger(MyFreemarkerTemplateEngine.class);

    private final String projectPath;
    private final GenConfig genConfig;
    private static final String engineName = "freemarker";
    private final Engine engine;
    public MyFreemarkerTemplateEngine(String projectPath, GenConfig genConfig) {
        this.projectPath = projectPath;
        this.genConfig = genConfig;
        Engine engine = Engine.use(engineName);
        if (engine == null) {
            engine = Engine.create(engineName, e -> {
                e.addSharedStaticMethod(StringUtil.class);
                e.setSourceFactory(new EnjoyTemplate.FileAndClassPathSourceFactory());
            });
        }
        this.engine = engine;

        // 以下配置将支持 user.girl 表达式去调用 user 对象的 boolean isGirl() 方法
//        Engine.addFieldGetterToFirst(new FieldGetters.IsMethodFieldGetter());
    }

    @Override
    public void generate(Map<String, Object> params, String templateFilePath, File generateFile) {
        if (!generateFile.getParentFile().exists() && !generateFile.getParentFile().mkdirs()) {
            throw new IllegalStateException("Can not mkdirs by dir: " + generateFile.getParentFile());
        }
        Table table = (Table) params.get("table");
        String columns = table.getColumns().stream().map(Column::getName).collect(joining(" , "));
        table.getGlobalConfig().setCustomConfig("baseColumns",columns);
        // 开始生成文件
        try (FileOutputStream fileOutputStream = new FileOutputStream(generateFile)) {
            engine.getTemplate(templateFilePath).render(params, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件或者类路径读取模板。
     *
     * @author 王帅
     */
    public static class FileAndClassPathSourceFactory implements ISourceFactory {

        @Override
        public ISource getSource(String baseTemplatePath, String fileName, String encoding) {
            // 先从文件寻找资源，找不到再从类路径寻找资源
            if (new File(fileName).exists()) {
                return new FileSource(baseTemplatePath, fileName, encoding);
            }
            return new ClassPathSource(baseTemplatePath, fileName, encoding);
        }

    }
//    public MyFreemarkerTemplateEngine init() {
////        super.init(configBuilder);
//        configurationOut = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
//        configurationOut.setDefaultEncoding("utf-8");
//        try {
//            configurationOut.setDirectoryForTemplateLoading(new File(projectPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 自带的
//        configurationSelf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
//        configurationSelf.setDefaultEncoding("utf-8");
//        configurationSelf.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/");
//
//        return this;
//    }


//    @Override
//    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
//        Template template = null;
//        try {
//            template = configurationOut.getTemplate(templatePath);
//        } catch (Exception e) {
//            logger.debug("没有外部模板:" + templatePath + ";  文件:" + outputFile);
//            logger.debug(e.toString());
//        }
//        if (template != null) {
//            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
//                template.process(objectMap, new OutputStreamWriter(fileOutputStream, UTF8));
//            }
//        } else { // 自带的
//            template = configurationSelf.getTemplate(templatePath);
//            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
//                template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
//            }
//        }
//
//        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
//    }


//    @Override
//    public String templateFilePath(String filePath) {
//        return filePath + ".ftl";
//    }

    /**
     * 输出 java xml 文件
     */
//    @Override
//    public AbstractTemplateEngine batchOutput() {
//        try {
//            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
//            for (TableInfo tableInfo : tableInfoList) {
//                Map<String, Object> objectMap = getObjectMap(tableInfo);
//                objectMap.put("DATE", DateFormatUtils.format(new Date(), "yyyy/MM/dd"));
//                objectMap.put("TIME", DateFormatUtils.format(new Date(), "HH:mm:ss"));
//                objectMap.put("entity", tableInfo.getEntityName().replace("Table", EMPTY));
//                objectMap.put("email", "");
//
//                objectMap.put("controllerPath", tableInfo.getEntityName().replace("Table", EMPTY).replace("Entity", EMPTY).toLowerCase());
//                objectMap.put("mapperNameSpaceName", tableInfo.getEntityName().replace("Table", EMPTY).replace("Entity", EMPTY) + "Mapper");
//                objectMap.put("controllerMappingPath", tableInfo.getEntityName().replace("Table", EMPTY).replace("Entity", EMPTY));
//
//
//                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
//                TemplateConfig template = getConfigBuilder().getTemplate();
//                // 自定义内容
//                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
//                if (null != injectionConfig) {
//                    injectionConfig.initMap();
//                    objectMap.put("cfg", injectionConfig.getMap());
//                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
//                    // gen mapper.xml
//                    if (genConfig.isMapper()) {
//                        if (CollectionUtils.isNotEmpty(focList)) {
//                            for (FileOutConfig foc : focList) {
//                                if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
//                                    writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
//                                }
//                            }
//                        }
//                    }
//                }
//                String entityName = tableInfo.getEntityName();
//                // Mp.java
//                if (genConfig.isEntity()) {
//                    if (null != entityName && null != pathInfo.get(ENTITY_PATH)) {
//                        String entityFile = String.format((pathInfo.get(ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
//                        if (isCreate(FileType.ENTITY, entityFile)) {
//                            writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
//                        }
//                    }
//                }
//
//                // MpMapper.java
//                if (genConfig.isMapper()) {
//                    if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
//                        String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
//                        if (isCreate(FileType.MAPPER, mapperFile)) {
//                            writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
//                        }
//                    }
//                    // MpMapper.xml
//                    if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
//                        String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
//                        if (isCreate(FileType.XML, xmlFile)) {
//                            writer(objectMap, templateFilePath(template.getXml()), xmlFile);
//                        }
//                    }
//                }
//                // IMpService.java
//                if (genConfig.isService()) {
//                    if (null != tableInfo.getServiceName() && null != pathInfo.get(SERVICE_PATH)) {
//                        String serviceFile = String.format((pathInfo.get(SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
//                        if (isCreate(FileType.SERVICE, serviceFile)) {
//                            writer(objectMap, templateFilePath(template.getService()), serviceFile);
//                        }
//                    }
//                }
//                // MpServiceImpl.java
//                if (genConfig.isServiceImpl()) {
//                    if (null != tableInfo.getServiceImplName() && null != pathInfo.get(SERVICE_IMPL_PATH)) {
//                        String implFile = String.format((pathInfo.get(SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
//                        if (isCreate(FileType.SERVICE_IMPL, implFile)) {
//                            writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
//                        }
//                    }
//                }
//                // MpController.java
//                if (genConfig.isController()) {
//                    if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
//                        String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
//                        if (isCreate(FileType.CONTROLLER, controllerFile)) {
//                            writer(objectMap, templateFilePath(template.getController()), controllerFile);
//                        }
//                    }
//                }
//            }
//        } catch (
//            Exception e) {
//            logger.error("无法创建文件，请检查配置信息！", e);
//        }
//        return this;
//    }

    /**
     * 处理输出目录
     */
//    @Override
//    public AbstractTemplateEngine mkdirs() {
//        Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
//        getConfigBuilder().getPathInfo().forEach((key, value) -> {
//            if (key.equals(ENTITY_PATH) && !genConfig.isEntity()) {
//                return;
//            }
//            if (key.equals(SERVICE_PATH) && !genConfig.isService()) {
//                return;
//            }
//            if (key.equals(SERVICE_IMPL_PATH) && !genConfig.isServiceImpl()) {
//                return;
//            }
//            if (key.equals(CONTROLLER_PATH) && !genConfig.isController()) {
//                return;
//            }
//            if ((key.equals(MAPPER_PATH)|| key.equals(XML_PATH)) && !genConfig.isMapper()) {
//                return;
//            }
//
//            File dir = new File(value);
//            if (!dir.exists()) {
//                boolean result = dir.mkdirs();
//                if (result) {
//                    logger.debug("创建目录： [" + value + "]");
//                }
//            }
//        });
//        return this;
//    }


}
