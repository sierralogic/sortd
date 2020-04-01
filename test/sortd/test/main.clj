(ns sortd.test.main
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [sortd.main :refer :all]
            [sortd.test.conf :as conf]
            [sortd.util :refer [ascending?]]))

(def test-output-f str)

(deftest main-utils

  (testing "output"
    (binding [*output-f* test-output-f]
      (is (= (output "do" "re" "mi") "doremi"))))

  (testing "help"
    (binding [*output-f* test-output-f]
      (is (= (help) help-text))))

  )

(deftest parse-and-display

  (testing "parse and display, no sort field, defaults to name"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file conf/csv-filename)
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "parse and display, sort by name"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file "name" conf/csv-filename)
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "parse and display, sort by gender"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file "gender" conf/csv-filename)
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [sexton-pos shamaya-pos bukowski-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "parse and display, sort by dob"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file "dob" conf/csv-filename)
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [wright-pos bukowski-pos sexton-pos shamaya-pos]]
        (is (ascending? expected-order)))))

  (testing "parse and display, sad bad parse"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file "dob" "file-does-not-exist")]
        (is (= result :bad-parse)))))

  (testing "parse and display, sad empty parse"
    (binding [*output-f* test-output-f]
      (let [result (parse-display-file "dob" conf/empty-filename)]
        (is (= result empty-message)))))

  )

(deftest main-entry

  (testing "no args, help"
    (binding [*output-f* test-output-f]
      (let [result (-main)]
        (is (= result (help))))))

  (testing "delimited data filename and no sort field"
    (binding [*output-f* test-output-f]
      (let [result (-main conf/csv-filename)
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "delim data filename and sort by name"
    (binding [*output-f* test-output-f]
      (let [result (-main conf/csv-filename "name")
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [bukowski-pos sexton-pos shamaya-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "delim data filename and sort by gender"
    (binding [*output-f* test-output-f]
      (let [result (-main conf/csv-filename "gender")
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [sexton-pos shamaya-pos bukowski-pos wright-pos]]
        (is (ascending? expected-order)))))

  (testing "delim data filename and sort by dob"
    (binding [*output-f* test-output-f]
      (let [result (-main conf/csv-filename "dob")
            bukowski-pos (str/index-of result "Bukowski")
            sexton-pos (str/index-of result "Sexton")
            shamaya-pos (str/index-of result "Shamaya")
            wright-pos (str/index-of result "Wright")
            expected-order [wright-pos bukowski-pos sexton-pos shamaya-pos]]
        (is (ascending? expected-order)))))

  (testing "delim data filename, sad parse"
    (binding [*output-f* test-output-f]
      (let [result (-main "file-does-not-exist")]
        (is (= result :bad-parse)))))

  (testing "delim data filename, sad empty parse"
    (binding [*output-f* test-output-f]
      (let [result (-main conf/empty-filename "name")]
        (is (= result empty-message)))))

  )
