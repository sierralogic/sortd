(ns sortd.test.logic.parse
  (:require [clojure.test :refer :all]
            [sortd.logic.parse :refer :all]))

(deftest parse-utils

  (testing "delimiter classifier"
    (is (= (classify-delimiter-type nil) nil))
    (is (= (classify-delimiter-type "this | that | them") :pipe))
    (is (= (classify-delimiter-type "this, that, them") :comma))
    (is (= (classify-delimiter-type "this that them") :space)))

  (testing "normalizing headers"
    (is (= (normalize-header "this") :this))
    (is (= (normalize-header "   that   ") :that)))

  (testing "normalizing fields"
    (is (= (normalize-field "this") "this"))
    (is (= (normalize-field "   that   ") "that")))

  )

(def data-headers #{:LastName :FirstName :FavoriteColor :Gender :DateOfBirth})

(deftest parse-core

  (testing "line to record"
    (is (= (->record #"," [:a :b :c] "alpha,beta, gamma")
           {:a "alpha" :b "beta" :c "gamma"})))

  (testing "csv parsing"
    (let [data (slurp "data/records.csv")
          parsed-result (->records data)
          {:keys [headers field-count record-count delimiter-type]} parsed-result]
      (is (= field-count 5))
      (is (= record-count 4))
      (is (= delimiter-type :comma))
      (is (= (into #{} headers) data-headers))))

  (testing "pipe-delimited parsing"
    (let [data (slurp "data/records.pipe.txt")
          parsed-result (->records data)
          {:keys [headers field-count record-count delimiter-type]} parsed-result]
      (is (= field-count 5))
      (is (= record-count 4))
      (is (= delimiter-type :pipe))
      (is (= (into #{} headers) data-headers))))

  (testing "space-delimited parsing"
    (let [data (slurp "data/records.space.txt")
          parsed-result (->records data)
          {:keys [headers field-count record-count delimiter-type]} parsed-result]
      (is (= field-count 5))
      (is (= record-count 4))
      (is (= delimiter-type :space))
      (is (= (into #{} headers) data-headers))))

  )


