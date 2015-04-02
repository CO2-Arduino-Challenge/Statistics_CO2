package Services;

import DAO.DeviceDAO;
import DAO.StatisticDAO;
import Mappers.StatisticMapper;
import Model.DeviceModel;
import Model.StatisticModel;
import com.mongodb.DB;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 14.03.15.
 */
public class StatisticService {
    private StatisticDAO statisticDAO;
    private DeviceDAO deviceDAO;

    public StatisticService(final DB co2Database) {
        statisticDAO = new StatisticDAO(co2Database);
        deviceDAO = new DeviceDAO(co2Database);
    }

    public List<StatisticModel> findByDateDescending(int page, int limit) {
          List<DBObject> data = statisticDAO.findByDateDescending(page, limit);
          return StatisticMapper.convertStatisticList(data);
    }

    public List<StatisticModel> findByDevice(String deviceId, Date startDate, Date endDate, boolean sortDescending) {
        List<DBObject> data = statisticDAO.findByDevice(deviceId, startDate, endDate, sortDescending);
        return StatisticMapper.convertStatisticList(data);
    }

    public Boolean addEntity(String device_id, double temperature, double co2) {
        if (!deviceDAO.ifDeviceExists(device_id)) return false;
        return statisticDAO.addEntity(device_id, temperature, co2);
    }

}
