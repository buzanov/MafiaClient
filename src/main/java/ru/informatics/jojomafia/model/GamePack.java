package ru.informatics.jojomafia.model;

public class GamePack {

    String name;
    String backgroundImage;
    String backCard;
    String mafiaCard;
    String doctorCard;
    String seekerCard;
    String citizenCards;

    @Override
    public String toString() {
        return name + " bg-image : " + backgroundImage;
    }

    public String getCard(String name) {
        switch (name) {
            case "MAFIA" : return mafiaCard;
            case "DOCTOR" : return doctorCard;
            case "COMMISSAR" : return seekerCard;
            case "CITIZEN" : return citizenCards;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackCard() {
        return backCard;
    }

    public void setBackCard(String backCard) {
        this.backCard = backCard;
    }

    public String getMafiaCard() {
        return mafiaCard;
    }

    public void setMafiaCard(String mafiaCard) {
        this.mafiaCard = mafiaCard;
    }

    public String getDoctorCard() {
        return doctorCard;
    }

    public void setDoctorCard(String doctorCard) {
        this.doctorCard = doctorCard;
    }

    public String getSeekerCard() {
        return seekerCard;
    }

    public void setSeekerCard(String seekerCard) {
        this.seekerCard = seekerCard;
    }

    public String getCitizenCards() {
        return citizenCards;
    }

    public void setCitizenCards(String citizenCards) {
        this.citizenCards = citizenCards;
    }
}
