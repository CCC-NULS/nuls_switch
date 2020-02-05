package io.nuls.nulsswitch.oss.service.impl;

import io.nuls.nulsswitch.common.base.CoreServiceImpl;
import io.nuls.nulsswitch.common.component.oss.support.FileNameUtils;
import io.nuls.nulsswitch.common.component.oss.support.UploadServer;
import io.nuls.nulsswitch.common.config.IFastProperties;
import io.nuls.nulsswitch.common.utils.FileType;
import io.nuls.nulsswitch.oss.dao.FileDao;
import io.nuls.nulsswitch.oss.domain.FileDO;
import io.nuls.nulsswitch.oss.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

/**
 * <pre>
 * </pre>
 *

 */
@Service
@AllArgsConstructor
public class FileServiceImpl extends CoreServiceImpl<FileDao, FileDO> implements FileService {

    private IFastProperties ifastConfig;
    private UploadServer uploader;

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        String url = uploader.upload(uploadBytes, FileNameUtils.getFileName(fileName, ifastConfig));
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, new Date());
        super.insert(sysFile);
        return url;
    }

    @Override
    public String upload(File file, String fileName) {
        return upload(getBytes(file), fileName);
    }


    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
