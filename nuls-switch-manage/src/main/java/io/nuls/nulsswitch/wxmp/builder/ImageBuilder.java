package io.nuls.nulsswitch.wxmp.builder;

import io.nuls.nulsswitch.wxmp.service.WeixinService;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * <pre>
 * </pre>
 * 

 */
public class ImageBuilder extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(String mediaId, WxMpXmlMessage wxMessage, WeixinService service) {

        WxMpXmlOutImageMessage m = WxMpXmlOutMessage.IMAGE().mediaId(mediaId).fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();

        return m;
    }

}
