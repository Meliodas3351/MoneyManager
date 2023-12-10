package com.example.moneymanager;

public class Currency {
    private final String FROM;
    private final String TO;
    private final double VALUE;

    public Currency(String FROM, String TO, double VALUE) {
        this.FROM = FROM;
        this.TO = TO;
        this.VALUE = VALUE;
    }

    public String getFROM() {
        return FROM;
    }

    public String getTO() {
        return TO;
    }

    public double getVALUE() {
        return VALUE;
    }

    @Override
    public String toString() {
        return FROM + '-' + TO + "->" + VALUE;
    }
}
