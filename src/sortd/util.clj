(ns sortd.util
  (:require [cheshire.core :as json]
            [clojure.string :as str])
  (:import (java.util Date)))

(defn ->str
  "Converts `x` to string.  Passes through `x` `nil` as `nil`."
  [x]
  (when x
    (if (keyword? x)
      (-> x
          str
          (subs 1))
      (str x))))

(defn ->keyword
  "Converts `x` to keyword.  Passes through `x` for `nil` and `x` if `x` is a keyword."
  [x]
  (when x
    (if (keyword? x)
      x
      (-> x
          str
          keyword))))

(defn safe-nth
  "Safe nth for vector/list `v` and index `n`.  Returns `nil` on exception."
  [v n]
  (when v
    (try
      (nth v n)
      (catch Exception _))))

(defn ascending?
  "Determines if numeric sequence `xs` is ascending from first to last."
  [xs]
  (reduce #(if (> % %2)
             (reduced false)
             %2)
          -1
          xs))

(defn not-yet-implemented
  "Generates not yet implemented response."
  [request]
  {:status 501
   :body {:message (str "Endpoint '"
                        (str/upper-case (->str (:request-method request "unk")))
                        " " (:uri request) "' not yet implemented.")}})

(defn echo
  "Simple echo of `request` without the original payload/body."
  [request]
  {:status 200
   :body (-> request
             (dissoc :body)
             (assoc :ts (str (Date.))))})

(defn ping
  "Simple ping with string date and epoch time in response."
  [_]
  {:status 200
   :body {:date (str (Date.))
          :epoch (System/currentTimeMillis)}})

(defn json-str->edn
  "Convert JSON string `s` to EDN."
  [s]
  (json/parse-string s true))

(defn edn->json-str
  "Convert Clojure `x` to JSON string."
  [x]
  (json/generate-string x {:pretty true}))

