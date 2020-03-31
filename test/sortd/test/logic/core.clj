(ns sortd.test.logic.core
  (:require [clojure.test :refer :all]
            [sortd.logic.core :refer :all]
            [sortd.model.store :as store]))

(deftest data-loads

  (testing "delimited data file loading"
    (store/records! nil)
    (is (nil? @store/records))
    (let [good-load (load-delimited-data-file "data/records.csv")
          {:keys [delimiter-type field-count record-count]} @store/records]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) delimiter-type))
      (is (= (:field-count good-load) field-count))
      (is (= (:record-count good-load) record-count))))

  (testing "delimited data string loading"
    (store/records! nil)
    (is (nil? @store/records))
    (let [good-load (load-delimited-data (slurp "data/records.csv"))
          {:keys [delimiter-type field-count record-count]} @store/records]
      (is (nil? (:error good-load)))
      (is (= (:delimiter-type good-load) delimiter-type))
      (is (= (:field-count good-load) field-count))
      (is (= (:record-count good-load) record-count))))

  )
