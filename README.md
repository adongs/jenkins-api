## Jenkins api

> Jenkins api 提供基础的功能,后续增加其他功能

### 使用方法

> 获取一个连接对象

```java
  //获取连接对象
  JenkinsClient jenkinsClient = new JenkinsClient(new TokenSave() {
             /**
              * 获取缓存的令牌
              * @return 令牌
              */
        @Override
        public String token() {
            return null;
        }

             /**
              * 保存令牌,当访问到令牌的时候会调用保存
              * @param token  令牌
              * @param time   有效时间 
              * @param timeUnit 单位
              */
        @Override
        public void save(String token, long time, TimeUnit timeUnit) {

        }

             /**
              * 有效时间
              * @return
              */
        @Override
        public long time() {
            return 0;
        }

             /**
              * 是否过期
              * @return
              */
        @Override
        public boolean expired() {
            return false;
        }

             /**
              * 删除令牌
              */
        @Override
        public void delete() {

        }
    }, "jenkins服务器url", "name", "密码".toCharArray());
```

> 获取视图

```java
         //获取视图信息
        final ViewInfo view = jenkinsClient.getView();
        //全局视图
        final List<View> global = view.global();
        //我的视图,这个对应jenkins的我的视图
        final List<View> views = view.myView();
```

> 获取队列相关

```java
        //获取队列
        final BuildQueue buildQueue = jenkinsClient.getBuildQueue();
        //获取构建等待队列
        final List<QueueJob> queueJobs = buildQueue.buildQueue();
        //获取执行中的队列
        final List<QueueJob> executors = buildQueue.executors();
```

> Job动作

```java
       final JobAction jobAction = jenkinsClient.getJobAction();
       //构建
        final boolean build = jobAction.build("job名称");
        //取消构建,需要获取执行中的队列进行取消
        final boolean cancel = jobAction.cancel(executors.get(0));
        //获取构建输出
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
```