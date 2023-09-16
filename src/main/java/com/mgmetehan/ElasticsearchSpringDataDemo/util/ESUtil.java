package com.mgmetehan.ElasticsearchSpringDataDemo.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@UtilityClass
public class ESUtil {
    public static Supplier<Query> supplier() {
        Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery() {
        val matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithNameField(String fieldValue) {
        Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithNameField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithNameField(String fieldValue) {
        val matchQuery = new MatchQuery.Builder();
        return matchQuery.field("name").query(fieldValue).build();
    }

    public static Supplier<Query> supplierQueryForBoolQuery(String name, String brand) {
        Supplier<Query> supplier = () -> Query.of(q -> q.bool(boolQuery(name, brand)));
        return supplier;
    }

    public static BoolQuery boolQuery(String name, String brand) {

        val boolQuery = new BoolQuery.Builder();
        return boolQuery.filter(termQuery("name",name)).must(termQuery("brand",brand)).build();
    }
    //termQuery kesin eslesme icin kullanilir
    public static List<Query> termQuery(String field, String value) {
        final List<Query> terms = new ArrayList<>();
        val termQuery = new TermQuery.Builder();
        terms.add(Query.of(q -> q.term(termQuery.field(field).value(value).build())));
        return terms;
    }

    //matchQuery daha esnek bir arama secenegi sunar ve belge iceriginde benzer terimleri bulabilir
    public static List<Query> matchQuery(String field, String value) {
        final List<Query> matches = new ArrayList<>();
        val matchQuery = new MatchQuery.Builder();
        matches.add(Query.of(q -> q.match(matchQuery.field(field).query(value).build())));
        return matches;
    }
}
