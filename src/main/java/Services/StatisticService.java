package Services;

import DAO.StatisticDAO;
import Mappers.StatisticMapper;
import Model.StatisticModel;
import com.mongodb.DB;
import com.mongodb.DBObject;

import java.util.ArrayList;
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
        List<StatisticModel> result = new ArrayList<StatisticModel>();
        List<DBObject> data = statisticDAO.findByDateDescending(page, limit);
        for(DBObject row : data) {
            result.add(StatisticMapper.convertDbObject(row));
            //TODO think about returning null in case of exception
        }
        return  result;
    }

}