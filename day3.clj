(ns day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def input
  (->> (slurp "day3.input")
       str/split-lines))

(def char->priority #(let [% (int %)] (if (<= % 90) (- % 38) (- % 96))))

;; Q1

(def answer1
  (->> input
       (map #(split-at (/ (count %) 2) %))
       (map #(apply set/intersection (map set %)))
       (map (comp char->priority first))
       (apply +)))

;; Q2

(def answer2
  (->> input
       (partition 3)
       (map #(apply set/intersection (map set %)))
       (map (comp char->priority first))
       (apply +)))