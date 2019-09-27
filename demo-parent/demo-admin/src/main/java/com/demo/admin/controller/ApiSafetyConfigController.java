package com.demo.admin.controller;

import com.laiai.core.Result;
import com.laiai.core.BaseController;
import com.demo.admin.model.ApiSafetyConfig;
import com.demo.admin.service.ApiSafetyConfigService;
import com.demo.admin.swagger.ApiSafetyConfigSwagger;
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
 * @author CodeGenerator
 * @date 2019-03-07 09:50:56
 */
@RestController
@RequestMapping("/api/safety/config")
public class ApiSafetyConfigController extends BaseController implements ApiSafetyConfigSwagger {
    @Resource
    private ApiSafetyConfigService apiSafetyConfigService;

    @PostMapping
    @Override
    public Result add(@RequestBody @Valid ApiSafetyConfig apiSafetyConfig,BindingResult result) throws IOException  {
    	ValidatorHelper.validate(result);
        apiSafetyConfigService.saveApiSafetyConfig(apiSafetyConfig);
        return success();
    }

    @DeleteMapping("/{id}")
    @Override
    public Result delete(@PathVariable Integer id) {
        apiSafetyConfigService.deleteById(id);
        return success();
    }

    @PutMapping
    @Override
    public Result update(@RequestBody ApiSafetyConfig apiSafetyConfig) {
        ValidatorHelper.notEmpty("id",apiSafetyConfig.getId());
        apiSafetyConfigService.update(apiSafetyConfig);
        return success();
    }

    @GetMapping("/{id}")
    @Override
    public Result view(@PathVariable Integer id) {
        ApiSafetyConfig apiSafetyConfig = apiSafetyConfigService.findById(id);
        return success(apiSafetyConfig);
    }


    @PostMapping("/page")
    @Override
    public Result pageList(@RequestBody ApiSafetyConfig model, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(current, pageSize);
        List<ApiSafetyConfig> list = apiSafetyConfigService.findList(model);
        PageInfo pageInfo = new PageInfo(list);
        return success(pageInfo);
    }
}
