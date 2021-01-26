import com.iflytek.client.IatClient;
import com.iflytek.client.IatSessionParam;
import com.iflytek.client.SessionResponse;
import com.iflytek.iat.sdk.core.IatResult;
import com.iflytek.util.IatUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestIat {


    @Test
    public void test1() {
        IatSessionParam iatSessionParam = new IatSessionParam();
        iatSessionParam.setSid(IatUtil.getUUID());
        iatSessionParam.setAue("raw");
        iatSessionParam.setRst("json");
        //iatSessionParam.setRse("utf8");
        //iatSessionParam.setEos("20000");
        //iatSessionParam.setDwa("wpgs");
        //iatSessionParam.setWbest("");
        //iatSessionParam.setWbestonlyper("");
        iatSessionParam.setRate("8k");
        iatSessionParam.setHotword("");
        iatSessionParam.setEngine_param("");
        //iatSessionParam.setPgs_mode("");
        //iatSessionParam.setOpenwp("");
        iatSessionParam.setSamplesInfo("测试音频");


        StringBuilder sb = new StringBuilder();
        final CountDownLatch finishLatch = new CountDownLatch(1);

        IatClient iatClient = new IatClient("172.31.161.96:8197", iatSessionParam);


        SessionResponse sessionResponse = new SessionResponse() {

            @Override
            public void onCallback(IatResult iatResult) {
                log.info("是不是结束了：" + iatResult.getEndFlag() + "返回结果：" + iatResult.getAnsStr() +
                        "如果出错了，返回的错误编码是:" + iatResult.getErrCode() + "错误描述是:" + iatResult.getErrStr());
                sb.append(iatResult.getAnsStr());
            }

            @Override
            public void onError(Throwable throwable) {
                finishLatch.countDown();
                log.info(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
                System.out.println("completed");
            }
        };


        iatClient.connect(sessionResponse);

        FileInputStream bis = null;
        try {
            bis = new FileInputStream(new File("E:\\audio\\4s.wav"));

            byte[] buf = new byte[1280];

            while (bis.read(buf) != -1) {
                Thread.sleep(40);//每次间隔40ms
                iatClient.postAudioData(buf);
            }
            iatClient.disConnect();

            //等待引擎返回结果
            finishLatch.await(1, TimeUnit.MINUTES);
            log.info("结果为："+sb.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bis!=null)
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
