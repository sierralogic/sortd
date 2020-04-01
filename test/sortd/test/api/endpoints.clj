(ns sortd.test.api.endpoints
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [sortd.api.endpoints :refer :all]
            [sortd.handler :refer [app]]
            [sortd.model.store :as store]
            [sortd.util :as util]
            [sortd.test.conf :as conf]))

(deftest test-core-api

  (testing "load data, nil body"
    (let [response (app (request :post "/records" nil))
          {:keys [status body]} response]
      (is (= status 400))
      (is (str/index-of body "empty"))))

  (testing "load data, empty body"
    (let [response (app (request :post "/records" ""))
          {:keys [status body]} response]
      (is (= status 400))
      (is (str/index-of body "empty"))))

  (testing "load data, csv success"
    (let [response (app (request :post "/records" (slurp conf/csv-filename)))
          {:keys [status body]} response
          {:keys [record-count field-count]} @store/records]
      (is (= status 201))
      (is (str/index-of body "uccess"))
      (is (= field-count 5))
      (is (= record-count 4))))

  (testing "load data, pipe-delimited success"
    (let [response (app (request :post "/records" (slurp conf/pipe-filename)))
          {:keys [status body]} response
          {:keys [record-count field-count]} @store/records]
      (is (= status 201))
      (is (str/index-of body "uccess"))
      (is (= field-count 5))
      (is (= record-count 4))))

  (testing "load data, space-delimited success"
    (let [response (app (request :post "/records" (slurp conf/space-filename)))
          {:keys [status body]} response
          {:keys [record-count field-count]} @store/records]
      (is (= status 201))
      (is (str/index-of body "uccess"))
      (is (= field-count 5))
      (is (= record-count 4))))

  (testing "sort records, name"
    (let [_ (app (request :post "/records" (slurp conf/csv-filename)))
          response (app (request :get "/records/name"))
          {:keys [body status]} response
          result (util/json-str->edn body)
          {:keys [records]} result
          result-names (mapv :LastName records)]
      (is (= status 200))
      (is (= (count records) 4))
      (is (= result-names conf/expected-sorted-by-name-last-names))))

  (testing "sort records, gender"
    (let [_ (app (request :post "/records" (slurp conf/csv-filename)))
          response (app (request :get "/records/gender"))
          {:keys [body status]} response
          result (util/json-str->edn body)
          {:keys [records]} result
          result-names (mapv :LastName records)]
      (is (= status 200))
      (is (= (count records) 4))
      (is (= result-names conf/expected-sorted-by-gender-last-names))))

  (testing "sort records, dob"
    (let [_ (app (request :post "/records" (slurp conf/csv-filename)))
          response (app (request :get "/records/birthdate"))
          {:keys [body status]} response
          result (util/json-str->edn body)
          {:keys [records]} result
          result-names (mapv :LastName records)]
      (is (= status 200))
      (is (= (count records) 4))
      (is (= result-names conf/expected-sorted-by-dob-last-names))))

  )

(deftest test-util-api

  (testing "ping"
    (let [response (app (request :get "/ping"))]
      (is (= (:status response) 200))))

  (testing "echo"
    (let [response (app (request :post "/echo"))
          {:keys [status body]} response]
      (is (= status 200))))

  )

