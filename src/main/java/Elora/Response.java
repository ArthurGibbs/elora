package Elora;

import java.util.List;

public class Response {
    float weight;
    List<String> remarks;

    public Response(float weight, List<String> remarks) {
        this.weight = weight;
        this.remarks = remarks;
    }

    public float getWeight() {
        return weight;
    }

    public List<String> getRemarks() {
        return remarks;
    }

}
