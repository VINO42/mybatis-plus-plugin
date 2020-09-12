package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * =====================================================================================
 *
 * @Created :   ${DATE} ${TIME}
 * @Compiler :  jdk 11
 * @Author :    ${author}
 * @Copyright : 长峰集团(http://www.cfyygf.com/)
 * @Decription : ${table.comment} 服务类
 * =====================================================================================
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
