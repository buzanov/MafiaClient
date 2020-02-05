package ru.informatics.jojomafia.dto;

import ru.informatics.jojomafia.utils.ModelBuilder;

public class UserStats {

    private Long id;
    private Long winCount;
    private Long lostCount;
    private Long userId;

    public UserStats(Long id, Long winCount, Long lostCount, Long userId) {
        this.id = id;
        this.winCount = winCount;
        this.lostCount = lostCount;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWinCount() {
        return winCount;
    }

    public void setWinCount(Long winCount) {
        this.winCount = winCount;
    }

    public Long getLostCount() {
        return lostCount;
    }

    public void setLostCount(Long lostCount) {
        this.lostCount = lostCount;
    }


}
