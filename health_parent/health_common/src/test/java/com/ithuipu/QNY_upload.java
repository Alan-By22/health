package com.ithuipu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/14---21:57
 * @描述信息
 */

public class QNY_upload {
    //1.测试上传
    @Test
    public void upload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //Configuration cfg = new Configuration(Zone.zone0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "4i5ZgF-Ou1aM2FhkCqOGdr7VsATjjdM8CEMPtwJc";
        String secretKey = "ALj4Qpea69dHTpBzsj7PNHqzxBteVU5VhLsl5lvN";
        String bucket = "ithuipu-1";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\11752\\Pictures\\Camera Roll\\u=1208614639,3032255268&fm=193&f=GIF.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "鸟巢.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
