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
    (is (= (->str "meh") "meh")))

  )

(deftest collection-utils

  (testing "safe nths"
    (is (= (safe-nth nil 34) nil))
    (is (= (safe-nth [1 2 3] 0) 1))
    (is (= (safe-nth "meh" 33) nil))
    (is (= (safe-nth [] 1) nil))
    (is (= (safe-nth [1 2 3] 3) nil)))

  (testing "ascending?"
    (is (ascending? (range 99)))
    (is (not (ascending? (cons 42 (range 99))))))

  )
