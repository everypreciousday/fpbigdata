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
  (doseq 
    [word (take 20000000 (infinite-word word-list))] 
    (.write w (str word "\n"))))
