(ns day7
  (:require [clojure.string :as str]))


(def input
  (->> (slurp "day7.input")
       str/split-lines
       (map #(let [[a & r] (str/split % #" ")]
               (if (#{"$" "dir"} a) (apply vector (keyword a) r) [:file (first r) (Integer/parseInt a)])))))

(defn accumulate
  [{:keys [:sizes :path], :as acc} [a b c :as command]]
  (cond (= [:$ "cd" ".."] command) (update acc :path (comp vec butlast))
        (= [:$ "cd"] [a b]) (update acc :path conj c)
        (= :file a)  (assoc acc :sizes (reduce #(update %1 %2 (fnil (partial + c) 0)) 
                                               sizes (map #(take % path) (range 1 (count path)))))
        :else acc))

(def question1
  (->> input
       (reduce accumulate {:path [], :sizes {}})
       :sizes
       (mapv last)
       (filter (partial >= 100000))
       (apply +)))

;; (def question2
;;   (->> input
;;        (reduce accumulate {:path [], :sizes {}})
;;        :sizes
;;        (mapv last)
;;        (sort)
;;        (take-while #())))