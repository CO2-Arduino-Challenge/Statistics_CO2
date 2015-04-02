package Model;

/**
 * Created by denis on 19.03.15.
 */
public class DeviceModel {
    private String device_id;

    private String device_name;

    private Integer delay;

    private double co2MinLevel;

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public double getCo2MinLevel() {
        return co2MinLevel;
    }

    public void setCo2MinLevel(double co2MinLevel) {
        this.co2MinLevel = co2MinLevel;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_id() {

        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
