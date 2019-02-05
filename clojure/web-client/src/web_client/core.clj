(ns web-client.core
  (:require [org.httpkit.client :as http])
  (:gen-class))


(def url "http://localhost:3000")
(defn -main
  [& args]
  (let [resp @(http/get url)]
     (println (get resp :status))
     (println (get resp :body))))
