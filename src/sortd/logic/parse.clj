(ns sortd.logic.parse
  (:require [clojure.string :as str]))

(def ^:private pipe-delimiter "|")
(def ^:private comma-delimiter ",")
(def ^:private space-delimiter " ")

(def default-delimiter comma-delimiter)

(defn classify-delimiter-type
  "Classifies delimiter type from `line` text.
  Returns `nil` if line is empty/nil, and either `:pipe`, `:comma`, or `:space`."
  [line]
  (when-not (empty? line)
    (cond
      (-> line str (str/index-of pipe-delimiter) nil? not) :pipe
      (-> line str (str/index-of comma-delimiter) nil? not) :comma
      :else :space)))

(def ^:private delimiter-regex {:pipe #"\|"
                                :comma #","
                                :space #"\s"})

(defn normalize-header
  "Normalize header value `v`."
  [v]
  (-> v
      str
      str/trim
      keyword))

(defn normalize-field
  "Normalize field value `v`."
  [v]
  (-> v
      str/trim))

(defn ->record
  "Convert a delimited record `line` via delimiter regex `delim-regex` and `headers`
  vector to record map."
  [delim-regex headers line]
  (let [fields (map normalize-field (str/split line delim-regex))]
    (zipmap headers fields)))

(defn ->records
  "Parse the `delimited-data` string to a vector of record maps."
  [delimited-data]
  (try
    (let [lines (str/split-lines delimited-data)
          header (first lines)
          data (rest lines)
          delim-type (classify-delimiter-type header)
          delim-regex (get delimiter-regex delim-type default-delimiter)
          headers (mapv normalize-header (str/split header delim-regex))
          ->rec (partial ->record delim-regex headers)
          records (reduce #(conj % (->rec %2))
                          []
                          data)]
      {:records records
       :delimiter-type delim-type
       :headers headers
       :field-count (count headers)
       :record-count (count records)})
    (catch Exception ex
      {:error "Exception occurred during tranform to records."
       :exception (str ex)})))
