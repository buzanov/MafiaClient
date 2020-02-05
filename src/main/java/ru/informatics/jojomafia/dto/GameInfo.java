package ru.informatics.jojomafia.dto;

public enum GameInfo {;

    public enum State {
        DAY,
        NIGHT
    }

    public enum Activity {
        AFTER_VOTING,
        VOTING,
        ANNOUNCEMENT,
        MAFIA_TURN,
        COMMISSAR_TURN,
        DOCTOR_TURN,
        MANIAC_TURN
    }

    public enum Roles {
        MAFIA,
        CITIZEN,
        COMMISSAR,
        DOCTOR
    }
}
