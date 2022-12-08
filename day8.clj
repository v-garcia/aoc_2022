(ns day8
  (:require [clojure.string :as str]))


(def input
  (->> (slurp "day8.input")
       str/split-lines
       (map (fn [l] (map int l)))))


;; question 1
(defn browse1 [map i current-line]
  (let
   [top-l    (take i map)
    bottom-l (drop (inc i) map)]
    [top-l bottom-l]
    (map-indexed (fn [y tree]
                   (let [left    (take y current-line)
                         right   (drop (inc y) current-line)
                         top     (mapv #(nth % y) top-l)
                         bottom  (mapv #(nth % y) bottom-l)
                         taller? (partial some (partial <= tree))]
                     (some nil? (mapv taller? [left right top bottom])))) current-line)))

(def answer1
  (->>
   input
   (map-indexed (partial browse1 (map vec input)))
   (apply concat)
   (filter true?)
   count))

;; question 2

(defn browse2 [map i current-line]
  (let
   [top-l    (take i map)
    bottom-l (drop (inc i) map)]
    [top-l bottom-l]
    (map-indexed (fn [y tree]
                   (let [left           (reverse (take y current-line))
                         right          (drop (inc y) current-line)
                         top            (reverse (mapv #(nth % y) top-l))
                         bottom         (mapv #(nth % y) bottom-l)
                         partial-score  #(let  [[s1 s2]  (split-with (partial > tree) %)] (+ (count s1) (count (take 1 s2))))]
                     (apply * (mapv partial-score [top right bottom   left]))))
                 current-line)))

(def answer2
  (->>
   input
   (map-indexed (partial browse2 (map vec input)))
   (apply concat)
   (apply max)))



