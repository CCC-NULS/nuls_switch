package io.nuls.nulsswitch.wxmp.builder;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuls.nulsswitch.common.utils.SpringContextHolder;
import io.nuls.nulsswitch.wxmp.domain.MpArticleDO;
import io.nuls.nulsswitch.wxmp.service.MpArticleService;
import io.nuls.nulsswitch.wxmp.service.WeixinService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <small> 2018年6月13日 | Aron</small>
 */
public class NewsBuilder extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(String keyword, WxMpXmlMessage wxMessage, WeixinService service) {
        List<WxMpXmlOutNewsMessage.Item> items = SpringContextHolder.getBean(MpArticleService.class).selectList(new EntityWrapper<>(MpArticleDO.builder().keyword(keyword).msgtype("news").build())).stream().map(article -> {
            WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
            item.setDescription(article.getIntroduct());
            item.setPicUrl(article.getImgurl());
            if(StringUtils.isNotBlank(article.getUrl())){
                item.setUrl(article.getUrl());
            }
            item.setTitle(article.getTitle());
            return item;
        }).collect(Collectors.toList());
        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().articles(items).fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        return m;
    }

}
