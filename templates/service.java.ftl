package ${package.Service};

import ${package.Entity}.${entity}Entity;
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
interface ${table.serviceName} : ${superServiceClass}<${entity}Entity>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}Entity> {

}
</#if>
