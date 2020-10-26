import com.adongs.JenkinsClient;
import com.adongs.api.BuildOutput;
import com.adongs.api.BuildQueue;
import com.adongs.api.JobAction;
import com.adongs.api.ViewInfo;
import com.adongs.api.impl.ViewInfoImpl;
import com.adongs.http.TokenSave;
import com.adongs.model.QueueJob;
import com.adongs.model.View;
import org.jsoup.Jsoup;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.core.node.AllText;
import org.seimicrawler.xpath.util.Scanner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 11:24 上午
 * @modified By
 */
public class FeignTest {


//测试样例
    public static void main(String[] args) throws Exception{
         JenkinsClient jenkinsClient = new JenkinsClient(new TokenSave() {
        @Override
        public String token() {
            return null;
        }

        @Override
        public void save(String token, long time, TimeUnit timeUnit) {

        }

        @Override
        public long time() {
            return 0;
        }

        @Override
        public boolean expired() {
            return false;
        }

        @Override
        public void delete() {

        }
    }, "jenkins服务器url", "name", "密码".toCharArray());
       //获取视图
        final ViewInfo view = jenkinsClient.getView();
        //全局视图
        final List<View> global = view.global();
        //我的视图
        final List<View> views = view.myView();
        //获取队列
        final BuildQueue buildQueue = jenkinsClient.getBuildQueue();
        //获取构建等待队列
        final List<QueueJob> queueJobs = buildQueue.buildQueue();
        //获取执行中的队列
        final List<QueueJob> executors = buildQueue.executors();
        final JobAction jobAction = jenkinsClient.getJobAction();
        //构建
        final boolean build = jobAction.build("job名称");
        //取消构建,需要获取执行中的队列进行取消
        final boolean cancel = jobAction.cancel(executors.get(0));
        final BuildOutput buildOutput = new BuildOutput() {
            //任务名称
            @Override
            public String jobName() {
                return null;
            }

            //任务编号
            @Override
            public String number() {
                return null;
            }

            //构建输出
            @Override
            public void output(String content, boolean isFinish) {
                System.out.println(isFinish);
                System.out.println(content);
            }

            //判断中断
            @Override
            public boolean discontinue() {
                return false;
            }

            //设置中断
            @Override
            public void setDiscontinue() {

            }
        };
        jobAction.buildOutput(buildOutput);
        //取消输出构建信息
        jobAction.cancelBuildOutput(buildOutput);
    }
}