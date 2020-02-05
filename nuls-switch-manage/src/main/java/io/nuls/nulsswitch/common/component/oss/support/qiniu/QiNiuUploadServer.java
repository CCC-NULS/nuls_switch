package io.nuls.nulsswitch.common.component.oss.support.qiniu;

import io.nuls.nulsswitch.common.component.oss.support.UploadServer;
import io.nuls.nulsswitch.common.exception.IFastException;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Region;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 七牛对象存储服务
 * </pre>
 * 

 */
public class QiNiuUploadServer implements UploadServer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadManager uploadManager;
    private Configuration cfg;
    private QiNiuOSSProperties config;
    private QiNiuTokenInMemory qiNiuToken;


    public QiNiuUploadServer(QiNiuOSSProperties config, Region zone, QiNiuTokenInMemory qiNiuToken) {
        cfg = new Configuration(zone);
        uploadManager = new UploadManager(cfg);
        this.config = config;
        this.qiNiuToken = qiNiuToken;
    }

    @Override
    public String upload(byte[] bytes, String fileName) {
        String token = qiNiuToken.get();
        try {
            uploadManager.put(bytes, fileName, token);
            String fileURL = this.config.getAccessURL() + fileName;
            log.info("上传成功，url:{}", fileURL);
            return fileURL;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            throw new IFastException(EnumErrorCode.FileUploadError.getCodeStr());
        }
    }

}
