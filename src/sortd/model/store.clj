(ns sortd.model.store)

(def records (atom nil))

(defn records!
  "Supplant records atom content with `rs`."
  [rs]
  (reset! records rs))
