package com.demo.admin.swagger;
import com.laiai.core.Result;
import com.demo.admin.model.ApiSafetyConfig;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import java.io.IOException;



/**
 * Created by CodeGenerator on 2019-03-07 09:50:56.
 */
@Api(tags = "api安全配置表管理")
public interface ApiSafetyConfigSwagger {

     @ApiOperation(value="新增", notes="新增ApiSafetyConfig")
     public Result add(ApiSafetyConfig apiSafetyConfig, BindingResult result) throws IOException;


     @ApiOperation(value="删除", notes="以主键id删除ApiSafetyConfig")
     public Result delete(@PathVariable Integer id);


     @ApiOperation(value="更新", notes="更新apiSafetyConfig")
     public Result update(@RequestBody ApiSafetyConfig apiSafetyConfig);


     @ApiOperation(value="详情", notes="获取详情")
     public Result view(@PathVariable Integer id);


     @ApiOperation(value="按条件及分页查找", notes="按条件及分页查找")
     public Result pageList(@RequestBody ApiSafetyConfig obj, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize);
}
