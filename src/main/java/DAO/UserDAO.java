package DAO;

import Model.DeviceModel;
import Utils.EmailValidator;
import com.mongodb.*;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by amira on 02.04.15.
 */
public class UserDAO {
    DBCollection usersCollection;

    private Random random = new SecureRandom();
    private EmailValidator emailValidator = new EmailValidator();

    public UserDAO(final DB co2Database) {
        usersCollection = co2Database.getCollection("users");
    }

    public List<DBObject> findDevicesByUser(String username) {
        List<DBObject> result = new ArrayList<>();
        DBObject user = usersCollection.findOne(new BasicDBObject("username", username));
        BasicDBList devices = (BasicDBList) user.get("devices");
        for (Object device : devices ) {

            result.add((DBObject) device);
        }
        return  result;
    }

    // validates that username is unique and insert into db
    public boolean addUser(String username, String email, String password, String name, String surname,
            String country, String city, List<DeviceModel> devices) {
        if (usersCollection.find(new BasicDBObject("username", username)).count() > 0) {
            System.out.println("User with this username already exists: " + username);
            return false;
        }
        if (usersCollection.find(new BasicDBObject("email", email)).count() > 0) {
            System.out.println("User with this email already exists: " + email);
            return false;
        }

        String passwordHash = makePasswordHash(password, Integer.toString(random.nextInt()));

        BasicDBObject user = new BasicDBObject();

        user.append("username", username)
            .append("password", passwordHash)
            .append("name", name)
            .append("surname", surname)
            .append("country", country)
            .append("city", city);

        if (email != null && !email.equals("") && emailValidator.validate(email)) {
            user.append("email", email);
        }

        ArrayList<String> devicesArray = new ArrayList<String>();
        for(DeviceModel device: devices) {
            devicesArray.add(device.getDevice_id());
        }
        user.append("devices", devicesArray);

        try {
            usersCollection.insert(user);
            return true;
        } catch (MongoException.DuplicateKey e) {
            System.out.println("Username already in use: " + username);
            return false;
        }
    }

    public DBObject validateLogin(String username, String password) {
        DBObject user;

        user = usersCollection.findOne(new BasicDBObject("_id", username));

        if (user == null) {
            System.out.println("User not in database");
            return null;
        }

        String hashedAndSalted = user.get("password").toString();

        String salt = hashedAndSalted.split(",")[1];

        if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
            System.out.println("Submitted password is not a match");
            return null;
        }

        return user;
    }

    private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }
}
