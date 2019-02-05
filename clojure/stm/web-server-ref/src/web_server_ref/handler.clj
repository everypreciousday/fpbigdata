(ns web-server-ref.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def jack (ref 1000))
(def tony (ref 1000))

(defroutes app-routes
  (GET "/jack" request 
       (dosync 
           (alter jack (fn [x] (- x 500)))
           (Thread/sleep (rand-int 1000))
           (alter tony (fn [x] (+ x 500)))))

  (GET "/tony" request 
       (dosync 
           (alter jack (fn [x] (- x 500)))
           (Thread/sleep (rand-int 1000))
           (alter tony (fn [x] (+ x 500)))))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
