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
