(ns sortd.model.store
  (:require [clojure.string :as str]))

(def records
  "Core records atom as map with records vector in the `:records` field."
  (atom nil))

(defn records!
  "Supplant records atom content with `rs`."
  [rs]
  (reset! records rs))

(defn format-dob
  "Formats `dob` field value."
  [dob]
  (if (string? dob)
    (try
      (let [splt (str/split dob #"\/")]
        (str (second splt) "/" (nth splt 2) "/" (first splt)))
      (catch Exception _
        dob))
    dob))

(defn decorate-record
  "Decorates the result record `r`."
  [r]
  (-> r
      (update :DateOfBirth format-dob)))

(defn sorted-records
  "Returns sorted records using sort-by function `sort-by-f` and optional
  records vector `rs`.  If `rs` is `nil` or missing, the atom `records`
  map field `:records` is used as source."
  ([sort-by-f] (sorted-records sort-by-f nil))
  ([sort-by-f rs]
   (mapv decorate-record (sort-by sort-by-f (or rs (get @records :records []))))))

(def gender-sort-f "Gender sort-by function." #(str (:Gender %) (:LastName %)))
(def dob-sort-f "DOB sort-by function." :DateOfBirth)
(def name-sort-f "Name sort-by function." #(str (:LastName %) "///" (:FirstName %)))

(def sort-by-gender
  "Sort records by gender."
  (partial sorted-records gender-sort-f))

(def sort-by-dob
  "Sort records by dob, descending."
  (partial sorted-records dob-sort-f))

(def sort-by-name
  "Sort records by last name, then first name."
  (partial sorted-records name-sort-f))
