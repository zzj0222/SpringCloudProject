package com.demo.member.controller;

import com.laiai.core.Result;
import com.laiai.core.BaseController;
import com.demo.member.model.ClockTarget;
import com.demo.member.service.ClockTargetService;
import com.demo.member.swagger.ClockTargetSwagger;
import org.springframework.validation.BindingResult;
import com.laiai.core.validator.ValidatorHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.io.IOException;
import javax.validation.Valid;



/**
 * @description:
 * @author zzj
 * @date 2019-08-08 08:31:53
 */
@RestController
@RequestMapping("/clock/target")
public class ClockTargetController extends BaseController implements ClockTargetSwagger {
    @Resource
    private ClockTargetService clockTargetService;

    @PostMapping
    @Override
    public Result add(@RequestBody @Valid ClockTarget clockTarget,BindingResult result) throws IOException  {
    	ValidatorHelper.validate(result);
        clockTargetService.save(clockTarget);
        return success();
    }

    @DeleteMapping("/{id}")
    @Override
    public Result delete(@PathVariable Integer id) {
        clockTargetService.deleteById(id);
        return success();
    }

    @PutMapping
    @Override
    public Result update(@RequestBody ClockTarget clockTarget) {
        ValidatorHelper.notEmpty("id",clockTarget.getId());
        clockTargetService.update(clockTarget);
        return success();
    }

    @GetMapping("/{id}")
    @Override
    public Result<ClockTarget> view(@PathVariable Integer id) {
        ClockTarget clockTarget = clockTargetService.findById(id);
        return success(clockTarget);
    }


    @PostMapping("/page")
    @Override
    public Result<PageInfo<List<ClockTarget>>> pageList(@RequestBody ClockTarget model, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(current, pageSize);
        List<ClockTarget> list = clockTargetService.findList(model);
        PageInfo pageInfo = new PageInfo(list);
        return success(pageInfo);
    }

    public static void main(String[] args) {
        System.out.println(5000 - System.currentTimeMillis()%1000);
        System.out.println( System.currentTimeMillis()%1000);
    }
}
