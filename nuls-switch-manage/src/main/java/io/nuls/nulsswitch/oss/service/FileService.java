package io.nuls.nulsswitch.oss.service;

import io.nuls.nulsswitch.common.base.CoreService;
import io.nuls.nulsswitch.oss.domain.FileDO;

import java.io.File;

/**
 * <pre>
 * 文件上传
 * </pre>

 */
public interface FileService extends CoreService<FileDO> {

    /**
     * <pre>
     * 上传文件，默认路径为
     *      http:// + 七牛默认分配的域名 + / + 项目名 + / + 日期 + / + 文件名 + "-" + 时间戳 + "." + 后缀
     *      如：http://p6r7ke2jc.bkt.clouddn.com/ifast/20180406/cat001-123412412431.jpeg
     * </pre>
     *

     *
     * @param uploadBytes 文件字节数组
     * @param fileName    简单文件名，带后缀，如：mycat.png
     * @return
     */
    String upload(byte[] uploadBytes, String fileName);

    /**
     * @param file
     * @param fileName
     * @return
     * @author zhongweiyuan
     * @date 2019年05月24日 16:01:07
     */
    String upload(File file, String fileName);

}
