package io.nuls.nulsswitch.wxmp.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.base.BaseController;
import io.nuls.nulsswitch.common.utils.Result;
import io.nuls.nulsswitch.wxmp.domain.MpArticleDO;
import io.nuls.nulsswitch.wxmp.domain.MpConfigDO;
import io.nuls.nulsswitch.wxmp.service.MpArticleService;
import io.nuls.nulsswitch.wxmp.service.MpConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018-10-18 21:21:36 | Aron</small>
 */
@Controller
@RequestMapping("/wxmp/mpArticle/news")
public class MpArticleNewsController extends BaseController {
    @Autowired
    private MpArticleService mpArticleService;
    @Autowired
    private MpConfigService mpConfigService;

    @GetMapping()
    @RequiresPermissions("wxmp:mpArticleNews:mpArticle")
    String MpArticle() {
        return "wxmp/mpArticleNews/mpArticle";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("wxmp:mpArticleNews:mpArticle")
    public Result<Page<MpArticleDO>> list(MpArticleDO mpArticleDTO, String appId) {
        Wrapper<MpArticleDO> wrapper = new EntityWrapper<>(mpArticleDTO).eq("mpId", mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId()).orderBy("id", false);
        Page<MpArticleDO> page = mpArticleService.selectPage(getPage(MpArticleDO.class), wrapper);
        return success(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("wxmp:mpArticleNews:add")
    String add() {
        return "wxmp/mpArticleNews/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("wxmp:mpArticleNews:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        MpArticleDO mpArticle = mpArticleService.selectById(id);
        model.addAttribute("mpArticle", mpArticle);
        return "wxmp/mpArticleNews/edit";
    }

    @Log("添加")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("wxmp:mpArticleNews:add")
    public Result<String> save(MpArticleDO mpArticle, String appId) {
        mpArticle.setMpId(mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId());
        mpArticleService.insert(mpArticle);
        return success();
    }

    @Log("修改")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("wxmp:mpArticleNews:edit")
    public Result<String> update(MpArticleDO mpArticle) {
        boolean update = mpArticleService.updateById(mpArticle);
        return update ? success() : Result.fail();
    }

    @Log("删除")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpArticleNews:remove")
    public Result<String> remove(Long id) {
        mpArticleService.deleteById(id);
        return success();
    }

    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpArticleNews:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        mpArticleService.deleteBatchIds(Arrays.asList(ids));
        return success();
    }

}
