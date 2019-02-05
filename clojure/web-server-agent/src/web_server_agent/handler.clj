(ns web-server-agent.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def shared (agent 0))

(defn slow-inc [x] 
    (Thread/sleep 50) 
    (inc x))

(defroutes app-routes
  (GET "/" request 
       (send shared slow-inc) 
       (println @shared) 
       "Hello World")
  (GET "/shared" [] 
       (println @shared) 
       "Hello")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
