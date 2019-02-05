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

(wordcount "/bigfile.txt")
