(ns sortd.logic.core
  (:require [sortd.logic.parse :as parse]
            [sortd.model.store :as store]))

(defn parse-delimited-data
  "Parse delimited data string `data` to storage."
  ([data] (parse-delimited-data nil data))
  ([load? data]
   (let [parse-result (parse/->records data)]
     (if (:error parse-result)
       parse-result
       (let [store-result (when load? (store/records! parse-result))]
         parse-result)))))

(defn parse-delimited-data-file
  "Parse delimited data file `filename`."
  ([filename] (parse-delimited-data-file nil filename))
  ([load? filename]
   (try
     (parse-delimited-data load? (slurp filename))
     (catch Exception ex
       {:error (str "Error processing/parsing/loading filename '" filename "': " (str ex))
        :exception ex}))))

(def load-delimited-data (partial parse-delimited-data true))
(def load-delimited-data-file (partial parse-delimited-data-file true))
