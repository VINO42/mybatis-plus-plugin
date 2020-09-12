package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * =====================================================================================
 *
 * @Created :   ${DATE} ${TIME}
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : 长峰集团(http://www.cfyygf.com/)
 * @Decription : ${table.comment!} 服务实现类
 * =====================================================================================
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

}
</#if>
