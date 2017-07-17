package cn.gson.crm.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.common</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月17日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class MySpecification<T> implements Specification<T> {

    List<Condition> andConditions = new ArrayList<>();
    List<Condition> orConditions = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate restrictions = cb.and(getAndPredicates(root, cb));
        restrictions = cb.and(restrictions, getOrPredicates(root, cb));
        //query.orderBy(toOrders(root,cb));
        return restrictions;
    }

    private Predicate getAndPredicates(Root<T> root, CriteriaBuilder cb) {
        Predicate restrictions = cb.conjunction();
        for (Condition condition : andConditions) {
            if (condition == null) {
                continue;
            }
            Path<?> path = root.get(condition.property);
            if (path == null) {
                continue;
            }

            switch (condition.operator) {
                case eq:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String) {
                            if (!((String) condition.value).isEmpty()) {
                                restrictions = cb.and(restrictions, cb.equal(path, condition.value));
                            }
                        } else {
                            restrictions = cb.and(restrictions, cb.equal(path, condition.value));
                        }
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.ge((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.gt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case in:
                    restrictions = cb.and(restrictions, path.in(condition.value));
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.le((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.lt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case ne:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.notEqual(path, condition.value));
                        } else {
                            restrictions = cb.and(restrictions, cb.notEqual(path, condition.value));
                        }
                    }
                    break;
                case like:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, "%" + condition.value + "%"));
                        }
                    }
                    break;
                case ilike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, (String) condition.value));
                        }
                    }
                    break;
                case llike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, "%" + condition.value));
                        }
                    }
                    break;
                case rlike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, condition.value + "%"));
                        }
                    }
                    break;
                case notIn:
                    restrictions = cb.and(restrictions, path.in(condition.value).not());
                    break;
                case isNull:
                    restrictions = cb.and(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = cb.and(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

    private Predicate getOrPredicates(Root<T> root, CriteriaBuilder cb) {
        Predicate restrictions = cb.conjunction();
        for (Condition condition : orConditions) {
            if (condition == null) {
                continue;
            }
            Path<?> path = root.get(condition.property);
            if (path == null) {
                continue;
            }

            switch (condition.operator) {
                case eq:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String) {
                            if (!((String) condition.value).isEmpty()) {
                                restrictions = cb.or(restrictions, cb.equal(path, condition.value));
                            }
                        }
                    } else {
                        restrictions = cb.or(restrictions, path.isNull());
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.ge((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.gt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case in:
                    restrictions = cb.or(restrictions, path.in(condition.value));
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.le((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.lt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case ne:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.notEqual(path, condition.value));
                        } else {
                            restrictions = cb.or(restrictions, cb.notEqual(path, condition.value));
                        }
                    }
                    break;
                case like:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, "%" + condition.value + "%"));
                        }
                    }
                    break;
                case ilike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, (String) condition.value));
                        }
                    }
                    break;
                case llike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, "%" + condition.value));
                        }
                    }
                    break;
                case rlike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, condition.value + "%"));
                        }
                    }
                    break;
                case notIn:
                    restrictions = cb.or(restrictions, path.in(condition.value).not());
                    break;
                case isNull:
                    restrictions = cb.or(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = cb.or(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

    public MySpecification and(Condition... conditions) {
        for (Condition condition : conditions) {
            andConditions.add(condition);
        }
        return this;
    }

    public MySpecification or(Condition... conditions) {
        for (Condition condition : conditions) {
            orConditions.add(condition);
        }
        return this;
    }

    public static class Condition {

        Operator operator;
        String property;
        Object value;

        public Condition(String property, Operator operator, Object value) {
            this.operator = operator;
            this.property = property;
            this.value = value;
        }

        public Condition(String property, Operator operator) {
            this.operator = operator;
            this.property = property;
        }

        /**
         * 相等
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition eq(String property, Object value) {
            return new Condition(property, Operator.eq, value);
        }

        /**
         * 不相等
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition ne(String property, Object value) {
            return new Condition(property, Operator.ne, value);
        }

        /**
         * 大于
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition gt(String property, Object value) {
            return new Condition(property, Operator.gt, value);
        }

        /**
         * 小于
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition lt(String property, Object value) {
            return new Condition(property, Operator.lt, value);
        }

        /**
         * 大于等于
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition ge(String property, Object value) {
            return new Condition(property, Operator.ge, value);
        }

        /**
         * 小于等于
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition le(String property, Object value) {
            return new Condition(property, Operator.le, value);
        }

        /**
         * 模糊like %%
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition like(String property, Object value) {
            return new Condition(property, Operator.like, value);
        }

        /**
         * 模块右like xxx%
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition rlike(String property, Object value) {
            return new Condition(property, Operator.rlike, value);
        }

        /**
         * 模糊左like %xxx
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition llike(String property, Object value) {
            return new Condition(property, Operator.llike, value);
        }

        /**
         * 自定义模糊
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition ilike(String property, Object value) {
            return new Condition(property, Operator.ilike, value);
        }

        /**
         * 集合中
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition in(String property, Object value) {
            return new Condition(property, Operator.in, value);
        }

        /**
         * 不在集合中
         *
         * @param property
         * @param value
         * @return
         */
        public static Condition notIn(String property, Object value) {
            return new Condition(property, Operator.notIn, value);
        }

        /**
         * 是空
         *
         * @param property
         * @return
         */
        public static Condition isNull(String property) {
            return new Condition(property, Operator.isNull);
        }

        /**
         * 不是空
         *
         * @param property
         * @return
         */
        public static Condition isNotNull(String property) {
            return new Condition(property, Operator.isNotNull);
        }

    }

    /**
     * 运算符
     */
    public enum Operator {

        /**
         * 等于
         */
        eq(" = "),

        /**
         * 不等于
         */
        ne(" != "),

        /**
         * 大于
         */
        gt(" > "),

        /**
         * 小于
         */
        lt(" < "),

        /**
         * 大于等于
         */
        ge(" >= "),

        /**
         * 小于等于
         */
        le(" <= "),

        /**
         * 类似
         */
        like(" like "),

        /**
         * 类似
         */
        rlike(" like "),

        /**
         * 类似
         */
        llike(" like "),

        /**
         * 类似
         */
        ilike(" like "),

        /**
         * 包含
         */
        in(" in "),

        /**
         * 包含
         */
        notIn(" not in "),

        /**
         * 包含
         */
        not(" not "),

        /**
         * 为Null
         */
        isNull(" is NULL "),

        /**
         * 不为Null
         */
        isNotNull(" is not NULL ");

        Operator(String operator) {
            this.operator = operator;
        }

        private String operator;

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }
    }
}
