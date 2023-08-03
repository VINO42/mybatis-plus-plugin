package com.baomidou.plugin.idea.mybatisx.codegenerator.utils;


import com.baomidou.plugin.idea.mybatisx.codegenerator.MysqlUtil;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.GenConfig;
import com.baomidou.plugin.idea.mybatisx.util.Constants;
import com.google.gson.Gson;
import com.mybatisflex.core.util.DateUtil;
import io.github.vino42.Generator;
import io.github.vino42.config.ColumnConfig;
import io.github.vino42.config.EntityConfig;
import io.github.vino42.config.GlobalConfig;
import io.github.vino42.config.TableDefConfig;
import io.github.vino42.template.impl.EnjoyTemplate;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static com.mybatisflex.core.util.DateUtil.datetimePattern;
import static java.util.regex.Pattern.matches;

/**
 * 代码生成
 * // todo 生成Result类
 */
public class GenUtil {


    public static void main(String[] args) {
        String json = "{\"rootFolder\":\"G:/Codes/GithubCodes/mybatis-plus-plugin\",\"pack\":\"io.github.vino42\",\"moduleName\":\"mymodule123\",\"prefix\":\"\",\"cover\":true,\"author\":\"vino\",\"isLombok\":true,\"isSwagger\":true,\"isRestController\":true,\"isResultMap\":true,\"isFill\":true,\"templatePath\":\"/flextemplates\",\"entityName\":\"entity\",\"mapperName\":\"mapper\",\"controllerName\":\"controller\",\"serviceName\":\"service\",\"serviceImplName\":\"service.impl\",\"idtype\":1,\"isEnableCache\":true,\"isBaseColumnList\":true,\"isAuthor\":false,\"isEntity\":true,\"isMapper\":true,\"isController\":true,\"isService\":true,\"isServiceImpl\":true}";

        Gson gson = new Gson();
        GenConfig genConfig = gson.fromJson(json, GenConfig.class);
        String tableName = "application";

        GenUtil.generatorCode(tableName, genConfig, null, null);
    }

    public static void generatorCode(String tableName, GenConfig genConfig, Map<String, String> fieldNameMap, String fieldPrefix) {
        // 数据源配置


//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/lll?useSSL=false&serverTimezone=UTC");
//        dataSource.setUsername("root");
//        dataSource.setPassword("nopasswd");
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setTemplateEngine(new EnjoyTemplate());

        //设置表前缀和只生成哪些表
        if (genConfig.isRestController()) {
            globalConfig.setCustomConfig("restControllerStyle", genConfig.isRestController());
        }
        //包设置
        String projectPath = genConfig.getRootFolder();
        globalConfig.getPackageConfig().setSourceDir(projectPath + File.separator + genConfig.getModuleName() + "/src/main/java");
        String pack = genConfig.getPack();

        globalConfig.setCustomConfig("genController", genConfig.isController());
        globalConfig.setCustomConfig("genResultMap ", genConfig.isResultMap());
        globalConfig.setCustomConfig("genBaseColumn", genConfig.isBaseColumnList());

        globalConfig.setCustomConfig("DATE", DateUtil.toString(new Date(), datetimePattern));
        globalConfig.setCustomConfig("entitySerialVersionUID", true);
        globalConfig.setCustomConfig("enableMapperCache", genConfig.isEnableCache());
        globalConfig.setCustomConfig("baseResultMap", genConfig.isResultMap());
        globalConfig.setCustomConfig("baseColumnList", genConfig.isBaseColumnList());

        //文档设置
        globalConfig.getJavadocConfig().setAuthor(genConfig.getAuthor());


        //控制器生成配置
        if (genConfig.isController()) {
            //生成controller的配置
            globalConfig.setControllerGenerateEnable(true);
            globalConfig.setServiceOverwriteEnable(genConfig.isCover());
            globalConfig.getJavadocConfig().setControllerPackage(pack + Constants.DOT + genConfig.getControllerName());
            globalConfig.getPackageConfig().setControllerPackage(pack + Constants.DOT + genConfig.getControllerName());
            globalConfig.getTemplateConfig().setController("/templates/controller.tpl");
            globalConfig.enableController().setRestStyle(true);
        }
        System.out.println("ctrl生成");
        //service生成配置
        if (genConfig.isService()) {
            globalConfig.setServiceGenerateEnable(true);
            globalConfig.setServiceOverwriteEnable(genConfig.isCover());
            globalConfig.getJavadocConfig().setServicePackage(pack + Constants.DOT + genConfig.getServiceName());
            globalConfig.getPackageConfig().setServicePackage(pack + Constants.DOT + genConfig.getServiceName());
            globalConfig.getTemplateConfig().setService("/templates/service.tpl");
        }
        System.out.println("service生成配置");
        //service生成配置
        if (genConfig.isServiceImpl()) {
            globalConfig.setServiceImplGenerateEnable(true);
            globalConfig.setServiceImplOverwriteEnable(genConfig.isCover());
            globalConfig.getJavadocConfig().setServiceImplPackage(pack + Constants.DOT + genConfig.getServiceImplName());
            globalConfig.getPackageConfig().setServiceImplPackage(pack + Constants.DOT + genConfig.getServiceImplName());
            globalConfig.getTemplateConfig().setServiceImpl("/templates/serviceImpl.tpl");
            globalConfig.enableServiceImpl().setClassSuffix("Impl");
        }
        System.out.println("serviceImpl生成配置");

        //mapper生成配置
        if (genConfig.isMapper()) {
            globalConfig.setMapperGenerateEnable(true);
            globalConfig.setMapperOverwriteEnable(genConfig.isCover());
            globalConfig.setMapperXmlOverwriteEnable(genConfig.isCover());

            globalConfig.getJavadocConfig().setMapperPackage(pack + Constants.DOT + genConfig.getMapperName());
            globalConfig.getPackageConfig().setMapperPackage(pack + Constants.DOT + genConfig.getMapperName());
            globalConfig.getTemplateConfig().setMapper("/templates/mapper.tpl");

            globalConfig.setMapperXmlGenerateEnable(true);
            globalConfig.setMapperXmlOverwriteEnable(genConfig.isCover());
            globalConfig.getPackageConfig().setMapperXmlPath(projectPath + File.separator + genConfig.getModuleName() + "/src/main/resources/mapper");
            globalConfig.getTemplateConfig().setMapperXml("/templates/mapperXml.tpl");
        }
        System.out.println("mapper生成配置");


        //entity生成配置
        if (genConfig.isEntity()) {
            globalConfig.setEntityGenerateEnable(true);
            globalConfig.setEntityOverwriteEnable(genConfig.isCover());
            globalConfig.getJavadocConfig().setEntityPackage(pack + Constants.DOT + genConfig.getEntityName());
            globalConfig.getPackageConfig().setEntityPackage(pack + Constants.DOT + genConfig.getEntityName());
            globalConfig.getTemplateConfig().setEntity("/templates/entity.tpl");
            globalConfig.setEntityClassSuffix("Entity");

            globalConfig.setTableDefGenerateEnable(true);
            globalConfig.setTableDefPropertiesNameStyle(TableDefConfig.NameStyle.UPPER_CAMEL_CASE);
            globalConfig.setTableDefOverwriteEnable(genConfig.isCover());
            globalConfig.getPackageConfig().setTableDefPackage(pack + Constants.DOT + "def");
            globalConfig.getJavadocConfig().setTableDefPackage(pack + Constants.DOT + "def");
            globalConfig.setTableDefPropertiesNameStyle(TableDefConfig.NameStyle.UPPER_CASE);
            globalConfig.getTemplateConfig().setTableDef("/templates/tableDef.tpl");
            globalConfig.enableTableDef();
        }
        System.out.println("entity生成配置");

        globalConfig.getJavadocConfig().setTableDefPackage(genConfig.getPack());
        //swagger配置
        if (genConfig.isSwagger()) {
            globalConfig.setEntityWithSwagger(genConfig.isSwagger());
            globalConfig.getEntityConfig().setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
        }
        if (genConfig.isFill()) {
            ColumnConfig columnConfig = new ColumnConfig();
            columnConfig.setOnUpdateValue("update_time");
            columnConfig.setOnInsertValue("create_time");
            globalConfig.getStrategyConfig().setColumnConfig(columnConfig);
        }
        //lombok配置
        if (genConfig.isLombok()) {
            globalConfig.enableEntity().setWithLombok(true);
        }

        globalConfig.getPackageConfig().setBasePackage(genConfig.getPack());
        globalConfig.getPackageConfig().setTableDefPackage(pack + Constants.DOT + genConfig.getEntityName());
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


        Generator generator = new Generator(MysqlUtil.getInstance().getDataSource(), globalConfig);
        System.out.println("开始生成");


        generator.generate();

//        Messages.showInfoMessage("生成完毕！请关闭窗口", "Mybatis Flex");

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
}
