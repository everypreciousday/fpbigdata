(ns web-client.core
  (:require [org.httpkit.client :as http])
  (:gen-class))

(def url "http://localhost:3000")
(defn -main
  [& args]
  (println (type (http/get url))))
