package ${package.Service};

import ${superServiceClassPackage};
import ${package.Entity}.${entity};

/**
 * =====================================================================================
 *
 * @Created :   ${DATE} ${TIME}
 * @Compiler :  jdk 17
 * @Author :    ${author}
 * @Copyright : ${author}
 * @Decription : ${table.comment} 服务类
 * =====================================================================================
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
