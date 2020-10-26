package com.adongs.model;

import com.adongs.api.BuildOutput;
import com.adongs.http.HttpReques;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/23 3:15 下午
 * @modified By
 */
public class TimedTask extends TimerTask {

    private BuildOutput buildOutput;

    private HttpReques httpReques;

    private long size = 0;

    public TimedTask(BuildOutput buildOutput, HttpReques httpReques) {
        this.buildOutput = buildOutput;
        this.httpReques = httpReques;
    }

    @Override
    public void run() {
        if (buildOutput.discontinue()){
            this.cancel();
            return;
        }
        Response response = null;
        try {
            Map<String,String> param = new HashMap<>();
            param.put("start",String.valueOf(size));
            response = httpReques.getResponse("/job/" + buildOutput.jobName() + "/" + buildOutput.number() + "/logText/progressiveText", param);
            if (response.isSuccessful()){
                final Headers headers = response.headers();
                String more = response.header("X-More-Data");
                if (StringUtils.isEmpty(more)) {
                    buildOutput.output(response.body().string(),true);
                    this.cancel();
                    return;
                }
                size = Integer.valueOf(response.header("X-Text-Size"));
                buildOutput.output(response.body().string(),false);
            }
        }catch (IOException e){
            this.cancel();
        }finally {
            if (response!=null){response.close();}
        }
    }
}
