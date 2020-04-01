(ns sortd.test.logic.core
  (:require [clojure.test :refer :all]
            [sortd.logic.core :refer :all]
            [sortd.model.store :as store]
            [sortd.test.conf :as conf]))

(deftest data-loads

  (testing "delimited data file loading"
    (store/records! nil)
    (is (nil? @store/records))
    (let [good-load (load-delimited-data-file conf/csv-filename)
          {:keys [delimiter-type field-count record-count]} @store/records]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) delimiter-type))
      (is (= (:field-count good-load) field-count))
      (is (= (:record-count good-load) record-count))))

  (testing "delimited data string loading"
    (store/records! nil)
    (is (nil? @store/records))
    (let [good-load (load-delimited-data (slurp conf/csv-filename))
          {:keys [delimiter-type field-count record-count]} @store/records]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) delimiter-type))
      (is (= (:field-count good-load) field-count))
      (is (= (:record-count good-load) record-count))))

  (testing "delimited data file parsing"
    (let [good-load (parse-delimited-data-file conf/csv-filename)]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) :comma))
      (is (= (:field-count good-load) 5))
      (is (= (:record-count good-load) 4))))

  (testing "delimited data file parsing exception"
    (let [sad-parse (parse-delimited-data-file nil)]
      (is (:error sad-parse))))

  (testing "delimited data string parsing"
    (let [good-load (parse-delimited-data (slurp conf/csv-filename))]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) :comma))
      (is (= (:field-count good-load) 5))
      (is (= (:record-count good-load) 4))))

  (testing "delimited data string parsing exception"
    (let [sad-parse (parse-delimited-data nil)]
      (is (:error sad-parse))))

  )
