package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Help;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.req.HelpDTO;
import com.example.vo.req.HelpQueryDTO;

import java.util.List;

public interface HelpService extends IService<Help> {

    void add(HelpDTO helpDTO);

    void updateHelp(Help help);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Help selectById(Integer id);

    List<Help> selectAll(Help help);

    Page<Help> selectPage(Help help, Integer pageNum, Integer pageSize);

    Page<Help> selectFrontPage(Help help, HelpQueryDTO helpQueryDTO);

}
