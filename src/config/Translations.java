package config;

import java.util.HashMap;
import java.util.Map;

public class Translations {
    public static Map<String, String> roles = new HashMap<String, String>(){{
        put("admin", "Администратор");
        put("customer", "Клиент");
    }};
}
