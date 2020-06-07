package model;

public class Rider {
    private Integer riderId;
    private Integer X0R;
    private Integer Y0R;
    private Integer XDR;
    private Integer YDR;
    private Integer JR;

    public Rider() {
    }

    public Rider(Integer riderId, Integer x0R, Integer y0R, Integer XDR, Integer YDR, Integer JR) {
        this.riderId = riderId;
        this.X0R = x0R;
        this.Y0R = y0R;
        this.XDR = XDR;
        this.YDR = YDR;
        this.JR = JR;
    }

    public Integer getRiderId() {
        return riderId;
    }

    public void setRiderId(Integer riderId) {
        this.riderId = riderId;
    }

    public Integer getX0R() {
        return X0R;
    }

    public void setX0R(Integer x0R) {
        X0R = x0R;
    }

    public Integer getY0R() {
        return Y0R;
    }

    public void setY0R(Integer y0R) {
        Y0R = y0R;
    }

    public Integer getXDR() {
        return XDR;
    }

    public void setXDR(Integer XDR) {
        this.XDR = XDR;
    }

    public Integer getYDR() {
        return YDR;
    }

    public void setYDR(Integer YDR) {
        this.YDR = YDR;
    }

    public Integer getJR() {
        return JR;
    }

    public void setJR(Integer JR) {
        this.JR = JR;
    }
}
