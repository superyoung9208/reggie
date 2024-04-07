package org.reggie.app.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;

public class SMSUtils {

    private static final String serverIp = "app.cloopen.com";

    private static final String serverPort = "8883";

    private static final String accountSId = "8a216da867e881cb0167f7d91b1009ac";

    private static final String accountToken = "288dbd51c49143b2aa84f677066eecd1";

    private static final String appId = "8a216da867e881cb0167f7d91b6a09b3";

    private static final String templateId = "1";


    /***
     * 使用容联云通讯sdk发送短信
     */
    public static void sendMessage(String phone,String code){
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String[] params = {code, "60"};
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone,templateId,params);
        if("000000".equals(result.get("statusCode"))){
            System.out.println("短信发送成功");
        }else{
            System.out.println("短信发送失败");
        }
   }

   public static void main(String[] args) {
       sendMessage("17634470910", "1234");
   }

}
