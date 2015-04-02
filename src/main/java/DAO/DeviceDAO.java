package DAO;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amira on 03.04.15.
 */
public class DeviceDAO {
    DBCollection deviceCollection;

    public DeviceDAO(final DB co2Database) {
        deviceCollection = co2Database.getCollection("data");
    }

    public DBObject findByDeviceId(String deviceId) {
        DBObject result = deviceCollection.findOne(new BasicDBObject("device_id", deviceId));
        return  result;
    }

    public boolean ifDeviceExists(String deviceId) {
        return (deviceCollection.find(new BasicDBObject("device_id", deviceId)).count() > 0);
    }

    public boolean addDevice(String device_id, String device_name, Integer delay, double co2MinLevel) {
        if (deviceCollection.find(new BasicDBObject("device_id", device_id)).count() > 0) {
            System.out.println("Device with this device_id already exists: " + device_id);
            return false;
        }

        BasicDBObject post = new BasicDBObject("device_id", device_id)
            .append("device_name", device_name)
            .append("delay", delay)
            .append("co2MinLevel", co2MinLevel);

        try {
            deviceCollection.insert(post);
        } catch (Exception e) {
            System.out.println("Error inserting post");
            return false;
        }

        return true;
    }
}
