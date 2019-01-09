import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.Body;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.mbtest.javabank.Client;
import org.mbtest.javabank.ImposterParser;
import org.mbtest.javabank.fluent.ImposterBuilder;
import org.mbtest.javabank.fluent.StubBuilder;
import org.mbtest.javabank.http.core.Stub;
import org.mbtest.javabank.http.imposters.Imposter;
import org.mbtest.javabank.http.responses.Response;


public class MountebankTest {

    @Test
    public void checkJSONforMounteBank() throws UnirestException {

        String DEFAULT_BASE_URL = "http://localhost:2525";

        Client client = new Client();

        client.deleteAllImposters();


        Imposter imposter = ImposterBuilder.anImposter().onPort(3002)
                .stub()
                    .response()
                        .is()
                            .body("{" +
                                    "\"glossary\": {" +
                                    "\"title\": \"example glossary\"," +
                                    "\"GlossDiv\": {" +
                                    "\"title\": \"S\"," +
                                    "\"GlossList\": {" +
                                    "\"GlossEntry\": {" +
                                    "\"ID\": \"SGML\"," +
                                    "\"SortAs\": \"SGML\"," +
                                    "\"GlossTerm\": \"Standard Generalized Markup Language\"," +
                                    "\"Acronym\": \"SGML\"," +
                                    "\"Abbrev\": \"ISO 8879:1986\"," +
                                    "\"GlossDef\": {" +
                                    "\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\"," +
                                    "\"GlossSeeAlso\": [\"GML\", \"XML\"]" +
                                    "}," +
                                    "\"GlossSee\": \"markup\"" +
                                    "}" +
                                    "}" +
                                    "}" +
                                    "}" +
                                    "}")
                            .statusCode(200)
                            .header(com.google.common.net.HttpHeaders.CONTENT_TYPE, "application/json")
                        .end()
                    .end()
                    .predicate()
                        .contains().path("testing").end()
                    .end()
                .end().build();

        int statusCode = client.createImposter(imposter);
        System.out.println(statusCode);


        JSONObject object = Unirest.get("http://localhost:3002/testing").asJson().getBody().getObject();

        String string = object.getJSONObject("glossary").getJSONObject("GlossDiv").getJSONObject("GlossList").getJSONObject("GlossEntry").getString("ID");

        Assert.assertEquals("SGML", string);


    }
}
