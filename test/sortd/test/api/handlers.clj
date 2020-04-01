(ns sortd.test.api.handlers
  (:require [clojure.test :refer :all]
            [sortd.api.handlers :refer :all]
            [clojure.string :as str]
            [sortd.util :as util]))

(deftest load-failures

  (testing "load fail, empty body"
    (let [response (records! {:body ""})
          {:keys [status body]} response]
      (is (= status 400))
      (is (str/index-of body "empty"))))

  )
