package com.adongs.model;

import java.util.List;

/**
 * @author yudong
 * @version 1.0
 * @date 2020/11/4 5:23 下午
 * @modified By
 */
public class BuildHistory {
    private String name;
    private Boolean buildable;
    private List<History> builds;
    private List<HealthReport> healthReport;
    private Boolean inQueue;
    private History lastBuild;
    private History lastCompletedBuild;
    private History lastFailedBuild;
    private History lastStableBuild;
    private History lastSuccessfulBuild;
    private History lastUnstableBuild;
    private History lastUnsuccessfulBuild;
    private Integer nextBuildNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBuildable() {
        return buildable;
    }

    public void setBuildable(Boolean buildable) {
        this.buildable = buildable;
    }

    public List<History> getBuilds() {
        return builds;
    }

    public void setBuilds(List<History> builds) {
        this.builds = builds;
    }

    public List<HealthReport> getHealthReport() {
        return healthReport;
    }

    public void setHealthReport(List<HealthReport> healthReport) {
        this.healthReport = healthReport;
    }

    public Boolean getInQueue() {
        return inQueue;
    }

    public void setInQueue(Boolean inQueue) {
        this.inQueue = inQueue;
    }

    public History getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(History lastBuild) {
        this.lastBuild = lastBuild;
    }

    public History getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public void setLastCompletedBuild(History lastCompletedBuild) {
        this.lastCompletedBuild = lastCompletedBuild;
    }

    public History getLastFailedBuild() {
        return lastFailedBuild;
    }

    public void setLastFailedBuild(History lastFailedBuild) {
        this.lastFailedBuild = lastFailedBuild;
    }

    public History getLastStableBuild() {
        return lastStableBuild;
    }

    public void setLastStableBuild(History lastStableBuild) {
        this.lastStableBuild = lastStableBuild;
    }

    public History getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public void setLastSuccessfulBuild(History lastSuccessfulBuild) {
        this.lastSuccessfulBuild = lastSuccessfulBuild;
    }

    public History getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    public void setLastUnstableBuild(History lastUnstableBuild) {
        this.lastUnstableBuild = lastUnstableBuild;
    }

    public History getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    public void setLastUnsuccessfulBuild(History lastUnsuccessfulBuild) {
        this.lastUnsuccessfulBuild = lastUnsuccessfulBuild;
    }

    public Integer getNextBuildNumber() {
        return nextBuildNumber;
    }

    public void setNextBuildNumber(Integer nextBuildNumber) {
        this.nextBuildNumber = nextBuildNumber;
    }

    public static class HealthReport{

        private String description;
        private String iconUrl;
        private String score;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    public static class History{
        private String number;
        private String url;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
