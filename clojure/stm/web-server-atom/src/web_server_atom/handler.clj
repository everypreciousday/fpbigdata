(ns web-server-atom.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def jack (atom 1000))
(def tony (atom 1000))

(defroutes app-routes
  (GET "/jack" request 
       (swap! jack (fn [x] (- x 500))) 
       (Thread/sleep (rand-int 1000)) 
       (swap! tony (fn [x] (+ x 500))) 
       (str "jack:" @jack " tony:" @tony))    
  (GET "/tony" request 
       (swap! tony (fn [x] (- x 500))) 
       (Thread/sleep (rand-int 1000)) 
       (swap! jack (fn [x] (+ x 500))) 
       (str "jack:" @jack " tony:" @tony))    
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
