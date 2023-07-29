package ${package.Controller};

import io.github.vino42.common.ServiceResponseResult;
import io.github.vino42.common.ResultMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import static io.github.vino42.common.ServiceResponseCodeEnum.ILLEGAL_ARGS;
<#if swagger2>
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * =====================================================================================
 *
 * @Created :   ${DATE} ${TIME}
 * @Compiler :  jdk 17
 * @Author :    ${author}
 * @Copyright :  ${author}
 * @Decription : ${table.comment!} 控制器
 * =====================================================================================
 */
<#if swagger2>
@Api(tags = "${entity}管理")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if camelTableNameStyle??>${camelTableName}<#else>${controllerMappingPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
  <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
  <#else>
public class ${table.controllerName} {
  </#if>

    @Autowired
    private ${table.serviceName} ${cfg.camelTableName}Service;

<#if swagger2>    @ApiOperation(value = "查询${entity}")</#if>
    @GetMapping(value = "/page")
    public ServiceResponseResult <IPage> get${entity}Page(Page<${entity}> page, ${entity} ${cfg.camelTableName}) {
        return ResultMapper.ok(${cfg.camelTableName}Service.page(page, Wrappers.query(${cfg.camelTableName})));
    }

<#if swagger2>    @ApiOperation(value = "新增${entity}")</#if>
    @PostMapping(value = "/add")
    public ServiceResponseResult create(@Valid @RequestBody ${entity} ${cfg.camelTableName}) {
        return ResultMapper.ok(${cfg.camelTableName}Service.save(${cfg.camelTableName}));
    }

<#if swagger2>    @ApiOperation(value = "修改${entity}")</#if>
    @PostMapping(value = "/update")
    public ServiceResponseResult update(@Valid @RequestBody ${entity} ${cfg.camelTableName}) {
        return ResultMapper.ok(${cfg.camelTableName}Service.updateById(${cfg.camelTableName}));
    }

    <#list table.fields as field>
         <#if field.keyFlag>
    <#if swagger2>@ApiOperation(value = "删除${entity}")</#if>
    @PostMapping(value = "/delete/{${field.propertyName}}")
    public ServiceResponseResult delete(@PathVariable ${field.propertyType} ${field.propertyName}) {
        return ResultMapper.ok(${cfg.camelTableName}Service.removeById(${field.propertyName}));
    }
    </#if>
</#list>
}
</#if>
