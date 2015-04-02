package Services;

import DAO.DeviceDAO;
import DAO.UserDAO;
import Mappers.StatisticMapper;
import Model.DeviceModel;
import com.mongodb.DB;
import com.mongodb.DBObject;

import java.util.List;

/**
 * Created by amira on 03.04.15.
 */
public class DeviceServise {
    private DeviceDAO deviceDAO;

    public DeviceServise(final DB co2Database) {
        deviceDAO = new DeviceDAO(co2Database);
    }

    public DeviceModel findDevicesById(String device_id) {
        DBObject device = deviceDAO.findByDeviceId(device_id);
        return  StatisticMapper.convertDeviceDbObject(device);
    }

    public Boolean ifDeviceExists(String device_id) {
        return deviceDAO.ifDeviceExists(device_id);
    }

    public Boolean addDevice(String device_id, String device_name, Integer delay, double co2MinLevel) {
        if (ifDeviceExists(device_id)) return false;
        return deviceDAO.addDevice(device_id, device_name, delay, co2MinLevel);
    }
}
