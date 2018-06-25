package yuan.com.androidarchitecture.data.remote.model;


import java.util.List;

import yuan.com.androidarchitecture.data.local.entity.JokeEntity;

public class JokeResponse {
    private List<JokeEntity> data;

    public List<JokeEntity> getData() {
        return data;
    }

    public void setData(List<JokeEntity> data) {
        this.data = data;
    }
}
