(ns sortd.logic.core
  (:require [sortd.logic.parse :as parse]
            [sortd.model.store :as store]))

(defn load-delimited-data
  "Load delimited data string `data` to storage."
  [data]
  (let [parse-result (parse/->records data)]
    (if (:error parse-result)
      parse-result
      (let [store-result (store/records! parse-result)]
        (dissoc parse-result :records)))))

(defn load-delimited-data-file
  "Load delimited data file `filename`."
  [filename]
  (load-delimited-data (slurp filename)))
