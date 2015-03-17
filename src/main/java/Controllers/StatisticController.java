package Controllers;

import DAO.StatisticDAO;
import Services.StatisticService;
import Utils.JsonUtil;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringEscapeUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
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


    private final Configuration cfg;
    private final StatisticDAO statisticDAO;
    private final StatisticService statisticService;


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


        statisticDAO = new StatisticDAO(co2Database);
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(StatisticController.class, "/freemarker");
        statisticService = new StatisticService(co2Database);

        setPort(8080);
        initializeRoutes();

    }

    private void initializeRoutes() throws IOException {
        // this is the blog home page
        get("/", (request, response) -> {
                List<DBObject> data = statisticDAO.findByDateDescending(0, 10);
                SimpleHash root = new SimpleHash();
                root.put("page", 0);
                root.put("data", data);

              return new ModelAndView(root, "index_template.ftl");

        }, new FreeMarkerEngine(cfg));

        get("/statistic", (req, res) -> statisticService.findByDevice(req.queryParams("deviceid"), new Date( Long.parseLong(req.queryParams("st_date"))), new Date(Long.parseLong(req.queryParams("end_date"))), true), JsonUtil.json());
     //   String deviceId = request.queryParams("deviceid");
//                    long st_time = Long.parseLong(request.queryParams("st_date"));
//                    long end_time = Long.parseLong(request.queryParams("end_date"));
//                    return statisticService.findByDevice(deviceId, new Date(st_time), new Date(end_time), true);
        get("/json_statistic", (req, res) -> statisticService.findByDateDescending(0, 10), JsonUtil.json());

        get("/page/:page", (request, response) -> {
                int page = Integer.parseInt(request.params(":page"));
                List<DBObject> data = statisticDAO.findByDateDescending(page, 10);
                SimpleHash root = new SimpleHash();
                root.put("page", page);
                root.put("data", data);
                return  new ModelAndView(root, "index_template.ftl");
        }, new FreeMarkerEngine(cfg));

        get("/addData", (request, response) -> {
                    SimpleHash root = new SimpleHash();
                    return  new ModelAndView(root, "newdata_template.ftl");
        }, new FreeMarkerEngine(cfg));

        post("/addData", (request, response) -> {
                String device_name = StringEscapeUtils.escapeHtml4(request.queryParams("device_name"));
                String temperature = StringEscapeUtils.escapeHtml4(request.queryParams("temperature"));
                String co2 = StringEscapeUtils.escapeHtml4(request.queryParams("co2"));

                double co2_d = Double.parseDouble(co2);
                double temperature_d = Double.parseDouble(temperature);
                statisticDAO.addEntity(device_name, temperature_d, co2_d);

                response.redirect("/", 302);
                return "";

        });
    }
}
