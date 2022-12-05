(ns day5
  (:require [clojure.string :as str]))

(def input
  (->> (slurp "day5.input")
       str/split-lines))

(def input-stacks (->> input
                       (take 8)
                       (map (fn [s] (map #(get s %) (range 1 (* 9 4) 4))))
                       (apply mapv (comp reverse vector))
                       (mapv #(remove #{\space} %))))

(def input-moves
  (->> input
       (drop 10)
       (map (partial re-seq #"(\d)+"))
       (map (fn [ms] (mapv (fn [[m]] (Integer/parseInt m)) ms)))
       (mapv (fn [[n from to]] [n (dec from) (dec to)]))))


;; Q1
(def answer1
  (->> (reduce (fn [stacks [n from to]]
                 (-> stacks
                     (update to concat (reverse (take-last n (get stacks from))))
                     (update from #(drop-last n %))))
               input-stacks input-moves)
       (map last) (apply str)))

;; Q2
(def answer2
  (->> (reduce (fn [stacks [n from to]]
                 (-> stacks
                     (update to concat (take-last n (get stacks from)))
                     (update from #(drop-last n %))))
               input-stacks input-moves)
       (map last) (apply str)))