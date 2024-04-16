package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Help;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.HelpService;
import com.example.mapper.HelpMapper;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.HelpDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HelpServiceImpl extends ServiceImpl<HelpMapper, Help> implements HelpService {

    @Resource
    private HelpMapper helpMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(HelpDTO helpDTO) {
        Help help = new Help();
        BeanUtil.copyProperties(helpDTO, help);
        help.setTime(DateUtil.now());
        String userId = ThreadLocalUtil.getUserId();
        help.setUserId(Integer.parseInt(userId));
        help.setStatus("待审核");
        help.setSolved("未解决");
        helpMapper.insert(help);
    }

    @Override
    public void deleteById(Integer id) {
        helpMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        helpMapper.deleteBatchIds(ids);
    }

    @Override
    public Help selectById(Integer id) {
        return helpMapper.selectById(id);
    }

    @Override
    public List<Help> selectAll(Help help) {
        return helpMapper.selectAll(help);
    }

    @Override
    public Page<Help> selectPage(Help help, Integer pageNum, Integer pageSize) {
        Page<Help> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Help> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(help.getTitle()), Help::getTitle, help.getTitle());
        Page<Help> result = helpMapper.selectPage(page, wrapper);
        List<Help> records = result.getRecords();
        List<Help> collect = records.stream().peek(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getName());
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }
}




