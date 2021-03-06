(ns web-server-shared-var.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def shared 0)

(defroutes app-routes
  (GET "/" request 
       (def shared (inc shared)) 
       (println shared) 
       "Hello World")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
