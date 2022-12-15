package com.skye.libra.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.skye.libra.model.GhMenu;
import com.skye.libra.dao.GhMenuMapper;
import com.skye.libra.service.GhMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 实现类
 * TODO
 * @author gaih
 * @since 2022-12-06 18:30:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GhMenuServiceImpl extends ServiceImpl<GhMenuMapper, GhMenu> implements GhMenuService {

}
