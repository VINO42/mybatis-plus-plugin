package ${package.Mapper};

import ${package.Entity}.${entity}EntityEntity;
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
interface ${table.mapperName} : ${superMapperClass}<${entity}EntityEntity>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}EntityEntity> {

}
</#if>
