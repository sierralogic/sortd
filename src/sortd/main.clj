(ns sortd.main
  (:require [clojure.string :as str]
            [sortd.logic.core :as sortd]
            [sortd.model.store :as store]
            [sortd.util :as util])
  (:gen-class))

(def ^:dynamic *output-f* print)

(def default-sort-field "name")
(def empty-message "No records.")

(defn output
  "Outputs arguments `xs` using dynamic `output-f` function variable. "
  [& xs]
  (apply *output-f* xs))

(def help-text
  (str "\nUsage: sortd <data-delimited-filename> [<sort-by-field: may be name, dob, or gender>]\n\n"
       "If the 'sort-by-field` is missing or invalid, name is used for sorting.\n\n"))

(defn help
  "Display help."
  []
  (output help-text)
  help-text)

(defn parse-display-file
  "Parses and displays delimited data field `filename` with optional `sort-by-field`
  string (defaults to `name`)."
  ([filename] (parse-display-file nil filename))
  ([sort-by-field filename]
   (let [result (sortd/parse-delimited-data-file filename)]
     (if-let [error (:error result)]
       (do
         (output (str "\nError loading and parsing data file '" filename "'.\n\n" error "\n"
                      help-text))
         :bad-parse)
       (let [{:keys [headers records]} result
             sorted (store/sort-by-field (keyword (or sort-by-field
                                                      default-sort-field))
                                         records)
             empty-msg (when (empty? sorted) empty-message)]
         (if empty-msg
           (do
             (output empty-msg)
             empty-msg)
           (let [header-line (str/join " | " (map util/->str headers))
                 header-border (str/join "" (repeat (count header-line) "="))
                 header-str (str "\n" header-line "\n" header-border "\n")
                 result-str (str (reduce (fn [a r]
                                           (str a (->> r
                                                       vals
                                                       (str/join " | "))
                                                "\n"))
                                         header-str
                                         sorted) "\n")]
             (output result-str)
             result-str)))))))

(defn -main
  "Main entry function for `sortd` application with variadic arguments `arg`."
  [& args]
  (if (empty? args)
    (help)
    (parse-display-file (util/safe-nth args 1) (util/safe-nth args 0))))
