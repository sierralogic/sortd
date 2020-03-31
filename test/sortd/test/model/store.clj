(ns sortd.test.model.store
  (:require [clojure.test :refer :all]
            [sortd.model.store :refer :all]))

(deftest storage
  (testing "storage"
    (is (nil? @records))
    (records! [:r1 :r2 :r3])
    (is (= (count @records) 3))))
