package ${package.Mapper};

import ${package.Entity}.${entity}Entity;
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
interface ${table.mapperName} : ${superMapperClass}<${entity}Entity>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}Entity> {

}
</#if>
