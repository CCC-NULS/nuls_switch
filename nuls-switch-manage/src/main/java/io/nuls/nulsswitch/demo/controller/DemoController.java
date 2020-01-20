package io.nuls.nulsswitch.demo.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.base.BaseController;
import io.nuls.nulsswitch.common.utils.Result;
import io.nuls.nulsswitch.demo.domain.DemoDO;
import io.nuls.nulsswitch.demo.service.DemoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 基础表
 * </pre>
 * <small> 2018-07-27 23:38:24 | Aron</small>
 */
@Controller
@RequestMapping("/demo/demoBase")
@Profile("!prod")
public class DemoController extends BaseController {
	@Autowired
	private DemoService demoBaseService;
	
	@GetMapping()
	@RequiresPermissions("demo:demoBase:demoBase")
	String DemoBase(){
	    return "demo/demoBase/demoBase";
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public Result<DemoDO> list(@PathVariable Long id){
        return success(demoBaseService.selectById(id));
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("demo:demoBase:demoBase")
	public Result<Page<DemoDO>> list(DemoDO demoBaseDTO){
        Wrapper<DemoDO> wrapper = new EntityWrapper<DemoDO>().orderBy("id", false);
        wrapper.like("title", demoBaseDTO.getTitle());
        wrapper.like("content", demoBaseDTO.getContent());
        Page<DemoDO> page = demoBaseService.selectPage(getPage(DemoDO.class), wrapper);
        return success(page);
	}

	@GetMapping("/add")
	@RequiresPermissions("demo:demoBase:add")
	String add(){
	    return "demo/demoBase/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("demo:demoBase:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DemoDO demoBase = demoBaseService.selectById(id);
		model.addAttribute("demoBase", demoBase);
	    return "demo/demoBase/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加基础表数据")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("demo:demoBase:add")
	public Result<String> save( DemoDO demoBase){
		boolean insert = demoBaseService.insert(demoBase);
        return insert ? success() : Result.fail();
	}
	/**
	 * 修改
	 */
	@Log("更新基础表数据")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("demo:demoBase:edit")
	public Result<String>  update( DemoDO demoBase){
		boolean updateById = demoBaseService.updateById(demoBase);
		return updateById ? success() : Result.fail();
	}
	
	/**
	 * 删除
	 */
	@Log("删除基础表数据")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:remove")
	public Result<String>  remove( Long id){
		demoBaseService.deleteById(id);
        return success();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除基础表数据")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] ids){
		demoBaseService.deleteBatchIds(Arrays.asList(ids));
		return success();
	}



}
