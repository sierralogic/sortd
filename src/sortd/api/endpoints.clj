(ns sortd.api.endpoints
  (:require [compojure.core :refer :all]
            [sortd.api.handlers :as handlers]
            [sortd.util :as util]))

(defroutes core-routes

  (POST "/records" request (handlers/records! request))                       ;; post a single data line in any of the 3 formats supported by your existing code

  (GET "/records/gender" request (handlers/records-by-field request :gender)) ;; returns records sorted by `gender`
  (GET "/records/birthdate" request (handlers/records-by-field request :dob)) ;; returns records sorted by `birthdate`
  (GET "/records/name" request (handlers/records-by-field request :name))     ;; returns records sorted by `name`

  (ANY "/echo" request (util/echo request))
  (ANY "/ping" request (util/ping request))

           )
