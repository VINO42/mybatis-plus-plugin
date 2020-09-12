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
package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
