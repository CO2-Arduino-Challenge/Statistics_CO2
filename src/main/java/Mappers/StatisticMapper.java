package Mappers;

import Model.DeviceModel;
import Model.StatisticModel;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 14.03.15.
 */
public class StatisticMapper {
    static public StatisticModel convertStatisticDbObject(DBObject statisticDbObject) {
        try {
            StatisticModel stat = new StatisticModel();
            stat.setCo2((Double) statisticDbObject.get("co2"));
            stat.setDevice_id((String) statisticDbObject.get("device_id"));
            stat.setDate((Date) statisticDbObject.get("date"));
            stat.setTemperature((Double) statisticDbObject.get("temperature"));
            return  stat;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    static public List<StatisticModel> convertStatisticList(List<DBObject> statisticList) {
        List<StatisticModel> result = new ArrayList<StatisticModel>();
        if (!statisticList.isEmpty()) {
           for (DBObject row : statisticList) {
               result.add(StatisticMapper.convertStatisticDbObject(row));
           }
        }
        return result;
    }

    static public DeviceModel convertDeviceDbObject(DBObject deviceDbObject) {
        try {
            DeviceModel device = new DeviceModel();
            device.setDevice_id((String)deviceDbObject.get("device_id"));
            device.setDevice_name((String)deviceDbObject.get("device_name"));
            return  device;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    static public List<DeviceModel> convertDevicesList(List<DBObject> devicesList) {
        List<DeviceModel> result = new ArrayList<>();
        if (!devicesList.isEmpty()) {
            for (DBObject device : devicesList) {
                result.add(StatisticMapper.convertDeviceDbObject(device));
            }
        }
        return result;
    }
}
