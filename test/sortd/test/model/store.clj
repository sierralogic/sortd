(ns sortd.test.model.store
  (:require [clojure.test :refer :all]
            [sortd.logic.core :as sortd]
            [sortd.model.store :refer :all]))

(def expected-sorted-by-gender-last-names ["Sexton" "Shamaya" "Bukowski" "Wright"])
(def expected-sorted-by-dob-last-names ["Wright" "Bukowski" "Sexton" "Shamaya"])
(def expected-sorted-by-name-last-names ["Bukowski" "Sexton" "Shamaya" "Wright"])

(deftest sorting-records

  (testing "sorting by gender, name"
    (records! nil)
    (let [load-result (sortd/load-delimited-data-file "data/records.csv")]
      (if-let [error (:error load-result)]
        (is (= nil error))
        (let [sorted-by-gender (sort-by-gender)]
          (is (= (mapv :LastName sorted-by-gender)
                 expected-sorted-by-gender-last-names))))))

  (testing "sorting by dob"
    (records! nil)
    (let [load-result (sortd/load-delimited-data-file "data/records.csv")]
      (if-let [error (:error load-result)]
        (is (= nil error))
        (let [sorted-by-dob (sort-by-dob)]
          (is (= (mapv :LastName sorted-by-dob)
                 expected-sorted-by-dob-last-names))))))

  (testing "sorting by name"
    (records! nil)
    (let [load-result (sortd/load-delimited-data-file "data/records.csv")]
      (if-let [error (:error load-result)]
        (is (= nil error))
        (let [sorted-by-name (sort-by-name)]
          (is (= (mapv :LastName sorted-by-name)
                 expected-sorted-by-name-last-names))))))

  )

(deftest storage

  (testing "storage"
    (records! nil)
    (is (nil? @records))
    (records! {:records [{:id :r1} {:id :r2} {:id :r3}] :record-count 3 :field-count 1})
    (is (= (count @records) 3)))

  )

(deftest storage-utils

  (testing "dob format"
    (is (= (format-dob "1999/01/02") "01/02/1999")))

  (testing "decorate record"
    (is (= (decorate-record {:DateOfBirth "1920/08/16"}) {:DateOfBirth "08/16/1920"})))

  )
