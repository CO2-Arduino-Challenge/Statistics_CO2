package Mappers;

import Model.StatisticModel;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 14.03.15.
 */
public class StatisticMapper {
    static public StatisticModel convertDbObject(DBObject mongoStat) {
        try {
            StatisticModel stat = new StatisticModel();
            stat.setCo2((Double)mongoStat.get("co2"));
            stat.setDevice_name((String)mongoStat.get("device_name"));
            stat.setDevice_id((String)mongoStat.get("device_id"));
            stat.setDate((Date)mongoStat.get("date"));
            stat.setTemperature((Double)mongoStat.get("temperature"));
            return  stat;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    static public List<StatisticModel> convertListOfDbObjects(List<DBObject> mongoStat) {
        List<StatisticModel> result = new ArrayList<StatisticModel>() { };
        if (!mongoStat.isEmpty()) {
           for (DBObject row : mongoStat) {
               result.add(StatisticMapper.convertDbObject(row));
           }
        }
        return result;
    }
}
