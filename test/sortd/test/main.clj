(ns sortd.test.main
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [sortd.main :refer :all]
            [sortd.util :refer [ascending?]]))

(deftest main-utils

  (testing "output"
    (binding [*output-f* str]
      (is (= (output "do" "re" "mi") "doremi"))))

  (testing "help"
    (is (= (help) help-text)))
  )

(deftest parse-and-display

  (testing "parse and display, no sort field, defaults to name"
    (let [result (parse-display-file "data/records.csv")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "parse and display, sort by name"
    (let [result (parse-display-file "name" "data/records.csv")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "parse and display, sort by gender"
    (let [result (parse-display-file "gender" "data/records.csv")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [sexton-pos shamaya-pos bukowski-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "parse and display, sort by dob"
    (let [result (parse-display-file "dob" "data/records.csv")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [wright-pos bukowski-pos sexton-pos shamaya-pos]]
      (is (ascending? expected-order))))

  (testing "parse and display, sad bad parse"
    (let [result (parse-display-file "dob" "data/asdfasdfrecords.csv")]
      (is (= result :bad-parse))))

  (testing "parse and display, sad empty parse"
    (let [result (parse-display-file "dob" "data/records.empty.csv")]
      (is (= result empty-message))))

  )

(deftest main-entry

  (testing "no args, help"
    (let [result (-main)]
      (is (= result (help)))))

  (testing "delimited data filename and no sort field"
    (let [result (-main "data/records.csv")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "delim data filename and sort by name"
    (let [result (-main "data/records.csv" "name")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "delim data filename and sort by gender"
    (let [result (-main "data/records.csv" "gender")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [sexton-pos shamaya-pos bukowski-pos wright-pos]]
      (is (ascending? expected-order))))

  (testing "delim data filename and sort by dob"
    (let [result (-main "data/records.csv" "dob")
          bukowski-pos (str/index-of result "Bukowski")
          sexton-pos (str/index-of result "Sexton")
          shamaya-pos (str/index-of result "Shamaya")
          wright-pos (str/index-of result "Wright")
          expected-order [wright-pos bukowski-pos sexton-pos shamaya-pos]]
      (is (ascending? expected-order))))

  (testing "delim data filename, sad parse"
    (let [result (-main "data/recorasdfasdfds.csv")]
      (is (= result :bad-parse))))

  (testing "delim data filename, sad empty parse"
    (let [result (-main "data/records.empty.csv" "name")]
      (is (= result empty-message))))

  )
