package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.RoleEnum;
import com.example.entity.Admin;
import com.example.entity.Notice;
import com.example.mapper.AdminMapper;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author Arrebol
 * @date 2024/3/14
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private AdminMapper adminMapper;

    /**
     * 新增
     */
    public void add(Notice notice) {
        notice.setTime(DateUtil.today());
        Map<String, Object> claims = ThreadLocalUtil.get();
        String role = claims.get("role").toString();
        String userId = claims.get("userId").toString();
        Admin admin = null;
        if (RoleEnum.ADMIN.name().equals(role)) {
            admin = adminMapper.selectById(Integer.valueOf(userId));
        }
        if (ObjectUtil.isNotNull(admin)) {
            notice.setUser(admin.getUsername());
        }
        noticeMapper.insert(notice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            noticeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateNotice(Notice notice) {
        noticeMapper.updateById(notice);
    }

    /**
     * 根据ID查询
     */
    public Notice selectById(Integer id) {
        return noticeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * 分页查询
     */
    public Page<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (notice.getId() != null) {
            wrapper.eq(Notice::getId, notice.getId());
        }
        if (notice.getTitle() != null) {
            wrapper.like(Notice::getTitle, notice.getTitle());
        }
        if (notice.getContent() != null) {
            wrapper.like(Notice::getContent, notice.getContent());
        }
        if (notice.getTime() != null) {
            wrapper.like(Notice::getTime, notice.getTime());
        }
        if (notice.getUser() != null) {
            wrapper.like(Notice::getTime, notice.getUser());
        }
        wrapper.orderByDesc(Notice::getTime);
        Page<Notice> result = noticeMapper.selectPage(page, wrapper);
        return result;
    }

}
