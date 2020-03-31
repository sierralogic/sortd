(ns sortd.routes.home
  (:require [compojure.core :refer :all]
            [sortd.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Sorted"]))

(defroutes home-routes
  (GET "/" [] (home)))
