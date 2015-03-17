package Services;

import DAO.StatisticDAO;
import Mappers.StatisticMapper;
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

    public StatisticService(final DB co2Database) {
        statisticDAO = new StatisticDAO(co2Database);
    }

    public List<StatisticModel> findByDateDescending(int page, int limit) {
          List<DBObject> data = statisticDAO.findByDateDescending(page, limit);
          return StatisticMapper.convertListOfDbObjects(data);
    }

    public List<StatisticModel> findByDevice(String deviceId, Date startDate, Date endDate, boolean sortDescending) {
        List<DBObject> data = statisticDAO.findByDevice(deviceId, startDate, endDate, sortDescending);
        return StatisticMapper.convertListOfDbObjects(data);
    }

}
