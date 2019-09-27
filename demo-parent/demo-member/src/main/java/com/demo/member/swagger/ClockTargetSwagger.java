package com.demo.member.swagger;
import com.laiai.core.Result;
import com.demo.member.model.ClockTarget;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import java.io.IOException;



/**
 * @description:
 * @author zzj
 * @date 2019-08-08 08:31:53
 */
@Api(tags = "打卡目标表管理")
public interface ClockTargetSwagger {

     @ApiOperation(value="新增", notes="新增ClockTarget")
     public Result add(ClockTarget clockTarget, BindingResult result) throws IOException;


     @ApiOperation(value="删除", notes="以主键id删除ClockTarget")
     public Result delete(@PathVariable Integer id);


     @ApiOperation(value="更新", notes="更新clockTarget")
     public Result update(@RequestBody ClockTarget clockTarget);


     @ApiOperation(value="详情", notes="获取详情")
     public Result view(@PathVariable Integer id);


     @ApiOperation(value="按条件及分页查找", notes="按条件及分页查找")
     public Result pageList(@RequestBody ClockTarget obj, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize);
}
