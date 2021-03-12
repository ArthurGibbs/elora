package Elora;

import java.util.Comparator;

public class WeightComparitor implements Comparator<Response> {
    @Override
    public int compare(Response o1, Response o2) {
        if (o1.getWeight() > o2.getWeight()) {
            return -1;
        } else if (o1.getWeight() < o2.getWeight()) {
            return 1;
        } else
            return 0;
    }
}
