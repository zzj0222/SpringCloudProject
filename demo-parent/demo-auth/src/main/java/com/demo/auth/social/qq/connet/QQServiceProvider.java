package com.demo.auth.social.qq.connet;

import com.demo.auth.social.qq.api.QQ;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author zzj
 * @create 2018-12-03 19:58
 **/
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String s) {
        return null;
    }
}
