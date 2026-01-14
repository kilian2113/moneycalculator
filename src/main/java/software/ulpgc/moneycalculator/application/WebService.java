package software.ulpgc.moneycalculator.application;

import software.ulpgc.moneycalculator.architecture.model.Currency;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WebService {
    private static final String ApiKey = "19ed106fb471122bc8494748";
    private static final String ApiUrl = "https://v6.exchangerate-api.com/v6/API-KEY/".replace("API-KEY", ApiKey);

    public static class CurrencyLoader implements software.ulpgc.moneycalculator.architecture.io.CurrencyLoader {

        @Override
        public List<Currency> loadAll() {
            try {
                return readCurrencies();
            } catch (IOException e) {
                return List.of();
            }
        }

        private List<Currency> readCurrencies() throws IOException {
            try (InputStream is = openInputStream(createConnection())) {
                return readCurrenciesWith(jsonIn(is));
            }
        }

        private List<Currency> readCurrenciesWith(String json) {
            return readCurrenciesWith(jsonObjectIn(json));
        }

        private List<Currency> readCurrenciesWith(JsonObject jsonObject) {
            return readCurrenciesWith(jsonObject.get("supported_codes").getAsJsonArray());
        }

        private List<Currency> readCurrenciesWith(JsonArray jsonArray) {
            List<Currency> list = new ArrayList<>();
            for (JsonElement item : jsonArray)
                list.add(readCurrencyWith(item.getAsJsonArray()));
            return list;
        }

        private Currency readCurrencyWith(JsonArray tuple) {
            return new Currency(
                    tuple.get(0).getAsString(),
                    tuple.get(1).getAsString()
            );
        }

        private static String jsonIn(InputStream is) throws IOException {
            return new String(is.readAllBytes());
        }

        private static JsonObject jsonObjectIn(String json) {
            return new Gson().fromJson(json, JsonObject.class);
        }

        private InputStream openInputStream(URLConnection connection) throws IOException {
            return connection.getInputStream();
        }

        private static URLConnection createConnection() throws IOException {
            URL url = new URL((ApiUrl + "codes"));
            return url.openConnection();
        }
    }
}

