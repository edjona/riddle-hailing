package model;

import static Constant.Constants.DRIVER_RATING_MINIMUM;
import static Constant.Constants.DRIVER_TRAVEL_LIMIT_DISTANCE;

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

    public boolean isHaveGoodRating() {
        return RD >= DRIVER_RATING_MINIMUM;
    }

    public boolean isAvailablePickUpRider(Rider rider) {
        return JD >= getPickUpDistance(rider);
    }

    public boolean isAvailableDropOffRider(Rider rider) {
        return DRIVER_TRAVEL_LIMIT_DISTANCE >= (getPickUpDistance(rider) + rider.getDropOffDistance() + KM);
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

    private int getPickUpDistance(Rider rider) {
        return Math.abs(X0D - rider.getX0R()) + Math.abs(Y0D - rider.getY0R());
    }
}
