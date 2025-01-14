/*
 * Copyright 2022 LambdaWorks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio.elasticsearch

import zio.elasticsearch.ElasticPrimitive.ElasticPrimitive
import zio.elasticsearch.query._
import zio.schema.Schema

object ElasticQuery {

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that contains the
   * specified value in the specified field.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   text value that will be used for the query in the pattern that represents `contains`
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def contains[S](field: Field[S, String], value: String): WildcardQuery[S] =
    Wildcard(field = field.toString, value = s"*$value*", boost = None, caseInsensitive = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that contains the
   * specified value in the specified field.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   text value that will be used for the query in the pattern that represents `contains`
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def contains(field: String, value: String): WildcardQuery[Any] =
    Wildcard(field = field, value = s"*$value*", boost = None, caseInsensitive = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.ExistsQuery]], that checks existence of the field,
   * using the specified parameters.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.ExistsQuery]] that represents the exists query to be performed.
   */
  final def exists[S](field: Field[S, _]): ExistsQuery[S] =
    Exists(field = field.toString)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.ExistsQuery]], that checks existence of the field, using the
   * specified parameters.
   *
   * @param field
   *   the field for which query is specified for
   * @return
   *   an instance of [[zio.elasticsearch.query.ExistsQuery]] that represents the exists query to be performed.
   */
  final def exists(field: String): ExistsQuery[Any] =
    Exists(field = field)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `filter` inside of the `Bool` query
   * @tparam S
   *   document for which field query is executed. An implicit `Schema` instance must be in scope
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must
   *   satisfy the criteria.
   */
  final def filter[S: Schema](queries: ElasticQuery[S]*): BoolQuery[S] =
    Bool[S](filter = queries.toList, must = Nil, mustNot = Nil, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `filter` inside of the `Bool` query
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must
   *   satisfy the criteria.
   */
  final def filter(queries: ElasticQuery[Any]*): BoolQuery[Any] =
    Bool[Any](filter = queries.toList, must = Nil, mustNot = Nil, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.HasChildQuery]] using the specified parameters.
   *
   * @param childType
   *   a name of the child relationship mapped for the join field
   * @param query
   *   the [[ElasticQuery]] object representing query you wish to run on child documents of the child `type` field
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.HasChildQuery]] that represents the `has child query` to be performed.
   */
  final def hasChild[S: Schema](childType: String, query: ElasticQuery[S]): HasChildQuery[S] =
    HasChild(childType = childType, query = query)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.HasChildQuery]] using the specified parameters.
   *
   * @param childType
   *   a name of the child relationship mapped for the join field
   * @param query
   *   the [[ElasticQuery]] object representing query you wish to run on child documents of the child `type` field
   * @return
   *   an instance of [[zio.elasticsearch.query.HasChildQuery]] that represents the `has child query` to be performed.
   */
  final def hasChild(childType: String, query: ElasticQuery[Any]): HasChildQuery[Any] =
    HasChild(childType = childType, query = query)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.HasParentQuery]] using the specified parameters.
   *
   * @param parentType
   *   a name of the parent relationship mapped for the join field
   * @param query
   *   the [[ElasticQuery]] object representing query you wish to run on parent documents of the `parent_type` field
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.HasParentQuery]] that represents the has parent query to be performed.
   */
  final def hasParent[S: Schema](parentType: String, query: ElasticQuery[S]): HasParentQuery[S] =
    HasParent(parentType = parentType, query = query)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.HasParentQuery]] using the specified parameters.
   *
   * @param parentType
   *   a name of the parent relationship mapped for the join field
   * @param query
   *   the [[ElasticQuery]] object representing query you wish to run on parent documents of the `parent_type` field
   * @return
   *   an instance of [[zio.elasticsearch.query.HasParentQuery]] that represents the has parent query to be performed.
   */
  final def hasParent(parentType: String, query: ElasticQuery[Any]): HasParentQuery[Any] =
    HasParent[Any](parentType = parentType, query = query)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.MatchAllQuery]] used for matching all documents.
   *
   * @return
   *   an instance of [[zio.elasticsearch.query.MatchAllQuery]] that represents the match all query to be performed.
   */
  final def matchAll: MatchAllQuery =
    MatchAll(boost = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.MatchQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.MatchQuery]] is used for matching a provided text, number, date or boolean value.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   the value to be matched, represented by an instance of type `A`
   * @tparam S
   *   document for which field query is executed
   * @tparam A
   *   the type of value to be matched. A JSON decoder must be in scope for this type
   * @return
   *   an instance of [[zio.elasticsearch.query.MatchQuery]] that represents the match query to be performed.
   */
  final def matches[S, A: ElasticPrimitive](field: Field[S, A], value: A): MatchQuery[S] =
    Match(field = field.toString, value = value, boost = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.MatchQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.MatchQuery]] is used for matching a provided text, number, date or boolean value.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   the value to be matched, represented by an instance of type `A`
   * @tparam A
   *   the type of value to be matched. A JSON decoder must be in scope for this type
   * @return
   *   an instance of [[zio.elasticsearch.query.MatchQuery]] that represents the match query to be performed.
   */
  final def matches[A: ElasticPrimitive](field: String, value: A): MatchQuery[Any] =
    Match(field = field, value = value, boost = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.MatchPhraseQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.MatchPhraseQuery]] analyzes the text and creates a phrase query out of the analyzed text.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   the value to be matched, represented by an instance of type `A`
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.MatchPhraseQuery]] that represents the match phrase query to be
   *   performed.
   */
  final def matchPhrase[S](field: Field[S, String], value: String): MatchPhraseQuery[S] =
    MatchPhrase(field = field.toString, value = value, boost = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.MatchPhraseQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.MatchPhraseQuery]] analyzes the text and creates a phrase query out of the analyzed text.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   the value to be matched, represented by an instance of type `A`
   * @return
   *   an instance of [[zio.elasticsearch.query.MatchPhraseQuery]] that represents the match phrase query to be
   *   performed.
   */
  final def matchPhrase(field: String, value: String): MatchPhraseQuery[Any] =
    MatchPhrase(field = field, value = value, boost = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `must` inside of the `Bool` query
   * @tparam S
   *   document for which field query is executed. An implicit `Schema` instance must be in scope
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must
   *   satisfy the criteria.
   */
  final def must[S: Schema](queries: ElasticQuery[S]*): BoolQuery[S] =
    Bool[S](filter = Nil, must = queries.toList, mustNot = Nil, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `must` inside of the `Bool` query
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must
   *   satisfy the criteria.
   */
  final def must(queries: ElasticQuery[Any]*): BoolQuery[Any] =
    Bool[Any](filter = Nil, must = queries.toList, mustNot = Nil, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must not satisfy the criteria
   * using the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `mustNot` inside of the `Bool` query
   * @tparam S
   *   document for which field query is executed. An implicit `Schema` instance must be in scope
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must not
   *   satisfy the criteria.
   */
  final def mustNot[S: Schema](queries: ElasticQuery[S]*): BoolQuery[S] =
    Bool[S](filter = Nil, must = Nil, mustNot = queries.toList, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that must not satisfy the criteria
   * using the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `mustNot` inside of the `Bool` query
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that must not
   *   satisfy the criteria.
   */
  final def mustNot(queries: ElasticQuery[Any]*): BoolQuery[Any] =
    Bool[Any](filter = Nil, must = Nil, mustNot = queries.toList, should = Nil, boost = None, minimumShouldMatch = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.NestedQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.NestedQuery]] wraps another query to search nested fields.
   *
   * @param path
   *   the type-safe path to the field for which query is specified for
   * @param query
   *   the [[ElasticQuery]] object representing the query to execute on nested objects.
   * @tparam S
   *   document for which field query is executed
   * @tparam A
   *   the type of the value that will be used for the query
   * @return
   *   an instance of [[zio.elasticsearch.query.NestedQuery]] that represents the nested query to be performed.
   */
  final def nested[S, A](path: Field[S, Seq[A]], query: ElasticQuery[A]): NestedQuery[S] =
    Nested(path = path.toString, query = query, scoreMode = None, ignoreUnmapped = None, innerHitsField = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.NestedQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.NestedQuery]] wraps another query to search nested fields.
   *
   * @param path
   *   the path to the field for which query is specified for
   * @param query
   *   the [[ElasticQuery]] object representing the query to execute on nested objects.
   * @return
   *   an instance of [[zio.elasticsearch.query.NestedQuery]] that represents the nested query to be performed.
   */
  final def nested(path: String, query: ElasticQuery[_]): NestedQuery[Any] =
    Nested(path = path, query = query, scoreMode = None, ignoreUnmapped = None, innerHitsField = None)

  /**
   * Constructs a type-safe unbounded instance of [[zio.elasticsearch.query.RangeQuery]] using the specified parameters.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @tparam S
   *   document for which field query is executed
   * @tparam A
   *   the type of the value that will be used for the query
   * @return
   *   an instance of [[zio.elasticsearch.query.RangeQuery]] that represents the range query to be performed.
   */
  final def range[S, A](field: Field[S, A]): RangeQuery[S, A, Unbounded.type, Unbounded.type] =
    Range.empty(field.toString)

  /**
   * Constructs an unbounded instance of [[zio.elasticsearch.query.RangeQuery]] using the specified parameters.
   *
   * @param field
   *   the field for which query is specified for
   * @return
   *   an instance of [[zio.elasticsearch.query.RangeQuery]] that represents the range query to be performed.
   */
  final def range(field: String): RangeQuery[Any, Any, Unbounded.type, Unbounded.type] =
    Range.empty[Any, Any](field = field)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that should satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `should` inside of the `Bool` query
   * @tparam S
   *   document for which field query is executed. An implicit `Schema` instance must be in scope
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that should
   *   satisfy the criteria.
   */
  final def should[S: Schema](queries: ElasticQuery[S]*): BoolQuery[S] =
    Bool[S](filter = Nil, must = Nil, mustNot = Nil, should = queries.toList, boost = None, minimumShouldMatch = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.BoolQuery]] with queries that should satisfy the criteria using
   * the specified parameters.
   *
   * @param queries
   *   a list of queries to add to `should` inside of the `Bool` query
   * @return
   *   an instance of [[zio.elasticsearch.query.BoolQuery]] that represents the bool query with queries that should
   *   satisfy the criteria.
   */
  final def should(queries: ElasticQuery[Any]*): BoolQuery[Any] =
    Bool[Any](filter = Nil, must = Nil, mustNot = Nil, should = queries.toList, boost = None, minimumShouldMatch = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that starts with the
   * specified value in the specified field.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   text value that will be used for the query
   * @tparam S
   *   document for which field query is executed in the pattern that represents `startsWith`
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def startsWith[S](field: Field[S, String], value: String): WildcardQuery[S] =
    Wildcard(field = field.toString, value = s"$value*", boost = None, caseInsensitive = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that starts with the
   * specified value in the specified field.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   text value that will be used for the query in the pattern that represents `startsWith`
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def startsWith(field: String, value: String): WildcardQuery[Any] =
    Wildcard(field = field, value = s"$value*", boost = None, caseInsensitive = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.TermQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.TermQuery]] is used for matching documents that contain an exact term in a provided
   * field.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   the value that will be used for the query, represented by an instance of type `A`
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.TermQuery]] that represents the term query to be performed.
   */
  final def term[S](field: Field[S, String], value: String): TermQuery[S] =
    Term(field = field.toString, value = value, boost = None, caseInsensitive = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.TermQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.TermQuery]] is used for matching documents that contain an exact term in a provided
   * field.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   the value that will be used for the query, represented by an instance of type `A`
   * @return
   *   an instance of [[zio.elasticsearch.query.TermQuery]] that represents the term query to be performed.
   */
  final def term(field: String, value: String): Term[Any] =
    Term(field = field, value = value, boost = None, caseInsensitive = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.TermsQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.TermsQuery]] is used for matching documents that contain one or more term in a provided
   * field. The terms query is the same as [[zio.elasticsearch.query.TermQuery]], except you can search for multiple
   * values.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param values
   *   a list of terms that should be find in the provided field
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.TermsQuery]] that represents the term query to be performed.
   */
  final def terms[S](field: Field[S, String], values: String*): Terms[S] =
    Terms(field = field.toString, values = values.toList, boost = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.TermsQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.TermsQuery]] is used for matching documents that contain one or more term in a provided
   * field. The terms query is the same as [[zio.elasticsearch.query.TermQuery]], except you can search for multiple
   * values.
   *
   * @param field
   *   the field for which query is specified for
   * @param values
   *   a list of terms that should be find in the provided field
   * @return
   *   an instance of [[zio.elasticsearch.query.TermsQuery]] that represents the term query to be performed.
   */
  final def terms(field: String, values: String*): Terms[Any] =
    Terms(field = field, values = values.toList, boost = None)

  /**
   * Constructs a type-safe instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that matches a provided
   * pattern value.
   *
   * @param field
   *   the type-safe field for which query is specified for
   * @param value
   *   text value that will be used for the query
   * @tparam S
   *   document for which field query is executed
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def wildcard[S](field: Field[S, _], value: String): Wildcard[S] =
    Wildcard(field = field.toString, value = value, boost = None, caseInsensitive = None)

  /**
   * Constructs an instance of [[zio.elasticsearch.query.WildcardQuery]] using the specified parameters.
   * [[zio.elasticsearch.query.WildcardQuery]] is used for matching documents containing a value that matches a provided
   * pattern value.
   *
   * @param field
   *   the field for which query is specified for
   * @param value
   *   text value that will be used for the query
   * @return
   *   an instance of [[zio.elasticsearch.query.WildcardQuery]] that represents the wildcard query to be performed.
   */
  final def wildcard(field: String, value: String): Wildcard[Any] =
    Wildcard(field = field, value = value, boost = None, caseInsensitive = None)
}
