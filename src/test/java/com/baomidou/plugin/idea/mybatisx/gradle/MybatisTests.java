package com.baomidou.plugin.idea.mybatisx.gradle;


import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.GenConfig;
import com.baomidou.plugin.idea.mybatisx.codegenerator.utils.GenUtil;

public class MybatisTests {

    //    @Test
    public void testMysql() {
        doGen();
    }

    public void doGen() {

        String tableName = "application";
        String projectPath = System.getProperty("user.dir");
        GenConfig genConfig = new GenConfig();
        genConfig.setRootFolder(projectPath);
        genConfig.setAuthor("jobob");
        genConfig.setModuleName("my-test");
        genConfig.setId(1L);
        genConfig.setPack("org.py.modules.ShowTableInfo");
        genConfig.setAuthor("py");
        genConfig.setCover(true);

        genConfig.setEnableCache(false);
        genConfig.setBaseColumnList(true);
        genConfig.setResultMap(true);
        genConfig.setDbUrl("jdbc:mysql://localhost:3306/lll?useSSL=false&serverTimezone=UTC");
        genConfig.setUsername("root");
        genConfig.setPassword("nopasswd");
        genConfig.setController(true);
        genConfig.setEntity(true);
        genConfig.setMapper(true);
        genConfig.setService(true);
        genConfig.setServiceImpl(true);

        GenUtil.generatorCode(tableName, genConfig, null, null);
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("jobob");
//        gc.setOpen(false);
//        // gc.setSwagger2(true); 实体属性 Swagger2 注解
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/mysql?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(null);
//        pc.setMapper("dao");
//        pc.setParent("com.baomidou.ant");
//        mpg.setPackageInfo(pc);
//
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        mpg.setCfg(cfg);
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        // 配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setEntity("templates/entity2.java");
//        // templateConfig.setService();
//        // templateConfig.setController();
//
////        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude("user");
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
    }
}
