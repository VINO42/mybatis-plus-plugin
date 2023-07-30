package com.baomidou.plugin.idea.mybatisx.codegenerator.utils;


//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableField;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import com.baomidou.plugin.idea.mybatisx.codegenerator.MyFreemarkerTemplateEngine;
import com.baomidou.plugin.idea.mybatisx.codegenerator.MysqlUtil;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.GenConfig;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.ColumnInfo;
import com.google.common.collect.Sets;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.*;
import com.mybatisflex.core.activerecord.Model;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

//import static com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.capitalFirst;
import static com.baomidou.plugin.idea.mybatisx.codegenerator.utils.MybatisConst.IDTYPES;
import static java.util.regex.Pattern.matches;

/**
 * 代码生成
 * // todo 生成Result类
 */
public class GenUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    private static final String EXTRA = "auto_increment";

    /**
     * @return 获取后端代码模板名称
     */
    public static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Dto");
        templateNames.add("Mapper");
        templateNames.add("Repository");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("QueryCriteria");
        templateNames.add("Controller");
        return templateNames;
    }

    /**
     * @return 获取前端代码模板名称
     */
    public static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("api");
        templateNames.add("index");
        templateNames.add("eForm");
        return templateNames;
    }

    public static void main(String[] args) {

        String tableName = "application";
        GenConfig genConfig = new GenConfig();
        genConfig.setId(1L);
        genConfig.setPack("org.py.modules.ShowTableInfo");
        genConfig.setModuleName("my-system");
        genConfig.setPath("D:\\tempfile\\");
        genConfig.setRootFolder("D:\\tempfile");
        genConfig.setAuthor("py");
        genConfig.setCover(true);

        genConfig.setEnableCache(false);
        genConfig.setBaseColumnList(true);
        genConfig.setResultMap(true);
//        List<ColumnInfo> columnInfos = new ArrayList<>();
//        ColumnInfo columnInfo = new ColumnInfo();
//        columnInfo.setColumnName("id");
//        columnInfo.setColumnType("bigint");
//        columnInfo.setColumnKey("PRI");
//        columnInfo.setExtra("auto_increment");
//        columnInfo.setColumnShow("true");
//
//        columnInfo.setColumnComment("123");
//        columnInfos.add(columnInfo);
        GenUtil.generatorCode(tableName, genConfig, null, null);
    }

    public static void generatorCode(String tableName, GenConfig genConfig,
                                     Map<String, String> fieldNameMap, String fieldPrefix) {
        // 数据源配置

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/lll?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("nopasswd");

//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl(MysqlUtil.getInstance().getDbUrl());
//         dsc.setSchemaName("public");
//        dsc.setDriverName(MysqlUtil.getInstance().getJdbcDriver());
//        dsc.setUsername(MysqlUtil.getInstance().getUsername());
//        dsc.setPassword(MysqlUtil.getInstance().getPassword());

        // 全局配置
//        GlobalConfig gc = new GlobalConfig();

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        //设置表前缀和只生成哪些表
        globalConfig.setCustomConfig("restControllerStyle",genConfig.isRestController());
        globalConfig.setCustomConfig("genController",genConfig.isController());
        globalConfig.setCustomConfig("genResultMap ",genConfig.isResultMap());
        globalConfig.setCustomConfig("genBaseColumn",genConfig.isBaseColumnList());
        globalConfig.setCustomConfig("date", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        globalConfig.setCustomConfig("DATE", DateFormatUtils.format(new Date(), "yyyy/MM/dd"));
        globalConfig.setCustomConfig("TIME", DateFormatUtils.format(new Date(), "HH:mm:ss"));
        globalConfig.setCustomConfig("entitySerialVersionUID", true);
        globalConfig.setCustomConfig("enableMapperCache",genConfig.isEnableCache());
        globalConfig.setCustomConfig("baseResultMap",genConfig.isResultMap());
        globalConfig.setCustomConfig("baseColumnList",genConfig.isBaseColumnList());

        //文档设置
        globalConfig.getJavadocConfig().setAuthor(genConfig.getAuthor());
        globalConfig.getJavadocConfig().setMapperPackage(genConfig.getMapperName());
        globalConfig.getJavadocConfig().setEntityPackage(genConfig.getEntityName());
        globalConfig.getJavadocConfig().setControllerPackage(genConfig.getControllerName());
        globalConfig.getJavadocConfig().setServicePackage(genConfig.getControllerName());
        globalConfig.getJavadocConfig().setServiceImplPackage(genConfig.getServiceImplName());
        globalConfig.getJavadocConfig().setTableDefPackage(genConfig.getPack());
        globalConfig.getEntityConfig().setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        //包设置
        String projectPath = genConfig.getRootFolder();
        globalConfig.getPackageConfig().setSourceDir(projectPath + File.separator + genConfig.getModuleName() + "/src/main/java");
        String pack = genConfig.getPack();
        globalConfig.getPackageConfig().setBasePackage(genConfig.getPack());
        globalConfig.getPackageConfig().setTableDefPackage(pack+"."+genConfig.getEntityName());
        globalConfig.getPackageConfig().setEntityPackage(pack+"."+genConfig.getEntityName());
        globalConfig.getPackageConfig().setMapperPackage(pack+"."+genConfig.getMapperName());
        globalConfig.getPackageConfig().setControllerPackage(pack+"."+genConfig.getControllerName());
        globalConfig.getPackageConfig().setServicePackage(pack+"."+genConfig.getServiceName());
        globalConfig.getPackageConfig().setServiceImplPackage(pack+"."+genConfig.getServiceImplName());
        globalConfig.getPackageConfig().setMapperXmlPath(projectPath + File.separator + genConfig.getModuleName() + "/src/main/resources/mapper");
        //策略配置
        //数据库表前缀，多个前缀用英文逗号（,） 隔开
        globalConfig.getStrategyConfig().setTablePrefix(genConfig.getPrefix());
        //是否生成视图映射
        globalConfig.getStrategyConfig().setGenerateForView(false);
        //逻辑删除的默认字段名称
        globalConfig.getStrategyConfig().setLogicDeleteColumn(null);
        //乐观锁的字段名称
        globalConfig.getStrategyConfig().setVersionColumn(null);
        HashSet<String> objects = new HashSet<>();
        objects.add(tableName);
        globalConfig.getStrategyConfig().setGenerateTables(objects);
        if (genConfig.isFill()) {
            ColumnConfig columnConfig = new ColumnConfig();
            columnConfig.setOnUpdateValue("update_time");
            columnConfig.setOnInsertValue("create_time");
            globalConfig.getStrategyConfig().setColumnConfig(columnConfig);
        }
        if (genConfig.isCover()){
            globalConfig.setEntityOverwriteEnable(true);
            globalConfig.setMapperOverwriteEnable(true);
            globalConfig.setMapperXmlOverwriteEnable(true);
            globalConfig.setServiceOverwriteEnable(true);
            globalConfig.setServiceImplOverwriteEnable(true);
            globalConfig.setControllerOverwriteEnable(true);
        }

        //模板配置
        String templatePath = "/templates";
        String mapperTemplatePath = templatePath + "/mapperXml.tpl";
        globalConfig.getTemplateConfig().setEntity(templatePath + "/entity.tpl");
        globalConfig.getTemplateConfig().setMapper(templatePath + "/mapper.tpl");
        globalConfig.getTemplateConfig().setService(templatePath + "/service.tpl");
        globalConfig.getTemplateConfig().setService(templatePath + "/service.tpl");
        globalConfig.getTemplateConfig().setServiceImpl(templatePath + "/serviceImpl.tpl");
        globalConfig.getTemplateConfig().setController(templatePath + "/controller.tpl");
        globalConfig.getTemplateConfig().setMapperXml(mapperTemplatePath);
        globalConfig.getTemplateConfig().setTemplate(new MyFreemarkerTemplateEngine(projectPath, genConfig));
        //可选
        globalConfig.enableEntity().setWithLombok(true);
        globalConfig.enableController().setRestStyle(true);
        globalConfig.enableController().setRestStyle(true);
        globalConfig.enableServiceImpl().setClassSuffix("Impl");
        globalConfig.setEntityWithSwagger(genConfig.isSwagger());
//        globalConfig.setMapperXmlFileSuffix(".xml");
        globalConfig.setTableDefPropertiesNameStyle(TableDefConfig.NameStyle.UPPER_CAMEL_CASE);
        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        globalConfig.setControllerGenerateEnable(true);
        globalConfig.setServiceGenerateEnable(true);
        globalConfig.setMapperXmlGenerateEnable(true);
        globalConfig.setEntityClassSuffix("Entity");
        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(genConfig.isCover());
        globalConfig.setControllerOverwriteEnable(genConfig.isCover());
        globalConfig.setServiceOverwriteEnable(genConfig.isCover());
        globalConfig.setMapperOverwriteEnable(genConfig.isCover());

//        String projectPath = System.getProperty("user.dir");
//        String projectPath = genConfig.getRootFolder();
//        gc.setOutputDir(projectPath + File.separator + genConfig.getModuleName() + "/src/main/java");
//        gc.setOpen(false);
//        gc.setFileOverride(genConfig.isCover());
        // 实体属性 Swagger2 注解
//        gc.setSwagger2(genConfig.isSwagger());
//        // 设置基础resultMap
//        gc.setBaseResultMap(genConfig.isResultMap());
//        // 是否在xml中添加二级缓存配置
//        gc.setEnableCache(genConfig.isEnableCache());
//        时间类型对应策略
//        gc.setDateType()
//        开启 baseColumnList
//        gc.setBaseColumnList(genConfig.isBaseColumnList());
//        //设置主键id
//        gc.setIdType(IDTYPES[genConfig.getIdtype()].getIdType());
//
//        mpg.setGlobalConfig(gc);
//
//        mpg.setDataSource(dsc);


        String xmlName = "mapper";
//        mpg.setPackageInfo(pc);

        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // 生成自定义的Map obj.Result
//                Map<String, Object> map = new HashMap<>();
//                map.put("obj", pc.getParent() + ".obj");
//                String idType = "String";
//                map.put("camelTableName", underlineToCamel(tableName));
//                setMap(map);
//            }
//
//            @Override
//            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
//                if (fieldNameMap != null) {
//                    TableInfo tableInfo = (TableInfo) objectMap.get("table");
//                    tableInfo.setFieldNames(null);
//                    Iterator<TableField> iterator = tableInfo.getFields().iterator();
//                    while (iterator.hasNext()) {
//                        TableField field = iterator.next();
//                        if (!fieldNameMap.containsKey(field.getName().toUpperCase())) {
//                            iterator.remove();
//                        } else {
//                            field.setConvert(true);
//                        }
//                    }
//                }
//
//                return super.prepareObjectMap(objectMap);
//            }
//        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//        String templatePath = "/templates";
//        String mapperTemplatePath = templatePath + "/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(mapperTemplatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + genConfig.getModuleName() + "/src/main/resources/" + xmlName + "/"
//                    + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });

//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
//        templateConfig.setEntity(templatePath + "/entity.java");
//        templateConfig.setMapper(templatePath + "/mapper.java");
//        templateConfig.setEntityKt(templatePath + "/entity.kt");
//        templateConfig.setService(templatePath + "/service.java");
//        templateConfig.setService(templatePath + "/service.java");
//        templateConfig.setServiceImpl(templatePath + "/serviceImpl.java");
//        templateConfig.setController(templatePath + "/controller.java");
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);

        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        // entity 是否使用lombok
//        strategy.setEntityLombokModel(genConfig.isLombok());
        // 是否使用restController
//        strategy.setRestControllerStyle(genConfig.isRestController());
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(tableName);
//        strategy.setControllerMappingHyphenStyle(true);
//        // 表前缀
//        strategy.setTablePrefix(genConfig.getPrefix());
//        strategy.setFieldPrefix(fieldPrefix);
        // 是否使用自动填充

        // 乐观锁
//        strategy.setVersionFieldName("version_name");

//        strategy.setNameConvert(new INameConvert() {
//            @Override
//            public String entityNameConvert(TableInfo tableInfo) {
//                String[] stringArray = ArrayUtils.toStringArray(strategy.getTablePrefix().stream().toArray());
//                return NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategy.getNaming(), stringArray));
//            }
//
//            @Override
//            public String propertyNameConvert(TableField field) {
//                String name = field.getName();
//                if (fieldNameMap != null && fieldNameMap.containsKey(name.toUpperCase())) {
//                    name = fieldNameMap.get(name.toUpperCase());
//                }
//                String[] stringArray = ArrayUtils.toStringArray(strategy.getFieldPrefix().stream().toArray());
//                return processName(name, strategy.getNaming(), stringArray);
//            }
//        });

//        mpg.setStrategy(strategy);
//        mpg.execute();
        globalConfig.setTemplateEngine(new MyFreemarkerTemplateEngine(projectPath,genConfig));
        Generator generator = new Generator(dataSource, globalConfig);
        generator.generate();
    }
    private static final Pattern CAPITAL_MODE = Pattern.compile("^[0-9A-Z/_]+$");
    public static boolean isCapitalMode(String word) {
        return null != word && CAPITAL_MODE.matcher(word).matches();
    }

    public static String capitalFirst(String name) {
        return StringUtils.isNotBlank(name) ? name.substring(0, 1).toUpperCase() + name.substring(1) : "";
    }
    public static boolean isMixedMode(String word) {
        return matches(".*[A-Z]+.*", word) && matches(".*[/_]+.*", word);
    }
    public static String underlineToCamel(String name) {
        // 快速检查
        if (!org.apache.commons.lang3.StringUtils.isEmpty(name)) {
            // 没必要转换
            return StringUtils.EMPTY;
        }
        String tempName = name;
        // 大写数字下划线组成转为小写 , 允许混合模式转为小写
        if (isCapitalMode(name) || isMixedMode(name)) {
            tempName = name.toLowerCase();
        }
        StringBuilder result = new StringBuilder();
        // 用下划线将原始字符串分割
        String[] camels = tempName.split("_");
        // 跳过原始字符串中开头、结尾的下换线或双重下划线
        // 处理真正的驼峰片段
        Arrays.stream(camels).filter(camel -> !org.apache.commons.lang3.StringUtils.isEmpty(camel)).forEach(camel -> {
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel);
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(capitalFirst(camel));
            }
        });
        return result.toString();
    }

    // copy from   ConfigBuilder
//    private static String processName(String name, NamingStrategy strategy, String[] prefix) {
//        boolean removePrefix = false;
//        if (prefix != null && prefix.length != 0) {
//            removePrefix = true;
//        }
//        String propertyName;
//        if (removePrefix) {
//            if (strategy == NamingStrategy.underline_to_camel) {
//                // 删除前缀、下划线转驼峰
//                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
//            } else {
//                // 删除前缀
//                propertyName = NamingStrategy.removePrefix(name, prefix);
//            }
//        } else if (strategy == NamingStrategy.underline_to_camel) {
//            // 下划线转驼峰
//            propertyName = NamingStrategy.underlineToCamel(name);
//        } else {
//            // 不处理
//            propertyName = name;
//        }
//        return propertyName;
//    }
}
