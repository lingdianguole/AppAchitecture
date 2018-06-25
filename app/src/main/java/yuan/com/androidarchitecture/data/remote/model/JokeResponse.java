package yuan.com.androidarchitecture.data.remote.model;


import java.util.List;

import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

public class JokeResponse {
    private List<JokeEntity> results;

    public List<JokeEntity> getResults() {
        return results;
    }

    public void setResults(List<JokeEntity> results) {
        this.results = results;
    }
}
