package fr.atypikhouse.api.Repositories.Utils;

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
