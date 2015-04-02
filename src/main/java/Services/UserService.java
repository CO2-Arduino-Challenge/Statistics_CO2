package Services;

import DAO.UserDAO;
import Mappers.StatisticMapper;
import Model.DeviceModel;
import com.mongodb.DB;
import com.mongodb.DBObject;

import java.util.List;

/**
 * Created by amira on 02.04.15.
 */
public class UserService {
    private UserDAO userDAO;

    public UserService(final DB co2Database) {
        userDAO = new UserDAO(co2Database);
    }

    public List<DeviceModel> findDevicesByUser(String username) {
        List<DBObject> devices = userDAO.findDevicesByUser(username);
        return  StatisticMapper.convertDevicesList(devices);
    }

    public Boolean addEntity(String username, String email, String password, String name, String surname,
                             String country, String city, List<DeviceModel> devices) {
        return userDAO.addUser(username, email, password, name, surname,
                country, city, devices);
    }
}
