package DAO;

import com.mongodb.*;

import java.util.Date;
import java.util.List;

/**
 * Created by denis on 12.03.15.
 */
public class StatisticDAO {
    DBCollection dataCollection;

    public StatisticDAO(final DB co2Database) {
        dataCollection = co2Database.getCollection("data");
    }


    public List<DBObject> findByDateDescending(int page, int limit) {
        List<DBObject> rows;
        DBCursor cursor = dataCollection.find().sort(new BasicDBObject().append("date", -1)).skip(limit*page).limit(limit);
        try {
            rows = cursor.toArray();
        } finally {
            cursor.close();
        }
        return rows;
    }

    public boolean addEntity(String name, double temperature, double co2) {
        BasicDBObject post = new BasicDBObject("device_name", name);
        post.append("temperature", temperature);
        post.append("co2", co2);
        post.append("date", new Date());

        try {
            dataCollection.insert(post);
        } catch (Exception e) {
            System.out.println("Error inserting post");
            return false;
        }

        return true;
    }


}
