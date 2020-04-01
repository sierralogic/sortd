(ns sortd.test.util
  (:require [clojure.test :refer :all]
            [sortd.util :refer :all]
            [clojure.string :as str]))

(deftest textual-utils

  (testing "->str conversion"
    (is (= (->str nil) nil))
    (is (= (->str :meh) "meh"))
    (is (= (->str :ns/meh) "ns/meh"))
    (is (= (->str 42) "42"))
    (is (= (->str true) "true"))
    (is (= (->str 6.28) "6.28"))
    (is (= (->str "meh") "meh")))

  (testing "->keyword conversion"
    (is (= (->keyword :foo) :foo))
    (is (= (->keyword "meh") :meh))
    (is (= (->keyword 42) :42)))

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

(deftest http-utils

  (testing "ping"
    (is (= (:status (ping nil)) 200)))

  (testing "echo"
    (let [{:keys [status body]} (echo {:foo :bar})]
      (is (= status 200))
      (is (= (:foo body) :bar))))

  (testing "not yet implemented"
    (let [{:keys [status body]} (not-yet-implemented nil)]
      (is (= status 501))
      (is (str/index-of body "mplement"))))

  )

(deftest conversion-utils

  (testing "json str to edn"
    (is (= (json-str->edn "{\"foo\":\"bar\", \"ans\" : 42 }") {:foo "bar" :ans 42})))

  (testing "edn to json str"
    (is (= (edn->json-str {:foo 42}) "{\n  \"foo\" : 42\n}")))

  )
