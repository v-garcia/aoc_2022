(ns day9
  (:require [clojure.string :as str]))


(def input
  (->> (slurp "day10.input")
       (str/split-lines)
       (map (fn [l] (let [[command n] (str/split l #" ")] [(keyword command) (and n (Integer/parseInt n))])))))

;; Question 1

(def cycle->v
  (->> input
       (reduce (fn [[v :as l] [command n]]
                 (case command
                   :noop (conj l v)
                   :addx (conj l v (+ v n))))
         '(1))
       reverse
       vec))

(def answer1
  (->> [20 60 100 140 180 220]
       (mapv #(* % (nth cycle->v (dec %))))
       (apply +)))

;; Question 2

(->> cycle->v
     (map-indexed (fn [i v] (if (<= (dec v) (mod i 40) (inc v)) \# \.)))
     
     (partition 40)
     (mapv println))
