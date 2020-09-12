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
package ${package.Controller};

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import com.cfyy.common.WrapMapper;
import com.cfyy.common.ServiceResultWrapper;

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
 * <p>
 * ${table.comment!} 控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(tags = "${entity}管理")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if camelTableNameStyle??>${camelTableName}<#else>${table.entityPath}</#if>")
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
    public ServiceResult<IPage> get${entity}Page(Page<${entity}> page, ${entity} ${cfg.camelTableName}){
        return WrapMapper.ok(${cfg.camelTableName}Service.page(page,Wrappers.query(${cfg.camelTableName})) );
    }

<#if swagger2>    @ApiOperation(value = "新增${entity}")</#if>
    @PostMapping(value = "/add")
    public ServiceResult create(@Valid @RequestBody ${entity} ${cfg.camelTableName}, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return WrapMapper.error(GsonUtils.toJsonString(fieldErrors));
        }
        return WrapMapper.ok(${cfg.camelTableName}Service.save(${cfg.camelTableName}));
    }

<#if swagger2>    @ApiOperation(value = "修改${entity}")</#if>
    @PutMapping(value = "/update")
    public ServiceResult update(@Valid @RequestBody ${entity} ${cfg.camelTableName}, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return WrapMapper.error(GsonUtils.toJsonString(fieldErrors));
         }
        return nWrapMapper.ok(${cfg.camelTableName}Service.updateById(${cfg.camelTableName}));
    }

    <#list table.fields as field>
         <#if field.keyFlag>
    <#if swagger2>@ApiOperation(value = "删除${entity}")</#if>
    @DeleteMapping(value = "/delete/{${field.propertyName}}")
    public ServiceResult delete(@PathVariable ${field.propertyType} ${field.propertyName}){
        return WrapMapper.ok(${cfg.camelTableName}Service.removeById(${field.propertyName}));
    }
         </#if>
    </#list>
}
</#if>
