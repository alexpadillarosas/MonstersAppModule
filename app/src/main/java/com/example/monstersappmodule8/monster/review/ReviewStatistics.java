package com.example.monstersappmodule8.monster.review;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class ReviewStatistics {
    private Integer totalVotes;
    private Float averageStars;

    @Ignore
    public ReviewStatistics() {
    }

    public ReviewStatistics(Integer totalVotes, Float averageStars) {
        this.totalVotes = totalVotes;
        this.averageStars = averageStars;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Float getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(Float averageStars) {
        this.averageStars = averageStars;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReviewAverage{" +
                "totalVotes=" + totalVotes +
                ", averageStars=" + averageStars +
                '}';
    }
}
