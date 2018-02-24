package com.github.handioq.diber.repository.specification;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrderSpecification implements Specification<Order> {

    private SearchCriteria criteria;

    public OrderSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // TODO 24.02.2018: Please refactor me!

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (criteria.getSubKey() == null) {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()).get(criteria.getSubKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (criteria.getSubKey() == null) {
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else {
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()).get(criteria.getSubKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("!")) {
            if (criteria.getSubKey() == null) {
                return builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            } else {
                return builder.notEqual(
                        root.get(criteria.getKey()).get(criteria.getSubKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                if (criteria.getSubKey() == null) {
                    return builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.like(
                            root.get(criteria.getKey()).get(criteria.getSubKey()), "%" + criteria.getValue() + "%");
                }
            } else {
                if (criteria.getSubKey() == null) {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                } else {
                    return builder.equal(root.get(criteria.getKey()).get(criteria.getSubKey()), criteria.getValue());
                }
            }
        }
        return null;
    }
}