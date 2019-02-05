(ns lazy.core
  (:gen-class))

(def list '(1 2 3 4 5 6 7 8 9 10))
(defn slowly [x] (Thread/sleep 3600000) (+ x 1))

(defn -main
  [& args]
  (println "let's go")
  (map slowly list)
  (println "finish"))
