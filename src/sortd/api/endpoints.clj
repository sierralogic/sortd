(ns sortd.api.endpoints
  (:require [compojure.core :refer :all]
            [sortd.api.handlers :as handlers]))

(defroutes core-routes

  (POST "/records" request (handlers/records! request)) ;; Post a single data line in any of the 3 formats supported by your existing code
  (GET "/records/gender" request (handlers/records-by-gender request)) ;; returns records sorted by `gender`
  (GET "/records/birthdate" request (handlers/records-by-dob request)) ;; returns records sorted by `birthdate`
  (GET "/records/name" request (handlers/records-by-name request)) ;; returns records sorted by `name`

  (ANY "/echo" request (handlers/echo request))
  (ANY "/ping" request (handlers/ping request))

           )
