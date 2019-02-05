(ns parallel-web-client.core
  (:gen-class))

(def url "http://localhost:3000")

(defn set-interval [callback ms ntimes]
  (future
    (dotimes [i ntimes]
      (Thread/sleep ms)
      (callback i))))

(defn request[x] 
    (let [resp @(http/get url)]
        (println (str x " " (get resp :status)))))

(defn -main
  [& args]
  (dotimes [x 2]
    (set-interval request 250 4))
  (shutdown-agents))

(comment "
(defn -main
  [& args]
  (let [future-count (Integer/parseInt (first args))
        sleep-time   (Integer/parseInt (first (next args)))
        iterate-cnt  (Integer/parseInt (first (next (next args))))]
    (dotimes [x future-count]
      (set-interval request sleep-time iterate-cnt)))
    (shutdown-agents))
")
