package com.adongs;

import com.adongs.api.*;
import com.adongs.api.impl.*;
import com.adongs.http.HttpReques;
import com.adongs.http.TokenSave;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * jenkins 客户端
 * @author yudong
 * @version 1.0
 * @date 2020/10/20 10:18 上午
 * @modified By
 */
public class JenkinsClient {

  private Server server;
  private HttpReques httpReques;
  private ViewInfo viewInfo;
  private JobAction jobAction;
  private BuildQueue buildQueue;
  private UserAction userAction;
  private HistoryInformation historyInformation;

  public JenkinsClient(TokenSave tokenSave, @NotNull String url, @NotNull String name, @NotNull char [] password) {
    this.server = new Server(url,name,password);
    this.httpReques = new HttpReques(tokenSave,this.server);
  }

  public ViewInfo getView(){
    if (viewInfo==null){
      viewInfo = new ViewInfoImpl(this.httpReques);
    }
    return viewInfo;
  }

  public JobAction getJobAction(){
    if (jobAction==null){
      jobAction = new JobActionImpl(httpReques);
    }
    return jobAction;
  }

  public BuildQueue getBuildQueue(){
    if (buildQueue==null){
      buildQueue = new BuildQueueImpl(httpReques);
    }
    return buildQueue;
  }

  public UserAction getUserAction(){
    if (userAction==null){
      userAction = new UserActionImpl(httpReques);
    }
    return userAction;
  }

  public HistoryInformation getHistoryInformation(){
    if (historyInformation==null){
      historyInformation = new HistoryInformationImpl(httpReques);
    }
    return historyInformation;
  }


  public static class Server{

    private String url;

    private String name;

    private char[] password;

    public Server() {
    }

    public Server(String url, String name, char[] password) {
      this.url = url;
      this.name = name;
      this.password = password;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public char[] getPassword() {
      return password;
    }

    public void setPassword(char[] password) {
      this.password = password;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public boolean complete(){
      return !StringUtils.isEmpty(url) && !StringUtils.isEmpty(name) && password!=null && password.length>0;
    }
  }
}
