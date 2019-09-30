; define variable 

(def x 12)
(def x 15)

; let local variable

(let [x 1 y 2]
  (+ x y))

(let [x 1 y 2]
  (+ x y))

(let [[x y] [1 2]]
  (+ x y))

; read document

(doc if)

; if, case, cond

(let [x ６]
  (if (> x 5)
    "big"
    "small"))

 (let [x 4]
   (if (> x 5)
     "big"
     "small"))

(let [x 2]
  (case x 
    1 "one"
    2 "two"
    3 "three"
    "otherwise"))

(let [x 10]
  (cond 
    (= x 1) "one"
    (= x 2) "two"
    (= x 3) "three"
    :else "otherwise"))

; define function

(defn sum [x y] (+ x y))

; lambda expression

(fn [x y] (+ x y))

((fn [x y] (+ x y)) 1 2)

(def f (fn [x] (+ x 1)))
(f 1)

(defn f [x] (fn [y] (+ x y)))
((f 1) 2)

(defn f [fp] (fp 1))
(f (fn [x] (+ x 1)))

; first, rest

(first '(1 2 3))
(rest '(1 2 3))

(first [1 2 3])
(rest [1 2 3])

; higher order function

(map (fn [x] (* x x)) '(1 2 3))
(reduce (fn [x y] (+ x y)) '(1 2 3))
(filter (fn [x] (> x 2)) '(1 2 3 4 5))

; dotimes

(dotimes [x 5]
  (println x))

; loop recur

(loop [x 1 ret 0]
  (if (> x 10)
    ret
    (recur (inc x) (+ ret x))))

; factorial two versions

(defn factorial [n]
  (if (= 1 n) 1
      (* n (factorial (dec n)))))

(defn factorial [x]
  (loop [n x prod 1]
    (if (= 1 n) prod
      (recur (dec n) (* prod n)))))

; reading file

(def content (slurp "/textfile"))
(type content)

(require '[clojure.string :as str])
(def line_list (str/split content #"\n"))

; map and mapcat

(require '[clojure.string :as str])

(map 
  (fn [line] (str/split line #" "))
  '("a b", "c d"))

(mapcat 
  (fn [line] (str/split line #" "))
  '("a b c", "d e f"))

; make list of word from file

(require '[clojure.string :as str])

(def content (slurp "/textfile"))

(def line_list (str/split content #"\n"))

(def word_list
  (mapcat 
    (fn [line] (str/split line #" "))
    line_list))

; how to use hashmap

(def mymap {})

(def mymap {:key1 "val1" :key2 2})

(get mymap :key1)

(assoc mymap :key1 "new_key1")

(update mymap :key2 inc)

(def mymap (assoc mymap :key "new_key1"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; word count (small file)

(require '[clojure.string :as str])

(def content (slurp "/textfile"))

(def line_list (str/split content #"\n"))

(def word_list
  (mapcat 
    (fn [line] (str/split line #" "))
    line_list))

(reduce
  (fn [acc_map elem]
    (if (nil? (get acc_map elem))
      (assoc acc_map elem 1)
      (update acc_map elem inc)))
  {}
  word_list)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; example of laziness

$ cd /workspace/clojure
$ lein new app lazy && cd lazy

[/workspace/lazy/core.clj]
(ns laziness.core
  (:gen-class))

(def list '(1 2 3 4 5 6 7 8 9 10))
(defn slowly [x] (Thread/sleep 3600000) (+ x 1))

(defn -main
  [& args]
  (println "let's go")
  (map slowly list)
  (println "finish"))

$ lein run
let's go
finish

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; infinite list

(defn infinite-num [n]
  (cons n
    (lazy-seq (infinite-num (+ n 1)))))

(take 10 (infinite-num 0))

(take 10 (infinite-num 0))

(type list)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; write big file using random infinite list

(def word-list '("Jack" "Bauer" "Tony" "Almeida" ))

(defn random-from-list [list]
  (nth list
    (rand-int (count list))))

(defn infinite-word [word-list]
  (cons
    (random-from-list word-list)
    (lazy-seq
      (infinite-word word-list))))

(with-open [w (clojure.java.io/writer "/bigfile.txt")]
  (doseq [word (take 20000000 (infinite-word word-list))]
    (.write w (str word "\n"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; doseq and for

(doseq [elem '(1 2 3)]
  (println elem))

(for [elem '(1 2 3)]
  (println elem))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; lazy and not lazy 

(defn doitslow [x]
  (Thread/sleep 1000)
  (println x)
  (+ 1 x))

(def lazylist
  (for [elem '(1 2 3)]
    (doitslow elem)))

(realized? lazylist)

;

(def notlazylist
  (doseq [elem '(1 2 3)]
    (doitslow elem)))

;

(defn slowly [x] (Thread/sleep 3000) (+ x 1))

(def foo (map slowly [1 2 3]))

(realized? foo)

;

(def foo (doall (map slowly [1 2 3])))
(realized? foo)
(type foo)

;;;;;;;

(def foo (dorun (map println [1 2 3])))
(type foo)

;;;;;;

(require '[clojure.java.io :as io])
(require '[clojure.string :as str])
(with-open [rdr (io/reader "/bigfile.txt")]
  (dorun
    (map println (line-seq rdr))))

(doc line-seq)

;;;;;;;

(require '[clojure.string :as str])
(with-open [rdr (io/reader "/bigfile.txt")]
  (reduce
    (fn [size line] (+ size (count line)))
    0
    (line-seq rdr)))

; frequencies function

(frequencies ["a" "a" "b"])

; merge-with function

(merge-with + {:a 1 :b 1} {:a 3 :c 1})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; word count big file

(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

(defn split_by_space [line] (str/split line #" "))

(defn merge_sum_map [map1 map2] (merge-with + map1 map2))

(defn split_and_merge [map line]
  (merge_sum_map
    map
    (frequencies (split_by_space line))))

(defn wordcount [filename]
  (with-open [rdr (io/reader filename)]
    (let [biglazylist (line-seq rdr)]
      (doall
        (reduce
          (fn [acc_map line]
            (split_and_merge acc_map line))
          {}
          biglazylist)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; basic web server using compojure

$ cd /workspace/clojure
$ lein new compojure basic-web && cd basic-web

$ tmux
$ lein ring server-headless

; tmux move another pane

$ curl localhost:3000

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; basic web client

$ cd /workspace/clojure
$ lein new app web-client

; modify project.clj

:dependencies [[http-kit "2.1.18"]
               [org.clojure/clojure "1.9.0"]]

;

[/workspace/clojure/src/web_client/core.clj]
(ns web-client.core
  (:require [org.httpkit.client :as http])
  (:gen-class))

(def url "http://localhost:3000")

(defn -main
  [& args]
  (let [resp @(http/get url)]
    (println (get resp :status))
    (println (get resp :body))))

$ lein run

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; future & promise

(defn iamgrut []
  (Thread/sleep 3000)
  (println "Hi")
  "I'm grut")

(iamgrut)

(future (iamgrut))

@(future (iamgrut))

; future & promise

(defn i-will-promise-you [] (promise))

(def his-promise (i-will-promise-you))

(future (Thread/sleep 20000) (deliver his-promise 1))

@his-promise

; calling function repeatedly

(defn set-interval [callback ms ntimes]
  (future
    (dotimes [i ntimes]
      (Thread/sleep ms)
      (callback))))

(set-interval
  (fn [] (println "hi"))
  1000
  5)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; parallel request web client

$ cd /workspace/clojure
$ lein new app parallel-web-client

; modify project.clj

:dependencies [[http-kit "2.1.18"]
               [org.clojure/clojure "1.9.0"]]

; 

[/workspace/clojure/parallel-web-client/src/parallel_web_client/core.clj]

(ns parallel-web-client.core
  (:require [org.httpkit.client :as http])
  (:gen-class))

(def url "http://localhost:3000")

(defn set-interval [callback ms ntimes]
  (future
    (dotimes [i ntimes]
      (Thread/sleep ms)
      (callback i))))

(defn request [x]
  (let [resp @(http/get url)]
  (println (str x " " (get resp :status)))))

(defn -main
  [& args]
  (dotimes [x 2]
  (set-interval request 250 4))
  (shutdown-agents))

$ lein run

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; parallel request web client with input argument

; modify only -main function like below

[/workspace/clojure/parallel-web-client/src/parallel_web_client/core.clj]
(defn -main
  [& args]
  (let [future-count (Integer/parseInt (first args))
        sleep-time (Integer/parseInt (first (next args)))
        iterate-cnt (Integer/parseInt (first (next (next args))))]
    (dotimes [x future-count]
      (set-interval request sleep-time iterate-cnt)))
  (shutdown-agents))


$ lein repl 3 150 3

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; web-server-shared-var 

$ cd /workspace/clojure
$ lein new compojure web-server-shared-var && cd web-server-shared-var

;

[/workspace/clojure/web-server-shared-var/src/web_server_shared_var/handler.clj]

(def shared 0)

(defroutes app-routes
  (GET "/" request
    (def shared (inc shared))
    (println shared)
    "Hello World")

  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes site-defaults))

;

$ cd /workspace/clojure/web-server-shared-var/
$ lein ring server-headless

; tmux another pane

$ cd /workspace/clojure/parallel-web-client
$ lein run 10 1 100

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; how to use atom 

(def x (atom 100))

(deref x)

@x

(+ 1 @x)

(swap! x (fn [x] (+ x 1)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; web-server-atom

$ cd /workspace/clojure
$ lein new compojure web-server-atom && cd web-server-atom

;

[/workspace/clojure/web-server-atom/src/web_server_atom/handler.clj]

(def shared (atom 0))

(defroutes app-routes
  (GET "/" request
    (swap! shared inc)
    (println @shared)
    "Hello World")
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes site-defaults))

$ lein ring server-headless

; tmux another pane 

$ cd /ref/clojure/parallel-web-client
$ lein run 10 1 100

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; how to use agent

(def x (agent 100))

(deref x)

@x

(+ 1 @x)

(send x inc)

@x

(defn take-long [x]
  (Thread/sleep 5000)
  (inc x))

(send x take-long)

@x

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; web-server for agent

$ cd /workspace/clojure
$ lein new compojure web-server-agent && cd web-server-agent

;

[/workspace/clojure/web-server-agent/src/web_server_agent/handler.clj]

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

(def app (wrap-defaults app-routes site-defaults))

$ lein ring server-headless

; tmux another pane

$ cd /ref/clojure/parallel-web-client
$ lein run 10 1 100

; after enough time elapsed

$ curl localhost:3000/shared

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; stm test - web server using only atom

;; web-server using only atom

$ mkdir stm && cd stm
$ lein new compojure web-server-atom && cd web-server-atom

;

[/workspace/clojure/stm/web-server-atom/src/web_server_atom/handler.clj]

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

(def app (wrap-defaults app-routes site-defaults))

;

$ lein ring server-headless

;; web client

$ cd /workspace/clojure/stm
$ lein new app web-client-stm && cd web-client-stm

; modify project.clj

:dependencies [[http-kit "2.1.18"]
               [org.clojure/clojure "1.9.0"]]

;

[/workspace/clojure/stm/web-client-stm/src/web-client-stm/core.clj]

(ns web-client-stm.core
  (:require [org.httpkit.client :as http])
  (:gen-class))

(def jack-url "http://localhost:3000/jack")
(def tony-url "http://localhost:3000/tony")

(defn set-interval [callback ms ntimes]
  (future
    (dotimes [i ntimes]
      (Thread/sleep ms)
      (callback i))))

(defn jack-give-money [x] 
  (let [resp @(http/get jack-url)]
    (println (str x " " (get resp :body)))))

(defn tony-give-money [x] 
  (let [resp @(http/get tony-url)]
    (println (str x " " (get resp :body)))))

(defn -main
  [& args]
  (set-interval jack-give-money 1 100)
  (set-interval tony-give-money 1 100)
  (shutdown-agents))

;

$ lein run 

;; web-server using only stm(ref)

$ cd /workspace/clojure/stm
$ lein new compojure web-server-ref && cd web-server-ref

;

[/workspace/clojure/stm/web-server-ref/src/web-server-ref/handler.clj]

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

