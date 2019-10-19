package com.bdtd.card.registration.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stylefeng.guns.core.encrypt.rsa.RSAUtils;
import com.stylefeng.guns.core.util.ClassPathUtil;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.JsonUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.util.SystemUtil;

@Service
public class AuthManager {
    
    private static final String AUTH_FILENAME = "auth.crypt";
    
    @Value("${bdtd.auth}")
    private boolean auth = true;
    @Value("${bdtd.modules}")
    private String modules;
    private String moduleSeprator = ":";
    private String subModuleSeprator = "-";
    
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @SuppressWarnings("unchecked")
    public void doAuth() {
        if (!auth) {
            log.info("授权验证已关闭！");
            return;
        }
        
        String path = ClassPathUtil.projectPath();
        String authFile = path + AUTH_FILENAME;
        File file = new File(authFile);
        if (!file.exists()) {
            log.error("授权文件不存在！");
            System.exit(-1);
        }
        
        try {
            String authText = FileUtil.getFileText(authFile);
            String publicKey = getPubKey();
            authText = RSAUtils.publicDecrypt(authText, RSAUtils.getPublicKey(publicKey));
            Map<String, Object> map = JsonUtil.strToJson(authText, Map.class);
            
            String mac = (String) map.get("mac");
            if (StringUtil.isNullEmpty(mac)) {
                log.error("授权文件内容错误！");
                System.exit(-1);
            }
            
            if (!mac.equals(SystemUtil.getMac(null))) {
                log.error("mac地址错误，此电脑与授权电脑非同一台！");
                System.exit(-1);
            }
            
            String module = (String) map.get("module");
            if (StringUtil.isNullEmpty(module)) {
                log.error("未找到授权模块！");
                System.exit(-1);
            }
            
            checkModule(module);
            
            String company = (String) map.get("company");
            if (!StringUtil.isNullEmpty(company)) {
                company = "";
            }
            log.debug(String.format("%s 授权成功！", company));
                    
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("授权文件不存在！");
            System.exit(-1);
        }
    }

    private void checkModule(String module) {
        if (!StringUtil.isNullEmpty(module)) {
            
        }
    }
    
    private String getPubKey() throws UnsupportedEncodingException, IOException {
        InputStream in = AuthManager.class.getResourceAsStream("/pub.key");
        return new String(IOUtils.toByteArray(in), "utf-8");
    }
    
}
