(ns sortd.test.util
  (:require [clojure.test :refer :all]
            [sortd.util :refer :all]))

(deftest textual-utils
  (testing "->str conversion"
    (is (= (->str nil) nil))
    (is (= (->str :meh) "meh"))
    (is (= (->str :ns/meh) "ns/meh"))
    (is (= (->str 42) "42"))
    (is (= (->str true) "true"))
    (is (= (->str 6.28) "6.28"))
    (is (= (->str "meh") "meh"))))
