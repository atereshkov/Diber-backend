package com.github.handioq.diber.repository.specification;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public OrderSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public OrderSpecificationsBuilder with(String key, String subKey,
                                           String operation, Object value) {
        params.add(new SearchCriteria(key, subKey, operation, value));
        return this;
    }

    public OrderSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Order> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Order>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            Specification spec = new OrderSpecification(param);
            specs.add(spec);
        }

        Specification<Order> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}