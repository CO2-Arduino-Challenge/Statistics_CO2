package Controllers;

import DAO.StatisticDAO;
import Model.StatisticModel;
import Services.StatisticService;
import Services.UserService;
import Utils.JsonUtil;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bson.types.ObjectId;
import spark.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;
import spark.template.freemarker.FreeMarkerEngine;


/* test commit to see what's up with repository transfer*/
/**
 * Created by denis on 12.03.15.
 */
public class StatisticController {

    public final static int PER_PAGE = 30;

    private final Configuration cfg;
    private final StatisticService statisticService;
    private final UserService userService;


    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            new StatisticController("mongodb://localhost");
        }
        else {
            new StatisticController(args[0]);
        }
    }

    public StatisticController(String mongoURIString) throws IOException {
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
        final DB co2Database = mongoClient.getDB("co2");

        cfg = new Configuration();
        cfg.setClassForTemplateLoading(StatisticController.class, "/freemarker");
        statisticService = new StatisticService(co2Database);
        userService = new UserService(co2Database);

        setPort(8180);
        initializeRoutes();

    }

    private void initializeRoutes() throws IOException {
        // this is the blog home page
        get("/", (request, response) -> {
                List<StatisticModel> data = statisticService.findByDateDescending(0, PER_PAGE);
                SimpleHash root = new SimpleHash();
                root.put("per_page", PER_PAGE);
                root.put("page", 0);
                root.put("data", data);

              return new ModelAndView(root, "index_template.ftl");

        }, new FreeMarkerEngine(cfg));

        get("/page/:page", (request, response) -> {
            int page = Integer.parseInt(request.params(":page"));
            List<StatisticModel> data = statisticService.findByDateDescending(page, PER_PAGE);
            SimpleHash root = new SimpleHash();
            root.put("per_page", PER_PAGE);
            root.put("page", page);
            root.put("data", data);
            return  new ModelAndView(root, "index_template.ftl");
        }, new FreeMarkerEngine(cfg));

        get("/api/statistic", (request, response) -> {
            String deviceId = request.queryParams("deviceid");
            long st_time = Long.parseLong(request.queryParams("st_date"));
            long end_time = Long.parseLong(request.queryParams("end_date"));
            return statisticService.findByDevice(deviceId, new Date(st_time), new Date(end_time), true);
        }, JsonUtil.json());

        get("/api/devices", (request, response) -> {
            return userService.findDevicesByUser(request.queryParams("username"));
        }, JsonUtil.json());

        get("/json_statistic", (req, res) -> statisticService.findByDateDescending(0, 10), JsonUtil.json());

        get("/addData", (request, response) -> {
            SimpleHash root = new SimpleHash();
            return new ModelAndView(root, "newdata_template.ftl");
        }, new FreeMarkerEngine(cfg));

        post("/addData", (request, response) -> {
                Map<String, String> params = request.params();
                if (params.containsKey("device_id") &&
                        params.containsKey("temperature") && params.containsKey("co2")) { // && params.containsKey("humidity"))
                    String device_id = StringEscapeUtils.escapeHtml4(request.queryParams("device_id"));
                    String temperature = StringEscapeUtils.escapeHtml4(request.queryParams("temperature"));
                    String co2 = StringEscapeUtils.escapeHtml4(request.queryParams("co2"));

                    double co2_d = Double.parseDouble(co2);
                    double temperature_d = Double.parseDouble(temperature);

                    statisticService.addEntity(device_id, temperature_d, co2_d);

                    response.redirect("/", 302);
                }
                else {
                    //TODO: redirect do error
                }

                return "";

        });
    }
}
