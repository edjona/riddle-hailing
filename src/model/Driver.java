package model;

public class Driver {
    private Integer driverId;
    private Integer X0D;
    private Integer Y0D;
    private Integer JD;
    private Integer KM;
    private Integer RD;

    public Driver() {
    }

    public Driver(Integer driverId, Integer x0D, Integer y0D, Integer JD, Integer KM, Integer RD) {
        this.driverId = driverId;
        this.X0D = x0D;
        this.Y0D = y0D;
        this.JD = JD;
        this.KM = KM;
        this.RD = RD;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getX0D() {
        return X0D;
    }

    public void setX0D(Integer x0D) {
        X0D = x0D;
    }

    public Integer getY0D() {
        return Y0D;
    }

    public void setY0D(Integer y0D) {
        Y0D = y0D;
    }

    public Integer getJD() {
        return JD;
    }

    public void setJD(Integer JD) {
        this.JD = JD;
    }

    public Integer getKM() {
        return KM;
    }

    public void setKM(Integer KM) {
        this.KM = KM;
    }

    public Integer getRD() {
        return RD;
    }

    public void setRD(Integer RD) {
        this.RD = RD;
    }
}
