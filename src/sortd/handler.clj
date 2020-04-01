(ns sortd.handler
  (:require [compojure.core :refer [defroutes routes]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.middleware :refer [wrap-base-url]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.json :as json]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [sortd.api.endpoints :as api]
            [sortd.routes.home :refer [home-routes]]))

(defn init []
  (println "sortd service is starting"))

(defn destroy []
  (println "sortd service is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes api/core-routes app-routes)
      (json/wrap-json-body {:keywords? true})
      (wrap-defaults api-defaults)
      (wrap-keyword-params)
      (wrap-params)
      (json/wrap-json-response)
      (handler/site)
      (wrap-base-url)))
