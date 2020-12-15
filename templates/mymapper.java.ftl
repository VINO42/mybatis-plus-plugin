package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
 * =====================================================================================
 *
 * @Created :   ${DATE} ${TIME}
 * @Compiler :  jdk 11
 * @Author :    ${author}
 * @Email : 38912428@qq.com
 * @Copyright : ${author}
 * @Decription : ${table.comment} Mapper 接口
 * =====================================================================================
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
