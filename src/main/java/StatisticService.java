import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 14.03.15.
 */
public class StatisticService {
    private StatsDAO statsDAO;

    public StatisticService(final DB co2Database) {
        statsDAO = new StatsDAO(co2Database);
    }

    public List<StatisticModel> findByDateDescending(int page, int limit) {
        List<StatisticModel> result = new ArrayList<StatisticModel>();
        List<DBObject> data = statsDAO.findByDateDescending(page, limit);
        for(DBObject row : data) {
            result.add(StatisticMapper.convertDbObject(row));
            //TODO think about returning null in case of exception
        }
        return  result;
    }

}
