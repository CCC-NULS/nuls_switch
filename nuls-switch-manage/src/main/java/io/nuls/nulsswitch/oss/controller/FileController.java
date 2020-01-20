package io.nuls.nulsswitch.oss.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.base.BaseController;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import io.nuls.nulsswitch.common.utils.Result;
import io.nuls.nulsswitch.oss.domain.FileDO;
import io.nuls.nulsswitch.oss.service.FileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * <pre>
 * 文件上传
 * </pre>
 * <p>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {
    
    @Autowired
    private FileService sysFileService;
    
    @GetMapping()
    @RequiresPermissions("oss:file:file")
    String sysFile(Model model) {
        return "common/file/file";
    }
    
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("oss:file:list")
    public Result<Page<FileDO>> list(FileDO fileDTO) {
        Wrapper<FileDO> wrapper = new EntityWrapper<FileDO>(fileDTO);
        Page<FileDO> page = sysFileService.selectPage(getPage(FileDO.class), wrapper);
        return Result.ok(page);
    }
    
    @GetMapping("/add")
    @RequiresPermissions("oss:file:add")
    String add() {
        return "common/sysFile/add";
    }
    
    @GetMapping("/edit")
    @RequiresPermissions("oss:file:update")
    String edit(Long id, Model model) {
        FileDO sysFile = sysFileService.selectById(id);
        model.addAttribute("sysFile", sysFile);
        return "common/sysFile/edit";
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("oss:file:info")
    public Result<FileDO> info(@PathVariable("id") Long id) {
        FileDO sysFile = sysFileService.selectById(id);
        return Result.ok(sysFile);
    }
    
    /**
     * 保存
     */
    @Log("添加文件")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("oss:file:add")
    public Result<String> save(FileDO sysFile) {
        sysFileService.insert(sysFile);
        return Result.ok();
    }
    
    /**
     * 修改
     */
    @Log("更新文件")
    @RequestMapping("/update")
    @RequiresPermissions("oss:file:update")
    public Result<String> update(@RequestBody FileDO sysFile) {
        sysFileService.updateById(sysFile);
        return Result.ok();
    }
    
    /**
     * 删除
     */
    @Log("删除文件")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("oss:file:remove")
    public Result<String> remove(Long id) {
        sysFileService.deleteById(id);
        return Result.ok();
    }
    
    /**
     * 删除
     */
    @Log("批量删除文件")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("oss:file:remove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysFileService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
    
    @Log("上传文件")
    @ResponseBody
    @PostMapping("/upload")
    @RequiresPermissions("oss:file:add")
    Result<String> upload(@RequestParam("file") MultipartFile file) {
        String url = "";
        try {
            url = sysFileService.upload(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.build(EnumErrorCode.FileUploadGetBytesError.getCode(),
                    EnumErrorCode.FileUploadGetBytesError.getMsg());
        }
        return Result.ok(url);
    }
    
}
