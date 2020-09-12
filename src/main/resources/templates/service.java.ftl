/**
* =====================================================================================
*
* @Created :   ${DATE} ${TIME}
* @Compiler :  jdk 11
* @Author :    VINO
* @Email : 38912428@qq.com
* @Copyright : 长峰集团(http://www.cfyygf.com/)
* @Decription :
* =====================================================================================
*/
package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
