(ns sortd.api.handlers
  (:require [sortd.logic.core :as sortd]
            [sortd.model.store :as store]
            [sortd.util :as util]))

(defn records!
  "Store records from `request` payload/body."
  [request]
  (let [body (:body request)
        nil-body? (nil? body)
        data (when-not nil-body? (if (string? body) body (slurp body)))
        empty-body? (or nil-body? (empty? data))
        result (sortd/load-delimited-data data)]
    (if empty-body?
      {:status 400 :body {:error "The payload of the request is empty."}}
      {:status 201 :body (merge {:message "Data parsed/loaded successfully."}
                                result)})))

(defn records-by-field
  "Return sorted records by `field`."
  [_ field]
  (let [result (store/sort-by-field field)]
    {:status 200
     :body {:records result}}))
