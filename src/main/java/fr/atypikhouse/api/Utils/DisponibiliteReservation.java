package fr.atypikhouse.api.Utils;

public enum DisponibiliteReservation {
    DISPONIBLE("DISPONIBLE"),
    RESERVE("RESERVE");

    private String status;

    DisponibiliteReservation(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
