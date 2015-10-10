package data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class GetCompanies {
    String[] companyNames;
    String[] companySymbol;

    public void fetcher(String urlAdd) throws JSONException, IOException {
        try {
            URL getQuery = new URL(urlAdd);
            InputStream input = getQuery.openStream();
            Reader reader = new InputStreamReader(input);
            JSONTokener jsTok = new JSONTokener(reader);
            JSONObject getResults = new JSONObject(jsTok);

            // JSON PARSER

            JSONObject jsonQuery = getResults.getJSONObject("query");

            // DEBUG
            // <p> Query: " + jsonQuery.toString() + "</p>\n");
            JSONObject results = jsonQuery.getJSONObject("results");

            JSONObject ind = results.getJSONObject("industry");
            // DEBUG
            // <p> Results " + results.toString() + "</p>\n");
            JSONArray list = ind.getJSONArray("company");

            companyNames = new String[list.length()];
            companySymbol = new String[list.length()];

            for (int i = 0; i < list.length(); i++) {
                JSONObject temp1 = list.getJSONObject(i);
                companyNames[i] = (String)temp1.get("name");
                companySymbol[i] = (String)temp1.get("symbol");
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] getCompNames() {
        return this.companyNames;
    }

    public String[] getCompSymb() {
        return this.companySymbol;
    }
}