package com.demo.member.service.impl;

import com.demo.member.mapper.ClockTargetMapper;
import com.demo.member.model.ClockTarget;
import com.demo.member.service.ClockTargetService;
import com.laiai.core.abstractbean.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @description:
 * @author zzj
 * @date 2019-08-08 08:31:53
 */
@Service
@Transactional
public class ClockTargetServiceImpl extends AbstractService<ClockTarget> implements ClockTargetService {
    @Resource
    private ClockTargetMapper clockTargetMapper;

}
