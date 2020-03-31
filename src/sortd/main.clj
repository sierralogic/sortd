(ns sortd.main
  (:require [sortd.model.store :as store])
  (:gen-class))

(defn -main
  [& args]
  (println :main :cli :args args))
