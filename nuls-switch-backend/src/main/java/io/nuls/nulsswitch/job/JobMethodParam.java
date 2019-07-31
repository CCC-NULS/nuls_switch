package io.nuls.nulsswitch.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class JobMethodParam implements Serializable {

    private static final long serialVersionUID = 0;

    private Map<String, String> params = new HashMap<String, String>();


    public String getValue(String key) {
        return params.get(key);
    }


    public JobMethodParam put(String key, String value) {
        params.put(key, value);
        return this;
    }


    public static JobMethodParam create() {
        return new JobMethodParam();
    }


    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public String toString() {
        return "JobMethodParam [params=" + params + "]";
    }
}
