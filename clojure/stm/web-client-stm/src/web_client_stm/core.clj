(ns web-client-stm.core
  (:gen-class))

(def jack-url "http://localhost:3000/jack")
(def tony-url "http://localhost:3000/tony")

(defn set-interval [callback ms ntimes]
  (future
    (dotimes [i ntimes]
      (Thread/sleep ms)
      (callback i))))

(defn jack-give-money [x] (let [resp @(http/get jack-url)]
    (println (str x " " (get resp :body)))))

(defn tony-give-money [x] (let [resp @(http/get tony-url)]
    (println (str x " " (get resp :body)))))

(defn -main
  [& args]
    (set-interval jack-give-money 1 100)
    (set-interval tony-give-money 1 100)
    (shutdown-agents))
