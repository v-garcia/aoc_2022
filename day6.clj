(ns day6
  (:require [clojure.string :as str]))

(def input (slurp "day6.input"))



(def answer1 (->>
              (map vector (range) input (drop 1 input) (drop 2 input) (drop 3 input))
              (some #(when
                      (= 4 (count (set (rest %))))
                       (+ (first %) 4)))))

(def answer2 (->>
              (apply map vector (range) (map #(drop % input) (range 0 14)))
              (some #(when
                      (= 14 (count (set (rest %))))
                       (+ (first %) 14)))))

