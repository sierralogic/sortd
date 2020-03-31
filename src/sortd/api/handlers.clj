(ns sortd.api.handlers
  (:require [clojure.string :as str]
            [sortd.util :refer [->str]])
  (:import (java.util Date)))

(defn not-yet-implemented
  [request]
  {:status 501
   :body {:message (str "Endpoint '"
                        (str/upper-case (->str (:request-method request "unk")))
                        " " (:uri request) "' not yet implemented.")}})

(defn records!
  "Store records from `request` payload/body."
  [request]
  (not-yet-implemented request))

(defn records-by-gender
  "Return sorted records by gender."
  [request]
  (not-yet-implemented request))

(defn records-by-dob
  "Return sorted records by dob."
  [request]
  (not-yet-implemented request))

(defn records-by-name
  "Return sorted records by name."
  [request]
  (not-yet-implemented request))

(defn echo
  "Simple echo of `request` without the original payload/body."
  [request]
  (println :echo :request request)
  {:status 200 :body (-> request
                         (dissoc :body)
                         (assoc :ts (str (Date.))))})

(defn ping
  "Simple ping with string date and epoch time in response."
  [_]
  {:status 200
   :body {:date (str (Date.))
          :epoch (System/currentTimeMillis)}})
