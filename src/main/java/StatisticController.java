import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringEscapeUtils;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;

/**
 * Created by denis on 12.03.15.
 */
public class StatisticController {


    private final Configuration cfg;
    private final StatsDAO statsDAO;
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


        statsDAO = new StatsDAO(co2Database);
        cfg = createFreemarkerConfiguration();
        statisticService = new StatisticService(co2Database);

        setPort(8080);

        initializeRoutes();
    }


    abstract class FreemarkerBasedRoute implements Route {
        final Template template;

        /**
         * Constructor
         *
         * @param path The route path which is used for matching. (e.g. /hello, users/:name)
         */
        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
            //super(path);
            template = cfg.getTemplate(templateName);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();
            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/internal_error");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final Writer writer)
                throws IOException, TemplateException;

    }

    private void initializeRoutes() throws IOException {
        // this is the blog home page
        get("/", new FreemarkerBasedRoute("/", "index_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                List<DBObject> data = statsDAO.findByDateDescending(0, 10);
                SimpleHash root = new SimpleHash();
                root.put("page", 0);
                root.put("data", data);

                template.process(root, writer);
            }
        });

        get("/json_statistic", (req, res) -> statisticService.findByDateDescending(0, 10), JsonUtil.json());

        get("/page/:page", new FreemarkerBasedRoute("/page/:page", "index_template.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                int page = Integer.parseInt(request.params(":page"));
                List<DBObject> data = statsDAO.findByDateDescending(page, 10);
                SimpleHash root = new SimpleHash();
                root.put("page", 0);
                root.put("data", data);

                template.process(root, writer);

            }
        });

        get("/addData", new FreemarkerBasedRoute("/addData", "newdata_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                    SimpleHash root = new SimpleHash();
                    template.process(root, writer);

            }
        });

        post("/addData", new FreemarkerBasedRoute("/addData", "newdata_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer)
                    throws IOException, TemplateException {

                String device_name = StringEscapeUtils.escapeHtml4(request.queryParams("device_name"));
                String temperature = StringEscapeUtils.escapeHtml4(request.queryParams("temperature"));
                String co2 = StringEscapeUtils.escapeHtml4(request.queryParams("co2"));

                double co2_d = Double.parseDouble(co2);
                double temperature_d = Double.parseDouble(temperature);

                statsDAO.addEntity(device_name, temperature_d, co2_d);

                response.redirect("/");

            }
        });
    }


    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(StatisticController.class, "/freemarker");
        return retVal;
    }

}
