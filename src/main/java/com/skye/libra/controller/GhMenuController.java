package com.skye.libra.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.skye.libra.dto.GhMenuPageDto;
import com.skye.libra.model.GhMenu;
import com.skye.libra.service.GhMenuService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * controller
 * TODO
 * @author gaih
 * @since 2022-12-06 18:30:19
 */
@RestController
@RequestMapping("ghMenu")
public class GhMenuController {
    /**
     * 自动注入
     */
    @Resource
    private GhMenuService ghMenuService;

    /**
     * 分页查询
     *
     * @param ghMenuPageDto 
     * @return 
     */
    @PostMapping("selectAllByPage")
    public Object selectAllByPage(@RequestBody  GhMenuPageDto ghMenuPageDto) {
		Page<GhMenu> page = new Page<>(ghMenuPageDto.getCurrent(), ghMenuPageDto.getSize());
        return this.ghMenuService.page(page, new QueryWrapper<>(ghMenuPageDto.getGhMenu()));
    }

    /**
     * ͨ根据id查询单条数据
     *
     * @param id 主键id
     * @return 
     */
    @GetMapping("selectOne/{id}")
    public Object selectOne(@PathVariable Serializable id) {
        return this.ghMenuService.getById(id);
    }

    /**
     * 新增
     *
     * @param ghMenu 
     * @return
     */
    @PostMapping("insert")
    public Object insert(@RequestBody  GhMenu ghMenu) {
        return this.ghMenuService.save(ghMenu);
    }

    /**
     *  修改/更新
     *
     * @param ghMenu 
     * @return
     */
    @PostMapping("update")
    public Object update(@RequestBody  GhMenu ghMenu) {
        return this.ghMenuService.updateById(ghMenu);
    }

    /**
     * 根据id批量删除
     *
     * @param idList
     * @return 
     */
    @PostMapping("delete")
    public Object delete(@RequestParam("idList")  List<String> idList) {
        return this.ghMenuService.removeByIds(idList);
    }
    
    /**
     * 批量新增
     * @param ghMenuList 
     * @return 
     */
    @PostMapping("insertBatch")
    public Object insert(@RequestBody  List<GhMenu> ghMenuList) {
        return this.ghMenuService.saveBatch(ghMenuList);
    }

    
    /**
     * 全部查询
     *
     * @param ghMenu 
     * @return 
     */
    @PostMapping("selectAll")
    public Object selectAll(@RequestBody  GhMenu ghMenu) {
        QueryWrapper<GhMenu> ghMenuQueryWrapper = new QueryWrapper<>(ghMenu);
        return this.ghMenuService.list(ghMenuQueryWrapper);
    }
}
